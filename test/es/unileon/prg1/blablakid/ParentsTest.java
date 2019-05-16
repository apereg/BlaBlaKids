package es.unileon.prg1.blablakid;
/**
 * @author Adrian Perez
 *
 */
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class ParentsTest {
	
	private Parents parents;
	private Parent parent1;
	private Parent parent2;
	private Parent parent3;
	
	@Before
	public void setUp() throws KidException, ParentException {
		Kids kids = new Kids(2);
		Kid kid1 = new Kid("Pablo");
		Kid kid2 = new Kid("Juan");
		kids.add(kid1);
		kids.add(kid2);
		
		Kids kids2 = new Kids(1);
		Kid kid21 = new Kid("Luis");
		kids2.add(kid21);
		
		Kids kids3 = new Kids(3);
		Kid kid31 = new Kid("Fran");
		Kid kid32 = new Kid("Alvaro");
		Kid kid33 = new Kid("Hector");
		kids3.add(kid31);
		kids3.add(kid32);
		kids3.add(kid33);
		
		parents = new Parents(3);
		parent1 = new Parent("Juan", kids, 6);
		parent2 = new Parent("Alvaro", kids2, 3);
		parent3 = new Parent("Jose", kids3, 4);
		parents.addParent(parent1);
		parents.addParent(parent2);
		parents.addParent(parent3);
	}
	
	@Test
	public void testisIncluded() throws KidException {
		assertTrue(this.parents.isIncluded(parent1));
		Kids kids = new Kids(1);
		Kid kid = new Kid("carlos");
		kids.add(kid);
		Parent parent = new Parent("Joaquin", kids, 2);
		assertFalse(this.parents.isIncluded(parent));
	}

	@Test(expected = ParentException.class)
	public void testAddIncluded() throws ParentException, KidException {
		this.parents.removeParent(parent2);
		Kids kids = new Kids(2);
		Kid kid = new Kid("Daniel");
		Kid kid2 = new Kid("Pablito");
		kids.add(kid);
		kids.add(kid2);
		Parent parent = new Parent("Jose", kids, 1);
		this.parents.addParent(parent);
	}

	@Test(expected = KidException.class)
	public void testAddFull() throws ParentException {
	}

	@Test
	public void testAddOk() throws ParentException {
	}

}
