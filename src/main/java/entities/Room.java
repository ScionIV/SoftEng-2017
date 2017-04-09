package entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A class for Room(s).
 *
 */
//TODO: I wasn't able to tell based on the diagram if the Room would have a node or a node would have a room...
public class Room
{
	// TODO: Fix room shape operations
	protected static final double DEFAULT_STROKE_WIDTH = 1.5;
	protected static final double RECTANGLE_WIDTH = 7;
	protected static final double RECTANGLE_HEIGHT = 7;
	protected static final Color DEFAULT_SHAPE_COLOR = Color.web("0x0000FF");
	protected static final Color DEFAULT_STROKE_COLOR = Color.BLACK;
	protected static final Color SELECTED_SHAPE_COLOR = Color.BLACK;
	protected static final Color CONNECTION_LINE_COLOR = Color.BLACK;
	protected static final Color KIOSK_COLOR = Color.YELLOW;
	protected static final String KIOSK_NAME = "You Are Here";
	private static final String DEFAULT_IMAGE_PATH = "/MysteryRoom.png";

	/* Attributes */
	private Node location;
	private String name;
	private String description;
	private String image; // The String path of the image for this room
	private Rectangle rect;

	/* Constructors */
	public Room(String name, String description, String image) {
		this.location = null;
		this.name = name;
		this.description = description;
		this.image = image;
		this.makeShape();
	}

	public Room(String name, String description) {
		this(name, description, Room.DEFAULT_IMAGE_PATH);
	}

	// TODO: Remove this constructor in favor of association with existing nodes
	@Deprecated
	public Room(double x, double y, String name, String description, String image) {
		this(name, description, image);
		this.location = new Node(x, y);
	}

	// TODO: Remove this constructor in favor of association with existing nodes
	@Deprecated
	public Room(double x, double y) {
		this("Anonymous Room", "A Room with no name or special description.");
		this.location = new Node(x, y);
	}

	/* Methods */

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getImage() {
		return this.image;
	}

	public Node getLocation() {
		return this.location;
	}

	public void setLocation(Node location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImage(String imagepath) {
		this.image = imagepath;
	}

	// TODO: Remove Room::toString; replace with custom method
	@Override
	public String toString() {
		return this.name;
	}

	public Rectangle getShape() {
		return this.rect;
	}

	private void makeShape() {
		if(this.location != null) {
			this.rect = new Rectangle(this.location.getX(), this.location.getY(), this.RECTANGLE_WIDTH, this.RECTANGLE_HEIGHT);

			this.rect.setStroke(this.DEFAULT_STROKE_COLOR);
			this.rect.setStrokeWidth(this.DEFAULT_STROKE_WIDTH);

			if (this.getName().equalsIgnoreCase(this.KIOSK_NAME)) {
				this.rect.setFill(this.KIOSK_COLOR);
			} else {
				this.rect.setFill(this.DEFAULT_SHAPE_COLOR);
			}
		}
	}
}
