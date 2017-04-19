package controllers.shared;

import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.LinkedList;

public class Floor implements FloorImage
{
	// Attributes of the floor class
	private int floorNum;
	private String path;
	private String thumbPath;
	// Linked list containing the string names of the different floor images.
	private LinkedList<String> floorImages = new LinkedList<>(Arrays.asList(
							  "/1_thefirstfloor.png", "/2_thesecondfloor.png", "/3_thethirdfloor.png",
							  "/4_thefourthfloor.png", "/5_thefifthfloor.png", "/6_thesixthfloor.png",
							  "/7_theseventhfloor.png"));

	// Linked list containing the string names of the different floor images.
	private LinkedList<String> thumbnails = new LinkedList<>(Arrays.asList(
			"/t_faulk1.png", "/t_faulk2.png", "/t_faulk3.png",
			"/t_faulk4.png", "/t_faulk5.png", "/t_faulk6.png",
			"/t_faulk7.png"));

	// constructor for the floor class
	public Floor(int floorNum) {
		this.floorNum = floorNum;
		this.path = floorImages.get(floorNum - 1);
		this.thumbPath = thumbnails.get(floorNum - 1);
	}

	/** takes the String name of the path attribute and loads the image attached to that path
	 *
 	 * @return an image of the specified path.
	 * TODO: The image doesn't load sometimes. Having it load in the background prevents it from crashing
	 */
	public Image display() {
		Image ret;
		ret = new Image(path, true);

		return ret;
	}

	public Image displayThumb() {
		Image ret;
		ret = new Image(thumbPath, true);

		return ret;
	}


}
