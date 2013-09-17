package it.geosolutions.nrl.utils;

public class DekadUtils {
	public static int absouluteFromYearMonthDek(int year, int month, int dek){
		return year * 36 + ((month -1)  * 3 )+ dek;
	} 
}
