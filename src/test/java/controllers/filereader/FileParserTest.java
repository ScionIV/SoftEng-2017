package controllers.filereader;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import entities.Professional;
import entities.Directory;
import entities.Room;

/*
Note that these tests exclude professionals with the same name, because we have no other
way to uniquely describe professionals.
 */
public class FileParserTest
{
	/** Add a professional with the given information and rooms based on the given keys */
	static Professional quickProf(Map<Integer, Room> roomMap, String surname, String givenName,
	                       String titles, Integer... roomKeys) {
		Professional p = new Professional(givenName, surname, titles);
		for (int i : roomKeys) {
			if (roomMap.containsKey(i)) p.addLocation(roomMap.get(i));
		}
		return p;
	}

	@Test
	public void baseTest()
			throws FileNotFoundException {
		Map<Integer, Room> map = new HashMap<>();
		map.put(2, new Room("Room 2", ""));
		map.put(1, new Room("Room 1", ""));
		map.put(3, new Room("Room 3", ""));
		map.put(4, new Room("Room 4", ""));
		map.put(5, new Room("Room 5", ""));

		Directory dir = new Directory();
		dir.addRoom(map.get(1));
		dir.addRoom(map.get(2));
		dir.addRoom(map.get(3));
		dir.addRoom(map.get(4));
		dir.addRoom(map.get(5));
	
		// This should be in alphabetical order
		SortedSet<Professional> expect = new TreeSet<>(Arrays.asList(
				quickProf(map, "Smith", "John", "MD", 1),
				quickProf(map, "Smith", "Jane", "MD", 1, 2),
				quickProf(map, "Smith", "Jose", "MD", 1, 2, 3),
				quickProf(map, "Smith", "Jess", "MD", 1, 2, 3, 4),
				quickProf(map, "Jones", "Joan", "", 1),
				quickProf(map, "Jones", "James", "RN, CPNP"),
				quickProf(map, "Johnson", "John", "MD"),
				quickProf(map, "Johnson", "Jane", "MD", 1),
				quickProf(map, "Johnson", "Jack", "MD", 1, 2),
				quickProf(map, "Surname", "GivenName Info", "Title 1, Title 2", 1, 2, 3)
		));

		/* End setup */

		File f = new File(this.getClass().getResource("/testData1.tsv").getFile());
		FileParser.parseProfessionals(f, dir);
		SortedSet<Professional> actual = dir.getProfessionals();

		while (! actual.isEmpty() && ! expect.isEmpty()) {
			Professional act = actual.first();
			Professional exp = expect.first();
			actual.remove(act);
			expect.remove(exp);
			assertProfessionalsEqual(exp, act);
		}
		if (! actual.isEmpty() || ! expect.isEmpty()) {
			Assert.assertEquals("No elements should remain:", expect.size(), actual.size());
		}
	}

	/** return whether the names and titles of the given professionals are the same */
	private static void assertProfessionalsEqual(Professional expect, Professional actual) {
		Assert.assertEquals("Surname", expect.getSurname(), actual.getSurname());
		Assert.assertEquals("Given name", expect.getGivenName(), actual.getGivenName());
		Assert.assertEquals("Title", expect.getTitle(), actual.getTitle());
		Assert.assertEquals("Locatons", expect.getLocations(), actual.getLocations());
	}
}
