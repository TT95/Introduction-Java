package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;


import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * HTTP server able to respond to client requests. Uses TCP protocol.
 * Can provide smart script (smscr extension) at request. Properties for server defined in
 * \config folder. Gives away cookies with values represented by 20 random uppercase letters.
 * Can respond to more requests at same time working with mulitiple threads. Address, port, number of 
 * worker threads, session timeout and slimilar can be defined in mentioned folder \config working with
 * properties files. Cookies are http-only.
 * 
 * @author Teo Toplak
 *
 */
public class SmartHttpServer {
	/**
	 * Server adress
	 */
	private String address;
	/**
	 * Path to worker defined by convention.
	 * Convention is using /ext/ in path before name 
	 * of worker class
	 */
	private String conventionWorkerPath;
	/**
	 * server port
	 */
	private int port;
	/**
	 * number of worker threads
	 */
	private int workerThreads;
	/**
	 * Session timeout for server
	 */
	private int sessionTimeout;
	/**
	 * Map of workers instances defined in this server
	 */
	private Map<String,IWebWorker> workersMap = new HashMap<String, IWebWorker>();
	/**
	 * Mime types supported by this server
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Class creating instances of new clients for
	 * request
	 */
	private ServerThread serverThread;
	/**
	 * Thread pool for server
	 */
	private ExecutorService threadPool;
	/**
	 * Document root of this server containing some
	 * reachable files for client
	 */
	private Path documentRoot;
	/**
	 * All sessions made while server was running.
	 * {@link SessionMapEntry} keeps information 
	 * for proper working with cookies
	 */
	private Map<String, SessionMapEntry> sessions =
			new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * {@link Random} class for generating random cookie values
	 */
	private Random sessionRandom = new Random();
	/**
	 * Tells if server is requested to stop
	 */
	private boolean stopServer=false;

	/**
	 * Method called when starting this server.
	 * @param args accepts path to server.properties file
	 */
	public static void main(String[] args) {
		if(args.length!=1) {
			System.out.println("Enter path to server.properties file!");
			System.exit(1);
		}
		SmartHttpServer server = new SmartHttpServer(args[0]);
		Scanner scanner = new Scanner(System.in);
		System.out.println("For terminating server at next request enter \"stop\"");
		server.start();
		while(true) {
			String line = scanner.nextLine();
			if(line.equals("stop")) {
				server.stop();
				scanner.close();
				break;
			}
		}
	}
	/**
	 * Starts up server.
	 * Initializes thread pool and server thread.
	 */
	protected synchronized void start() {
		if(serverThread==null) {
			serverThread = new ServerThread();
		}
		threadPool = Executors.newFixedThreadPool(workerThreads);
		serverThread.start();
	}

	/**
	 * Stops running server.
	 */
	protected synchronized void stop() {
		threadPool.shutdown();
		stopServer = true;
	}

	/**
	 * Constructor which initializes all values.
	 * Uses properties files to set up values.
	 * @param configFileName configuration file name
	 */
	public SmartHttpServer(String configFileName) {
		Path serverPath = Paths.get(configFileName);
		Properties prop = new Properties();
		// didnt want to put this in properties file since during review of this homework
		// those files will be overrided
		conventionWorkerPath = "hr.fer.zemris.java.webserver.workers.";

		try {
			InputStream is = Files.newInputStream(serverPath);
			//reading server porperties
			prop.load(is);
			address = prop.getProperty("server.address");
			port = Integer.parseInt(prop.getProperty("server.port"));
			workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
			documentRoot = Paths.get(prop.getProperty("server.documentRoot"));
			Path mimePath = Paths.get(prop.getProperty("server.mimeConfig"));
			sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout"));
			Path workersPath = Paths.get(prop.getProperty("server.workers"));

			//reading mime properties
			prop = new Properties();
			is = Files.newInputStream(mimePath);
			prop.load(is);
			Set<String> keys = prop.stringPropertyNames();
			for(String s : keys) {
				mimeTypes.put(s, prop.getProperty(s));
			}
			
			//reading workers properties
			prop = new Properties();
			is = Files.newInputStream(workersPath);
			prop.load(is); 
			Set<String> keysWork = prop.stringPropertyNames();
			for(String s : keysWork) {
				String path = s; 
				String fqcn = prop.getProperty(s);
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker)newObject;
				workersMap.put(path, iww);
			}
			is.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	/**
	 * Server thread method.
	 * Accepts client and submits new thread to thread pool
	 * to serve client.
	 * @author Teo Toplak
	 *
	 */
	protected class ServerThread extends Thread {
		@Override
		public void run() {
			try {
				@SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress((InetAddress)null, port));
				while(true) {
					if(stopServer == true) {
						return;
					}
					Socket toClient = serverSocket.accept();
					ClientWorker cw = new ClientWorker(toClient);
					threadPool.submit(cw);
				}

			} catch (Exception e) {
				System.out.println("Server terminated");
			}

		}
	}

