package hr.fer.zemris.java.tecaj.hw07.shell;

/**
 * This class is used to support paths given with quotes. Path given with quotes
 * are mostly used to give path to a file which has whitespace in it.
 * 
 * @author Teo Toplak
 *
 */
public class PathSupport {

	/**
	 * Method returns regex for splitting string by spaces, while preserving
	 * those within quotes. Keeps quotes. For removing quotes after splitting
	 * with this regex other method getRegex() is used.
	 * @return regex for splitting
	 */
	public static String getRegex() {
		return "[ ]+(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
	}
	
	/**
	 * Method removing quotes surrounding string. If there is no quote marks,
	 * argument string will be returned.
	 * @param str argument string (with possible quotation marks)
	 * @return string without quotes
	 */
	public static String removeQuotes(String str) {
		if(str.startsWith("\"") && str.endsWith("\"")) {
			return str.substring(1,str.length()-1);
		}
		return str;
	}
}
