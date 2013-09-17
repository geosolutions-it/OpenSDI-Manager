package it.geosolutions.nrl.model;

import it.geosolutions.nrl.utils.DekadUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the dekad as an temporal entity. The dekad is a 8-11 days interval
 * that wraps the first 12 days in month, the 2nd 10 and the remaining.
 * e.g.
 *  1st dekad Jan - 1st Jan to 10th Jan
 *  3rd dek Feb - from 21st Feb to 28th-29th Feb
 *  
 * @author Admin
 *
 */
public class Dekad {
	private static final String REG_DEK= "^[0-9]{4}[0-1]{1}[0-9]{1}[0-3]{0,1}[1-3]{1}$";
	private static final String REG_TIME_SERIES = ""; //TODO
	private int absoluteDekad;
	
	public Dekad(String time){
		setTime(time);
		
	}
	public void setTime(String time){
		Pattern pattern = Pattern.compile(REG_DEK);
		Matcher match = pattern.matcher(time);
		if(match.matches()){
			int year = Integer.parseInt(time.substring(0,4));
			int month = Integer.parseInt(time.substring(4,6));
			int dek =Integer.parseInt(time.substring(6));
			absoluteDekad = DekadUtils.absouluteFromYearMonthDek(year, month, dek);
		}
	}
	
	/**
	 * Seasonal dekad is a integer from 1 to 36 from Nov 1st.
	 * 1st Dekad on November is the start of Rabi Season
	 * 36th Dekad is the 3rd Dekad in October, end of Kahrif Season
	 * E.g.
	 * * 1  - Nov 1st dek
	 * * 2  - Nov 2nd dek
	 * * 36 - Oct 3rd dek
	 * @return the seasonal dekad (1-36)
	 */
	public int getSeasonalDekad(){
		return (getDekadInYear() + 5 ) %36 + 1;
	}
	
	/**
	 * 
	 * @return 1 if the dekad is 1st, 2 if the dekad is 2nd,3 if the dekad is 3rd
	 */
	public int getDekad(){
		return (absoluteDekad - 1 )% 3 + 1;
	}
	
	/**
	 * dekad in year is an integer between 1 and 36
	 * * 1 - Jan 1st dek
	 * * 2 - Jam 2nd dek
	 * * 36 - Dec 36 dek 
	 * @return a number between 1 and 36 (the dekad in year)
	 */
	public int getDekadInYear(){
		return (absoluteDekad -1) %36 +1;
	}
	
	/**
	 * The absolute dekad is the number of the dekads from the year 0
	 * e.g. 
	 * * year 0 - Jan 1st dekad = 1
	 * * year 1 - Jan 1st dekad = 37
	 * * year 1492 - Sept 2nd dekad = 53738
	 * * year 2000 - Jan 1st dekad  =72001 
	 * @return the absolute dekad
	 */
	public int getAbsoluteDekad(){
		return this.absoluteDekad;
	}
	
}
