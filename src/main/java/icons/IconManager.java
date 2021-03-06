package icons;

import entities.Node;
import entities.Room;
import entities.RoomType;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class for managing room icons
 *
 * @note instance variables named "handler" in this class are functions that generate
 * handlers, not handlers themselves
 *
 * If you want to add new listeners to icons, follow these instructions:
 *
 * 1. Add a new instance variable after the existing listener variables. This should be of
 *    type BiConsumer&lt;Room, MouseEvent&gt; (a void function that takes a room and a
 *    mouse event).
 * 2. Add a setter for the variable.
 * 3. Add it to applyListeners, following the pattern of existing listeners.
 */
public class IconManager
{
	private static final double ICON_SIZE = 0.08; //Scale factor on the icon image
	private static final double ICON_SIZE_LARGE = 0.1; //Scale factor of icon when hovered over
	private static final double ICON_SIZE_HUGE = 0.1333333;
	private static final double LABEL_SIZE = 0.3; //Scale factor on the icon image
	private static final double LABEL_SIZE_LARGE = 0.4; //Scale factor on the icon image
	private static final double LABEL_SIZE_HUGE = 0.9; //Scale factor on the icon image
	private static final int FONT_SIZE = 15;
	private static final Color BACKGROUND_COLOR = Color.DARKGRAY.deriveColor(0, 0, 0, 0.5);
	private static final BackgroundFill BACKGROUND_FILL = new BackgroundFill(
			BACKGROUND_COLOR,
			new CornerRadii(2),
			new Insets(0, -2, 0, -2)
	);
	private static final Background LABEL_BACKGROUND = new Background(BACKGROUND_FILL);

	private MassMap<Room, Icon> roomIcons;
	//Map<EventType<? extends Event>, Function<Room, EventHandler<? super Event>>> handlers;

	/* Listener variables */
	private BiConsumer<Room, MouseEvent> onMouseClickedOnRoomHandler;
	private BiConsumer<Room, MouseEvent> onMouseDraggedOnLabelHandler;
	private BiConsumer<Room, MouseEvent> onMouseEnteredRoomHandler;
	private BiConsumer<Room, MouseEvent> onMouseClickedOnPathSegmentEnd;
	private boolean increaseRoomSizeOnHover = true;

	public IconManager() {
		this.roomIcons = new MassMap<>();
	}

	/**
	 * Prepare a mouse click handler for the main icon
	 *
	 * The handler function should produce a handler that operates on a room.
	 *
	 * @param handler A function that consumes a room and a mouse event
	 */
	public void setOnMouseClickedOnSymbol(BiConsumer<Room, MouseEvent> handler) {
		this.onMouseClickedOnRoomHandler = handler;
	}

	public void updateListeners(Set<Room> rooms) {
		for (Room r : rooms) {
			if (this.roomIcons.containsKey(r)) {
				this.applyListeners(r, r.getIcon());
			}
		}
	}

	/**
	 * Prepare a mouse drag handler for the icons' labels
	 *
	 * @param handler A function that consumes a room, and a mouse event
	 */
	public void setOnMouseDraggedOnLabel(BiConsumer<Room, MouseEvent> handler) {
		this.onMouseDraggedOnLabelHandler = handler;
	}


	/**
	 * Prepare a handler for the icons' symbols in a path
	 *
	 * The passed room is from an adjacent segment to the clicked room
	 *
	 * @param handler A function that consumes a room and a mouse event
	 */
	public void setOnMouseClickedOnPathSegmentEnd(BiConsumer<Room, MouseEvent> handler) {
		this.onMouseClickedOnPathSegmentEnd = handler;
	}

	/**
	 * Set whether icons should increase in size when the icon or label is hovered
	 *
	 * The default value is true
	 *
	 * @param value true to change room sizes when hovered
	 */
	public void increaseRoomSizeOnHover(boolean value) {
		this.increaseRoomSizeOnHover = value;
	}

	/**
	 * Get the objects to display for the given rooms
	 *
	 * @param rooms The rooms to get icons for
	 * @return A set of JavaFX Nodes to display
	 *
	 * @note No guarantees are made about the specific contents of the returned set
	 */
	public Set<Icon> getIcons (Collection<Room> rooms) {
		return roomIcons.computeAllIfAbsent(rooms, this::makeIcon);
	}

