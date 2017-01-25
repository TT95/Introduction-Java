package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Demonstrating class {@link RequestContext}.
 * Writes context files into demo folder.
 * @author Teo Toplak
 *
 */
public class DemoRequestContext {
	
	/**
	 * Method called when executing demonstration.
	 * @param args no command line arguments 
	 * @throws IOException when error with stream occured
	 */
	public static void main(String[] args) throws IOException {
		demo1("demo/primjer1.txt", "ISO-8859-2");
		demo1("demo/primjer2.txt", "UTF-8");
		demo2("demo/primjer3.txt", "UTF-8");
	}
	
	/**
	 * Demonstration one.
	 * @param filePath path where demo file will be written
	 * @param encoding encoding
	 * @throws IOException when stream error occured
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
	
	/**
	 * Demonstration two.
	 * @param filePath path where demo file will be written
	 * @param encoding encoding
	 * @throws IOException when stream error occured
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}
