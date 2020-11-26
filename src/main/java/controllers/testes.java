package controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class testes {

	public static void main(String[] args) {

		Double do1 = 23.20;
		Double do2 = 23.20;
		Double do3 = 23.29999999999999;
		
		System.out.println(23.20);
		System.out.println(do1);
		System.out.println(do2);
		System.out.println(do3);
		System.out.println(round(do3, 2));
		
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