	/**
	 * Client worker class.
	 * Serves one client and initializes environment 
	 * needed for serving.
	 * @author Teo Toplak
	 */
	private class ClientWorker implements Runnable {
		/**
		 * socket for serving client
		 */
		private Socket csocket;
		/**
		 * input TCP stream
		 */
		private PushbackInputStream istream;
		/**
		 * output TCP stream
		 */
		private OutputStream ostream;
		/**
		 * parameters map
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * persistent parameters in terms with one client connection
		 */
		private Map<String, String> permPrams = null;
		/**
		 * cookies made in this connection
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		/**
		 * Constructor accepting reference to socket.
		 * @param csocket socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {

				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = (csocket.getOutputStream());
				byte[] request = readRequest(istream);
				if(request==null) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				String requestStr = new String(
						request, 
						StandardCharsets.ISO_8859_1
						);
				List<String> headers = extractHeaders(requestStr);
				String[] firstLine = headers.isEmpty() ? 
						null : headers.get(0).split(" ");
				if(firstLine==null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				String method = firstLine[0].toUpperCase();
				if(!method.equals("GET")) {
					sendError(ostream, 400, "Method Not Allowed");
					return;
				}

				String version = firstLine[2].toUpperCase();
				if(!version.equals("HTTP/1.1")) {
					sendError(ostream, 505, "HTTP Version Not Supported");
					return;
				}

				String[] pathArr = firstLine[1].split("\\?");
				String path = pathArr[0];
				String paramString=null;
				
				checkSession(headers);
				
				if(pathArr.length==2) {
					paramString = pathArr[1];
					parseParameters(paramString);
				}
				Path requestedPath = Paths.get(documentRoot.toString()+path);

				File requestFile = requestedPath.toFile();


				RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);

				if(workersMap.containsKey(path)) {
					workersMap.get(path).processRequest(rc);
					return;
				}

				if(path.startsWith("/ext/")) {
					String worker = path.substring(5);
					Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(
							conventionWorkerPath+worker);
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker)newObject;
					iww.processRequest(rc);
					return;
				}

				if(!(requestedPath.toString()).contains(documentRoot.toString())) {
					sendError(ostream, 404, "Forbidden");
					return;
				}

				if(!requestFile.exists() || !requestFile.canRead()) {
					sendError(ostream, 404, "Forbidden");
					return;
				} 
				String mimeType = mimeTypes.get(getExtension(requestFile.toString()));
				if(mimeType==null) {
					//wut
				}	

				if(getExtension(requestFile.toString()).equals("smscr")) {

					String documentBody = readFromDisk(documentRoot.toString()+path);
					new SmartScriptEngine(
							new SmartScriptParser(documentBody).getDocumentNode(),
							rc
							).execute();
				} else {
					byte[] out = Files.readAllBytes(requestedPath);
					rc.setMimeType(mimeType);
					rc.setStatusCode(200);
					rc.write(out);

				}

				return;

			}catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					csocket.close();
				} catch (IOException ignorable) { }
			}
		}
	
		/**
		 * Checking session for cookies.
		 * Creates/updates cookie needed.
		 * Also updates persistent parameters map.
		 * @param headers list of lines from header input
		 */
		private synchronized void checkSession(List<String> headers) {
			SessionMapEntry entry=checkCookieSession(headers);
			permPrams=entry.map;
			sessions.put(entry.sid, entry);
		}
		
		/**
		 * Creates/updates cookie needed.
		 * @param headers headers list of lines from header input
		 * @return entry made for cookie created
		 */
		private SessionMapEntry checkCookieSession(List<String> headers) {
			for(String line : headers) {
				if(!line.startsWith("Cookie:")) {
					continue;
				}
				String cookieLine = line.replaceAll("Cookie: ", "");
				String[] cookies = cookieLine.split(";");
				for(String cookie : cookies) {
					String[] values = cookie.split("\\=");
					if(!values[0].equals("sid")) {
						continue;
					}
					String sidCandidate = values[1];
					if(sessions.containsKey(sidCandidate)) {
						SessionMapEntry entry = sessions.get(sidCandidate);
						if(entry.validUntil< System.currentTimeMillis()) {
							sessions.remove(sidCandidate);
							return createCookie();
						}
						entry.validUntil=System.currentTimeMillis()+sessionTimeout*1000;
						return entry;
					} 
					
				}
			}
			return createCookie();
		}

