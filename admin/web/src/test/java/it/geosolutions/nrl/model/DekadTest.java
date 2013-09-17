package it.geosolutions.nrl.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DekadTest {

	@Test
	public void absoluteDekadTest(){
		Dekad d1 = new Dekad("00000201");
		assertEquals(4,d1.getAbsoluteDekad());
		Dekad d2 = new Dekad("00010201");
		assertEquals(40,d2.getAbsoluteDekad());
		Dekad d3 = new Dekad("0000011");
		assertEquals(1,d3.getAbsoluteDekad());
		Dekad d4 = new Dekad("00001203");
		assertEquals(36,d4.getAbsoluteDekad());
		Dekad d5 = new Dekad("00000101");
		assertEquals(1,d5.getAbsoluteDekad());
		Dekad d6 = new Dekad("2013052");
		assertEquals(72482,d6.getAbsoluteDekad());
		Dekad d7 = new Dekad("1492092");
		assertEquals(53738,d7.getAbsoluteDekad());
		Dekad d8 = new Dekad("2000011");
		assertEquals(72001,d8.getAbsoluteDekad());

	}
	
	@Test
	public void dekadTest(){
		Dekad d1 = new Dekad("00000201");
		assertEquals(1,d1.getDekad());
		Dekad d2 = new Dekad("00010201");
		assertEquals(1,d2.getDekad());
		Dekad d3 = new Dekad("0000011");
		assertEquals(1,d3.getDekad());
		Dekad d4 = new Dekad("20131203");
		assertEquals(3,d4.getDekad());
		Dekad d5 = new Dekad("00000101");
		assertEquals(1,d5.getDekad());
		Dekad d6 = new Dekad("2013052");
		assertEquals(2,d6.getDekad());
		Dekad d7 = new Dekad("2013123");
		assertEquals(3,d7.getDekad());
		Dekad d8 = new Dekad("2014011");
		assertEquals(1,d8.getDekad());
	}

	
	@Test
	public void dekadInYearDekadTest(){
		Dekad d1 = new Dekad("00000201");
		assertEquals(4,d1.getDekadInYear());
		Dekad d2 = new Dekad("00010201");
		assertEquals(4,d2.getDekadInYear());
		Dekad d3 = new Dekad("0000011");
		assertEquals(1,d3.getDekadInYear());
		Dekad d4 = new Dekad("20131203");
		assertEquals(36,d4.getDekadInYear());
		Dekad d5 = new Dekad("00000101");
		assertEquals(1,d5.getDekadInYear());
		Dekad d6 = new Dekad("2013052");
		assertEquals(14,d6.getDekadInYear());
		Dekad d7 = new Dekad("2013123");
		assertEquals(36,d7.getDekadInYear());
		Dekad d8 = new Dekad("2014011");
		assertEquals(1,d8.getDekadInYear());

	}
	
	@Test
	public void seasonalDekadTest(){
		Dekad d1 = new Dekad("00000201");
		assertEquals(10,d1.getSeasonalDekad());
		Dekad d2 = new Dekad("00010201");
		assertEquals(10,d2.getSeasonalDekad());
		Dekad d3 = new Dekad("0000011");
		assertEquals(7,d3.getSeasonalDekad());
		Dekad d4 = new Dekad("20131203");
		assertEquals(6,d4.getSeasonalDekad());
		Dekad d5 = new Dekad("00000101");
		assertEquals(7,d5.getSeasonalDekad());
		Dekad d6 = new Dekad("2013052");
		assertEquals(20,d6.getSeasonalDekad());
		Dekad d7 = new Dekad("2013123");
		assertEquals(6,d7.getSeasonalDekad());
		Dekad d8 = new Dekad("2014011");
		assertEquals(7,d8.getSeasonalDekad());
		Dekad d9 = new Dekad("2014103");
		assertEquals(36,d9.getSeasonalDekad());
		Dekad d10 = new Dekad("2014111");
		assertEquals(1,d10.getSeasonalDekad());
	}
}