	/**
	 * Get the existing icons for the given room, ignoring rooms without icons
	 */
	public Set<Icon> getSavedIcons(Collection<Room> rooms) {
		return roomIcons.getAll(rooms);
	}

	/**
	 * Makes an icon and adds it to a room
	 *
	 * @param room The room we are adding the icon to
	 * @return The Icon
	 *
	 */
	public Icon makeIconInner(Room room, BiConsumer<Room, Icon> listenerAdder) {
		RoomType type = room.getType();
		String name = room.getDisplayName(); // CHANGE TO getName TO SHOW ALL LABELS
		Image originalImage = type.getImage();
		double x = room.getLocation().getX();
		double y = room.getLocation().getY();
		double imageHeight = originalImage.getHeight();
		double imageWidth = originalImage.getWidth();

		ImageView image = new ImageView(originalImage);
		Label label = new Label(name); // TODO: Hide the label if given empty string/null

		image.setScaleX(ICON_SIZE);
		image.setScaleY(ICON_SIZE);

		//Center image on the coordinates.
		image.setLayoutX(x - imageWidth/2);
		image.setLayoutY(y - imageHeight/2);

		//Label settings
		label.setLayoutX(x + room.getLabelOffsetX());
		label.setLayoutY(y + room.getLabelOffsetY());
		label.setFont(new Font(FONT_SIZE));
		label.setTextFill(Color.LIGHTGRAY);
		label.setBackground(LABEL_BACKGROUND);
		label.setScaleX(LABEL_SIZE);
		label.setScaleY(LABEL_SIZE);

		//Create Icon and link to room
		Icon icon = new Icon(room, image, label);
		listenerAdder.accept(room, icon);
		room.setIcon(icon);
		return icon;
	}
	private Icon makeIcon(Room room) {
		return this.makeIconInner(room, this::applyListeners);
	}
	private Icon makePathIcon(Room room) {
		return this.makeIconInner(room, this::applyPathListeners);
	}
	private Icon makeFrozenIcon(Room room) {
		return this.makeIconInner(room, (r, i) -> {});
	}

	/**
	 * Generate and apply listeners to the room and icon
	 *
	 * @param room The room to reference in the listeners
	 * @param icon The room's icon
	 */
	private void applyListeners(Room room, Icon icon) {
		if (increaseRoomSizeOnHover) {
			ImageView image = icon.getImage();
			Label label = icon.getLabel();
			label.setPickOnBounds(false);
			label.setOnMouseEntered(event -> {
				image.setScaleX(ICON_SIZE_LARGE);
				image.setScaleY(ICON_SIZE_LARGE);
			});
			label.setOnMouseExited(event -> {
				image.setScaleX(ICON_SIZE);
				image.setScaleY(ICON_SIZE);
			});
			image.setOnMouseEntered(event -> {
				image.setScaleX(ICON_SIZE_LARGE);
				image.setScaleY(ICON_SIZE_LARGE);
			});
			image.setOnMouseExited(event -> {
				image.setScaleX(ICON_SIZE);
				image.setScaleY(ICON_SIZE);
			});
		}


		if (onMouseClickedOnRoomHandler != null) {
			ImageView image = icon.getImage();
			BiConsumer<Room, MouseEvent> listener = onMouseClickedOnRoomHandler;
			image.setOnMouseClicked(event -> {
				listener.accept(room, event);
			});
		}

		if (onMouseEnteredRoomHandler != null) {
			ImageView image = icon.getImage();
			BiConsumer<Room, MouseEvent> listener = onMouseEnteredRoomHandler;
			image.setOnMouseEntered(event -> {
				listener.accept(room, event);
			});
		}

//		if (onMouseDraggedOnLabelHandler != null) {
//			Label label = icon.getLabel();
//			BiConsumer<Room, MouseEvent> listener = onMouseDraggedOnLabelHandler;
//			label.setOnMouseDragged(event -> {
//				listener.accept(room, event);
//			});
//		}
	}


