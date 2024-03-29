package es.unileon.prg1.blablakid;
 /**
 * 
 * @author Adrian Perez
 *
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ParentTest {
	
	private Parent parent;
	Kids parentKids;

	@Before
	public void setUp()throws KidException{
		this.parentKids = new Kids(3);
		Kid Beatriz = new Kid("Beatriz");
		Kid Daniel = new Kid("Daniel");
		Kid Manuel = new Kid("Manuel");
		parentKids.add(Beatriz);
		parentKids.add(Daniel);
		parentKids.add(Manuel);
		this.parent = new Parent("Pedro", parentKids, 6);
	}

	@Test
	public void testGetName(){
		assertEquals("Pedro", parent.getName());
	}
	
	@Test
	public void testGetNumRides(){
		assertEquals(6, parent.getNumRides());
	}
	
	@Test
	public void testSearchKids() {
		assertEquals("Beatriz", this.parent.search("Beatriz").getName());
	}
	
	@Test
	public void testSearchKidsWrong() {
		assertNull(this.parent.search("Rodolfo"));
	}
	
	@Test
	public void testIsSame()throws KidException{
		Kids parentKids2 = new Kids(3);
		Kid Juan2 = new Kid("Juan");
		Kid Roberto2 = new Kid("Roberto");
		Kid Hector2 = new Kid("Hector");
		parentKids2.add(Juan2);
		parentKids2.add(Roberto2);
		parentKids2.add(Hector2);
		Parent parent2 = new Parent("Pedro", parentKids2, 6);
		assertTrue(this.parent.isSame(parent2));
	}
	
	@Test
	public void testIsSameWrong()throws KidException{
		Kids parentKids2 = new Kids(3);
		Kid Juan2 = new Kid("Juan");
		Kid Roberto2 = new Kid("Roberto");
		Kid Hector2 = new Kid("Hector");
		parentKids2.add(Juan2);
		parentKids2.add(Roberto2);
		parentKids2.add(Hector2);
		Parent parent2 = new Parent("Alvaro",parentKids2, 6);
		assertFalse(this.parent.isSame(parent2));
	}
	
	@Test
	public void testSearchDays() {
		WeekDays wDay = WeekDays.TUESDAY;
		Day day = new Day(wDay);
		assertEquals(day.getNumDay() ,this.parent.search(1).getNumDay());
	}
	
	@Test
	public void testRides() throws HourException, RideException, DayException {
		Place startPlace = new Place("Palomera");
		Place endPlace = new Place("Casa");
		Hour startTime = new Hour(16, 00);
		Hour endTime = new Hour(16, 30);
		Ride ride = new Ride(startPlace, endPlace, startTime, endTime);
		Day day = this.parent.search(2);
		this.parent.add(ride, day);
		assertEquals(ride.getEndPlace(), this.parent.search(day.getNumDay()).search(startPlace.getPlace(), endPlace.getPlace()).getEndPlace());
		this.parent.remove(ride, day);
		assertNull(this.parent.search(day.getNumDay()).search(startPlace.getPlace(), endPlace.getPlace()));
	}

	@Test (expected = DayException.class)
	public void testAddRideWrong() throws Exception{
		Place startPlace = new Place("Palomera");
		Place endPlace = new Place("Casa");
		Hour startTime = new Hour(16, 00);
		Hour endTime = new Hour(16, 30);
		Ride ride = new Ride(startPlace, endPlace, startTime, endTime);
		Day day = null;
		this.parent.add(ride, day);
	}

	@Test
	public void testSearchRides() throws Exception{
		Place startPlace = new Place("Palomera");
		Place endPlace = new Place("Casa");
		Hour startTime = new Hour(16, 00);
		Hour endTime = new Hour(16, 30);
		Ride ride = new Ride(startPlace, endPlace, startTime, endTime);
		Day day = this.parent.search(2);
		this.parent.add(ride, day);
		assertEquals(ride.toString(), this.parent.search("Palomera", "Casa", this.parent.search(2)).toString());
	}
	
	@Test (expected = DayException.class)
	public void testSearchRidesWrong() throws DayException {
		Day day = null;
		this.parent.search("Palomera", "Casa",  day);
	}
	
	@Test
	public void testRemoveKid() throws KidException {
		Kid kid = new Kid(this.parent.search("Beatriz").getName());
		
		this.parent.remove(kid);
	}
	
	@Test
	public void testToString() {
		StringBuilder out = new StringBuilder();
		out.append("###### Pedro ######\n");
		out.append("Kids: \n");
		out.append(this.parentKids.toString());		
		assertEquals(out.toString(), this.parent.toString());
	}
}
