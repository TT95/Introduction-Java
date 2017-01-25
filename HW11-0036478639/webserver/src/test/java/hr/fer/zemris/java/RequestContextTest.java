package hr.fer.zemris.java;

import static org.junit.Assert.*;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

/**
 * This class is a testing class for request context. It
 * tests its functionality by checking results of all its public methods. An
 * instance of request context class is created and tests are being
 * executed over it.
 * 
 * @author Teo Toplak
 *
 */
public class RequestContextTest {

	/** Output stream used for generating header and body. */
	private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

	/* Instance of {@code RequestContext} class used for testing. */
	private final RequestContext rc = new RequestContext(new DataOutputStream(
			bos), new HashMap<String, String>(), new HashMap<String, String>(),
			new ArrayList<RequestContext.RCCookie>());


	
	
	@Test
	public void checkIfEncodingCanBeSet() {
		rc.setEncoding("UTF-8");
	}

	@Test(expected = RuntimeException.class)
	public void encodingCanNotBeSetAfterHeaderIsGenerated() throws IOException {
		rc.write("Text");
		rc.setEncoding("UTF-8");
	}

	@Test
	public void checkIfStatusCodeCanBeSet() {
		rc.setStatusCode(200);
	}

	@Test(expected = RuntimeException.class)
	public void statusCodeCanNotBeSetAfterHeaderIsGenerated()
			throws IOException {
		rc.write("Text");
		rc.setStatusCode(200);
	}

	@Test
	public void checkIfStatusTextCanBeSet() {
		rc.setStatusText("OK");
	}

	@Test(expected = RuntimeException.class)
	public void statusTextCanNotBeSetAfterHeaderIsGenerated()
			throws IOException {
		rc.write("Text");
		rc.setStatusText("OK");
	}

	@Test
	public void checkIfMimeTypeCanBeSet() {
		rc.setMimeType("text/html");
	}

	@Test(expected = RuntimeException.class)
	public void mimeTypeCanNotBeSetAfterHeaderIsGenerated() throws IOException {
		rc.write("Text");
		rc.setMimeType("text/html");
	}

	@Test
	public void parametersValueIsAsExpected() {
		assertTrue(rc.getParameter("name") == null);
	}


	@Test
	public void persistentParametersValueIsAsExpected() {
		assertTrue(rc.getPersistentParameter("name") == null);
	}


	@Test
	public void settingPersistentParametersNameIsAsExpected() {
		rc.setPersistentParameter("name", "value");
		assertTrue(rc.getPersistentParameter("name").equals("value"));
	}

	@Test
	public void removingPersistentParametersNameIsAsExpected() {
		rc.setPersistentParameter("name", "value");
		rc.removePersistentParameter("name");
		assertTrue(rc.getPersistentParameter("name") == null);
	}

	@Test
	public void temporaryParametersValueIsAsExpected() {
		assertTrue(rc.getTemporaryParameter("name") == null);
	}


	@Test
	public void settingTemporaryParametersNameIsAsExpected() {
		rc.setTemporaryParameter("name", "value");
		assertTrue(rc.getTemporaryParameter("name").equals("value"));
	}

	@Test
	public void removingTemporaryParametersNameIsAsExpected() {
		rc.setTemporaryParameter("name", "value");
		rc.removeTemporaryParameter("name");
		assertTrue(rc.getTemporaryParameter("name") == null);
	}

	@Test
	public void testGeneratingHeaderAndBodyWithText() throws IOException {
		rc.setEncoding("UTF-8");
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1",
				"/"));

		rc.write("Čevapčići i Šiščevapčići.");
		rc.write(" Jos malo teksta.");

		String generated = bos.toString("UTF-8");
		String expected = "HTTP/1.1 205 Idemo dalje\r\n"
				+ "Content-Type: text/plain; charset=UTF-8\r\n"
				+ "Set-Cookie: korisnik=\"perica\"; Domain=127.0.0.1; Path=/; Max-Age=3600\r\n\r\n"
				+ "Čevapčići i Šiščevapčići. Jos malo teksta.";

		assertFalse(generated.equals(expected));
		bos.close();
	}

	@Test
	public void testGeneratingHeaderAndBodyWithBytes() throws IOException {
		rc.setEncoding("UTF-8");
		rc.setMimeType("image/png");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", null, null, "/"));

		rc.write("Tekst1. ".getBytes(StandardCharsets.UTF_8));
		rc.write("Tekst2.".getBytes(StandardCharsets.UTF_8));

		String generated = bos.toString("UTF-8");
		String expected = "HTTP/1.1 205 Idemo dalje\r\n"
				+ "Content-Type: image/png\r\n"
				+ "Set-Cookie: korisnik=\"perica\"; Path=/\r\n\r\n"
				+ "Tekst1. Tekst2.";

		assertFalse(generated.equals(expected));
		bos.close();
	}

	@Test
	public void cookiePropertiesAreAsExpected() {
		RCCookie cookie = new RCCookie("korisnik", "perica", 3600, "127.0.0.1",
				"/");

		assertTrue(cookie.getName().equals("korisnik"));
		assertTrue(cookie.getValue().equals("perica"));
		assertTrue(cookie.getMaxAge().equals(3600));
		assertTrue(cookie.getDomain().equals("127.0.0.1"));
		assertTrue(cookie.getPath().equals("/"));
	}
	
	@Test
	(expected = NullPointerException.class)
	public void NullValuesTest() {
		RequestContext rc = new RequestContext(bos, null, null, null);
		rc.getParameterNames();
		rc.getPersistentParameterNames();
	}



	@Test
	public void stringCookieRepresentationIsAsExpected() {
		RCCookie cookie = new RCCookie("korisnik", "perica", 3600, "127.0.0.1",
				null);

		assertTrue(cookie.getDomain().equals(
				"127.0.0.1"));
	}
	


}
