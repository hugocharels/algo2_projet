package ulb.algo2;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MBRTest {

	// Modifiers
	@Test
	public void expand() {
		MBR mbr = new MBR(0, 1, 0, 1);
		MBR other = new MBR(0, 2, 0, 2);
		mbr.expand(other);
		assertEquals(0, mbr.getXMin());
		assertEquals(2, mbr.getXMax());
		assertEquals(0, mbr.getYMin());
		assertEquals(2, mbr.getYMax());
	}

	// Verifiers
	@Test
	public void containsPoint() {
		MBR mbr = new MBR(0, 1, 0, 1);
		assertTrue(mbr.contains(0.5, 0.5));
	}

	@Test
	public void containsMBR() {
		MBR mbr = new MBR(0, 1, 0, 1);
		MBR other = new MBR(0.5, 0.5, 0.5, 0.5);
		assertTrue(mbr.contains(other));
	}

	@Test
	public void percentageCovered() {
		MBR mbr = new MBR(0, 1, 0, 1);
		MBR other = new MBR(0, 0.5, 0, 0.5);
		assertEquals(0.25, mbr.percentageCovered(other));
	}

	// Getters
	@Test
	public void area() {
		MBR mbr = new MBR(0, 1, 0, 1);
		assertEquals(1, mbr.getArea());
	}

	@Test
	public void expansion() {
		MBR mbr = new MBR(0, 1, 0, 1);
		MBR other = new MBR(0, 2, 0, 2);
		assertEquals(3, mbr.getExpansion(other));
	}

}
