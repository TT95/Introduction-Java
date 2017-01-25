package hr.fer.zemris.java.custom.scripting.exec.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Demonstrating {@link SmartScriptEngine} class for 4-th script from
 * homework.
 * Writes solution of console.
 * @author Teo Toplak
 *
 */
public class EngineDemo4 {
	/**
	 * Method called when executing program.
	 * @param args accepts no command line arguments
	 */
	public static void main(String[] args) {
		String documentBody = readFromDisk("demo/script4.txt");
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
	}
	/**
	 * Method reads file from path given in only argument
	 * @param string path of file
	 * @return string representing file read
	 */
	private static String readFromDisk(String string) {
		byte[] arr =null;
		try {
			arr = Files.readAllBytes(Paths.get(string));
		} catch (IOException e) {
			System.err.println("josko");
			e.printStackTrace();
		}
		return new String(arr,StandardCharsets.UTF_8);
	}
}
