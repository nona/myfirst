package org.myfirst.domain;

import java.util.ArrayList;
import java.util.Locale;

public class Constants {

	public static int ITEMS_PER_PAGE = 10;

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