		/**
		 * Creates cookie and sets its parameters.
		 * Along with cookie {@link SessionMapEntry} entry is made.
		 * @return entry
		 */
		private SessionMapEntry createCookie() {
			SessionMapEntry entry = new SessionMapEntry();
			entry.map=Collections.synchronizedMap(new HashMap<String, String>());
			entry.sid=randomSid();
			entry.validUntil=System.currentTimeMillis()+sessionTimeout*1000;
			RCCookie cookie = new RCCookie("sid", entry.sid, null, address, "/");
			outputCookies.add(cookie);
			return entry;
		}
		
		/**
		 * Creates random string for cookie indetification
		 * @return string for cookie indetification
		 */
		private String randomSid() {
			String sid="";
			int sidLength = 20;
			for(int i=0;i<sidLength;i++) {
				sid+=(char)((int)'A'+sessionRandom.nextDouble()*((int)'Z'-(int)'A'+1));
			}
			return sid;
		}

		/**
		 * Reads file from disk with path given.
		 * @param string path to file
		 * @return file in form of string 
		 */
		private String readFromDisk(String string) {
			byte[] arr =null;
			try {
				arr = Files.readAllBytes(Paths.get(string));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new String(arr,StandardCharsets.UTF_8);
		}

		/**
		 * Parses parameters to parameters map.
		 * @param paramString string representing parameters
		 */
		private void parseParameters(String paramString) {
			String[] paramArr = paramString.split("&");
			for(int i=0;i<paramArr.length;i++) {
				String[] arr = paramArr[i].split("\\=");
				params.put(arr[0], arr[1]);
			}
		}

	}

	/**
	 * Entry made along each cookie for proper indetification
	 * @author Teo Toplak 
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Indetification value for cookie
		 */
		String sid;
		/**
		 * Time of expiration for cookie
		 */
		long validUntil;
		/**
		 * persistent parameters map for entry
		 */
		Map<String,String> map;
	}

	/**
	 * Gets extension for string given.
	 * String in terms of path to some file.
	 * @param s path to some file
	 * @return extension of file
	 */
	private String getExtension(String s) {
		int dotPosition = s.lastIndexOf(".");
		String extension = s.substring(dotPosition+1,s.length());
		return extension;

	}

	/**
	 * Sends proper error to stream given.
	 * @param cos outut stream
	 * @param statusCode status of error
	 * @param statusText status error text
	 * @throws IOException exception made when error with stream 
	 * occured
	 */
	private static void sendError(OutputStream cos, 
			int statusCode, String statusText) throws IOException {

		cos.write(
				("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
						"Server: simple java server\r\n"+
						"Content-Type: text/plain;charset=UTF-8\r\n"+
						"Content-Length: 0\r\n"+
						"Connection: close\r\n"+
						"\r\n").getBytes(StandardCharsets.US_ASCII)
				);
		cos.flush();

	}

	/**
	 * Extracts header lines from header string. 
	 * @param requestHeader header string
	 * @return list of header lines
	 */
	private static List<String> extractHeaders(
			String requestHeader) {

		List<String> headers = new ArrayList<String>();
		String currentLine = null;
		for(String s : requestHeader.split("\n")) {
			if(s.isEmpty()) break;
			char c = s.charAt(0);
			if(c==9 || c==32) {
				currentLine += s;
			} else {
				if(currentLine != null) {
					headers.add(currentLine);
				}
				currentLine = s;
			}
		}
		if(!currentLine.isEmpty()) {
			headers.add(currentLine);
		}
		return headers;
	}

	/**
	 * Reads request from client and parsers it properly
	 * @param is input stream
	 * @return byte array of parsed string 
	 * @throws IOException  exception mad when parsing string
	 */
	private static byte[] readRequest(InputStream is) 
			throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int state = 0;
		l:		while(true) {
			int b = is.read();
			if(b==-1) return null;
			if(b!=13) {
				bos.write(b);
			}
			switch(state) {
			case 0: 
				if(b==13) { state=1; } else if(b==10) state=4;
				break;
			case 1: 
				if(b==10) { state=2; } else state=0;
				break;
			case 2: 
				if(b==13) { state=3; } else state=0;
				break;
			case 3: 
				if(b==10) { break l; } else state=0;
				break;
			case 4: 
				if(b==10) { break l; } else state=0;
				break;
			}
		}
		return bos.toByteArray();
	}
}