	public Set<Icon> getLinkedPathIcons(List<List<Node>> path) {
//		if (true) throw new RuntimeException("Do not use IconManager::getLinkedPathIcons!");
		if (path.size() == 0) return Collections.emptySet();

		List<Node> firstSegment = path.get(0);

		List<Node> iconNodes = new LinkedList<>();

		iconNodes.add(firstSegment.get(0));
		if (firstSegment.size() != 1) {
			iconNodes.add(firstSegment.get(firstSegment.size()-1));
		} else {
			iconNodes.add(null);
		}

		if (path.size() > 2) {
			for (List<Node> segment : path.subList(1, path.size()-1)) {
				if (segment.size() <= 1) continue;
				iconNodes.add(segment.get(0));
				iconNodes.add(segment.get(segment.size() - 1));
			}
		}

		if (path.size() > 1) {
			List<Node> lastSegment = path.get(path.size()-1);
			if (lastSegment.size() != 1) {
				iconNodes.add(lastSegment.get(0));
			} else {
				iconNodes.add(null);
			}
			iconNodes.add(lastSegment.get(lastSegment.size() - 1));
		}

		return this.getPathNodeIcons(iconNodes);
	}

	/**
	 * Create icons for the key points of a path.
	 *
	 * This is the format for the argument:
	 *
	 * Each pair of nodes is the beginning and end node of a segment.
	 *
	 * No nodes are present for length-one segments, except the first and last
	 * segments.
	 *
	 * If the first segment has only one node, then the first entry will be null.
	 *
	 * If the last segment has only one node, then the next-to-last entry will be null.
	 *
	 *  @param iconNodes A list of nodes in a path, following the format described above
	 *
	 * @return A set of icons for the given nodes
	 */
	private Set<Icon> getPathNodeIcons(List<Node> iconNodes) {
		Set<Icon> icons = new HashSet<>();

		if (iconNodes.get(0) != null) {
			Icon icon = this.roomIcons.computeIfAbsent(iconNodes.get(0).getRoom(), this::makeFrozenIcon);
			Label label = icon.getLabel();
			label.setVisible(false);
		}

		for (int i = 1; i < iconNodes.size(); i += 2) {
			Node next = iconNodes.get(i);
			Node last = iconNodes.get(i-1);

			Consumer<Node> addStartOrEndIcon = node -> {
				if (node != null) {
					node.applyToRoom(room -> {
						Icon icon = this.makePathIcon(room);
						if (icon != null) {
							this.roomIcons.put(room, icon);
							icons.add(icon);
						}});}}; // this is lisp
			addStartOrEndIcon.accept(next);
			addStartOrEndIcon.accept(last);

		}

		if (iconNodes.get(iconNodes.size()-1) != null) {
			Icon icon = this.roomIcons.computeIfAbsent(iconNodes.get(iconNodes.size()-1).getRoom(), this::makeFrozenIcon);
			Label label = icon.getLabel();
			label.setVisible(false);
		}

		return roomIcons.computeAllIfAbsent(
				iconNodes.stream()
						.filter(Objects::nonNull)
						.map(Node::getRoom)
						.filter(Objects::nonNull)
						.collect(Collectors.toSet()),
				this::makePathIcon);

//		return icons;
	}

	private void applyPathListeners(Room target, Icon icon) {
		if (onMouseClickedOnPathSegmentEnd != null) {
			BiConsumer<Room, MouseEvent> listener = onMouseClickedOnPathSegmentEnd;
			icon.setOnMouseReleased(event -> {
				listener.accept(target, event);
			});
		}
	}

	public void setOnMouseEnteredRoomHandler(BiConsumer<Room, MouseEvent> handler) {
		this.onMouseEnteredRoomHandler = handler;
	}

	private class MassMap<K, V> extends HashMap<K, V>
	{
		MassMap() {
			super();
		}
		/**
		 * Get the values for the given keys, computing values for missing keys
		 *
		 * The computed values are storred in the map
		 */
		public Set<V> computeAllIfAbsent(Collection<K> keys,
		                                 Function<? super K, ? extends V> mappingFunction) {
			Set<V> values = new HashSet<>();
			for (K key : keys) {
				values.add(computeIfAbsent(key, mappingFunction));
			}
			return values;
		}

		/**
		 * Get the values for the given keys, ignoring non-present keys
		 */
		public Set<V> getAll(Collection<K> keys) {
			Set<V> values = new HashSet<>();
			for (K key : keys) {
				if (containsKey(key)) {
					values.add(get(key));
				}
			}
			return values;
		}
	}
}
