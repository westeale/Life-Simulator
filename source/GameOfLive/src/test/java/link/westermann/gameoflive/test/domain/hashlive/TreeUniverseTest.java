package link.westermann.gameoflive.test.domain.hashlive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import link.westermann.gameoflive.domain.hashlive.TreeUniverse;

public class TreeUniverseTest {
	private static TreeUniverse universe;
	
	@BeforeClass
	public static void init() {
		universe = new TreeUniverse();
	}

	@Test
	public void testBlinker() {
		universe.setBit(1, 0, true);
		universe.setBit(1, 1, true);
		universe.setBit(1, 2, true);
		universe.runStep();
		assertFalse(universe.getBit(0,0));
		assertFalse(universe.getBit(1,0));
		assertFalse(universe.getBit(2,0));
		assertFalse(universe.getBit(0,2));
		assertFalse(universe.getBit(1,2));
		assertFalse(universe.getBit(2,2));
		assertTrue(universe.getBit(0,1));
		assertTrue(universe.getBit(1,1));
		assertTrue(universe.getBit(2,1));
		universe.runStep();
		assertTrue(universe.getBit(1,0));
		assertTrue(universe.getBit(1,1));
		assertTrue(universe.getBit(1,2));
		assertFalse(universe.getBit(2,1));
	}

}
