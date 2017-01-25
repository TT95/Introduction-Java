package hr.fer.zemris.java.hw10.jnotepadpp.utilities;

import hr.fer.zemris.java.hw10.jnotepadpp.localization.LocalizationProvider;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Class representing utility for sorting text.
 * @author Teo Toplak
 *
 */
public class SortUtility {

	/**
	 * Sorts text descending or ascending depending on given arguments.
	 * 
	 * @param text text
	 * @param ascending true if you want ascening sorting
	 * @return string sorted
	 */
	public static String sort(String text, boolean ascending) {
		Locale hrLocale = new Locale(LocalizationProvider.getInstance().getLanguage());
		//for some reason only accepts raw types
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Comparator<String> collator =(Comparator) Collator.getInstance(hrLocale);
		String fin="";
		if(!ascending) {
			collator = collator.reversed();
		}
		String[] arr = text.split("\n");
		for(int i=0;i<arr.length;i++) {
			String[] lineArr = arr[i].split("\\s+");
			List<String> list = Arrays.asList(lineArr);
			Collections.sort(list,collator);
			arr[i]="";
			for(String str : list) {
				arr[i]+=str+" ";
			}
			//removing last space
			arr[i]=arr[i].substring(0, arr[i].length());
			arr[i]+="\n";
			fin+=arr[i];
		}
		return fin;
	}
}
