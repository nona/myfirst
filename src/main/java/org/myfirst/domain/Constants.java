package org.myfirst.domain;

import java.util.ArrayList;
import java.util.Locale;

public class Constants {


	
	
	public static enum Gender {
		MALE,
		FEMALE
	}

	public static ArrayList<String> getCountries() {
		String[] locales = Locale.getISOCountries();
		ArrayList<String> countryNames = new ArrayList<String>();

		for (String countryCode : locales) {

		    Locale obj = new Locale("", countryCode);
		    countryNames.add(obj.getDisplayCountry());
		}
		return countryNames;
	}
}
