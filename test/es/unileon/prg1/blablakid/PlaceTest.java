package es.unileon.prg1.blablakid;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlaceTest{
	Place place;

	@Before
	public void setUp(){
		place = new Place("Palomera");
	}

	@Test
	public void testPlace(){
		assertEquals("Palomera", place.getPlace());
	}
	
	@Test
	public void testIsSame(){
		Place place2 = new Place("Palomera");
		assertEquals(true, place.isSame(place2));
		
	}
	
	@Test
	public void testToString(){
		assertEquals("Place: Palomera", place.toString());
	}
}