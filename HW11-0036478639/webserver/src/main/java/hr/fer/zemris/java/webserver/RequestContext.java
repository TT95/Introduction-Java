package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class made for proper context made for responding to client.
 * Creates HTTP header when sending information to client.
 * Offers methods for setting context properties before creating header.
 * @author Teo Toplak
 *
 */
public class RequestContext {
	
	/**
	 * Output stream 
	 */
	OutputStream outputStream;
	/**
	 * charset for creating response
	 */
	Charset charset;
	/**
	 * encoding for response
	 */
	String encoding;
	/**
	 * status code for header
	 */
	int statusCode;
	/**
	 * status text for header
	 */
	String statusText;
	/**
	 * mime type for header
	 */
	String mimeType;
	/**
	 * parameters retreved from server
	 */
	private Map<String,String> parameters;
	/**
	 * temporary parameters retreved from server
	 */
	private Map<String,String> temporaryParameters;
	/**
	 * persistent parameters retreved from server
	 */
	private Map<String,String> persistentParameters;
	/**
	 * cookies retreved from server
	 */
	private List<RCCookie> outputCookies;
	/**
	 * tells if header has been created 
	 */
	private boolean headerGenerated;
	

	/**
	 * Constructor initializing all parameters needed for creation 
	 * of proper header. 
	 * @param outputStream output stream
	 * @param parameters parameters map
	 * @param persistentParameters persistent parameters
	 * @param outputCookies cookies for response
	 */
	public RequestContext(OutputStream outputStream, 
			Map<String,String> parameters, 
			Map<String,String> persistentParameters,
			List<RCCookie> outputCookies) {
	
		if(outputStream == null) {
			System.out.println("Wrong outputStream argument!");
			System.exit(1);
		}
		this.outputStream=outputStream;
		if(parameters == null) {
			parameters = new HashMap<>();
		} else {
			this.parameters=parameters;
		}
		if(persistentParameters == null) {
			persistentParameters = new HashMap<>();
		} else {
			this.persistentParameters=persistentParameters;
		}
		if(outputCookies == null) {
			outputCookies = new ArrayList<>();
		} else {
			this.outputCookies=outputCookies;
		}
		
		encoding="UTF-8";
		statusCode=200;
		statusText="OK";
		mimeType="text/html";
		headerGenerated=false;
		
		temporaryParameters = new HashMap<>();
	}
	
	/**
	 * Method that retrieves value from parameters map 
	 * (or null if no association exists)
	 * @param name key string
	 * @return value string
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in parameters map
	 * @return set of all names
	 */
	public Set<String> getParameterNames() {
		Set<String> set = new HashSet<String>();
		parameters.forEach((k,v) -> set.add(k));
		return set;
	}
	
	/**
	 * Method that retrieves value from persistentParameters map
	 *  (or null if no association exists
	 * @param name key sting
	 * @return value string
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in persistent parameters map
	 * @return set of all names
	 */
	public Set<String> getPersistentParameterNames() {
		Set<String> set = new HashSet<String>();
		persistentParameters.forEach((k,v) -> set.add(k));
		return set;
	}
	
	/**
	 * Method that stores a value to persistentParameters map
	 * @param name key string
	 * @param value value string
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	
	/**
	 * Method that removes a value from persistentParameters map
	 * @param name key string
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Method that retrieves value from temporaryParameters map
	 *  (or null if no association exists)
	 * @param name key string
	 * @return value string
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in temporary parameters map
	 * @return set of key string names
	 */
	public Set<String> getTemporaryParameterNames() {
		Set<String> set = new HashSet<String>();
		temporaryParameters.forEach((k,v) -> set.add(k));
		return set;
	}
	
	/**
	 * Method that stores a value to temporaryParameters map
	 * @param name key string
	 * @param value value string
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Method that removes a value from temporaryParameters map
	 * @param name string key
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Method writing response to client.
	 * Header will be generated with first call of this method
	 * @param data data to be written (as byte array)
	 * @return reference to this object
	 * @throws IOException when error with stream appeared
	 */
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated) {
			byte[] header = generateHeader();
			outputStream.write(header);
			headerGenerated=true;
			outputStream.flush();
		}
		outputStream.write(data);
		outputStream.flush();
		return this;
	}

	/**
	 * Method writing response to client.
	 * Header will be generated with first call of this method
	 * @param text data to be written (as string)
	 * @return reference to this object
	 * @throws IOException when error with stream appeared
	 */
	public RequestContext write(String text) throws IOException {
		if(!headerGenerated) {
			byte[] header = generateHeader();
			outputStream.write(header);
			headerGenerated=true;
			outputStream.flush();
		}
		return write(text.getBytes(charset));
	}
	
	/**
	 * Method generating header as array of bytes
	 * @return header as array of bytes
	 */
	private byte[] generateHeader() {
		charset = Charset.forName(encoding);
		String str = "HTTP/1.1 "+ statusCode + " " + statusText+"\r\n";
		str+= "Content-Type: "+mimeType+";";
		if(mimeType.startsWith("text/")) {
			str+=" charset="+encoding;
		}
		str+="\r\n";
		if(outputCookies!=null) {
			for (RCCookie cookie : outputCookies) {
				str += "Set-Cookie: " + cookie.getName() + "=" + cookie.getValue()+";";
				if(cookie.getDomain()!=null) {
					str+=" Domain=" + cookie.getDomain()+";";
				}
				if(cookie.getPath()!=null) {
					str+=" Path=" + cookie.getPath()+";";
				}
				if(cookie.getMaxAge()!=null) {
					str+=" Max-Age="+cookie.getMaxAge()+";";
				}
				str+=" HttpOnly";
				str+="\r\n";
			}
		}
		str+="\r\n";
		return str.getBytes(StandardCharsets.ISO_8859_1);
	}
	
	/**
	 * Checks if header was already generated.
	 * If header was already generated method will
	 * throw exception
	 */
	private void checkLegalInput() {
		if(headerGenerated==true) {
			throw new RuntimeException();
		}
	}

	/**
	 * Adds {@link RCCookie} parameter to this 
	 * request context
	 * @param cookie cookie added
	 */ 
	public void addRCCookie(RCCookie cookie) {
		checkLegalInput();
		outputCookies.add(cookie);
	}
	 
	/**
	 * Sets encoding parameter to this 
	 * request context
	 * @param encoding the encoding to set
	 */
	 public void setEncoding(String encoding) {
		 checkLegalInput();
		 this.encoding = encoding;
	 }


	/**
	 * Sets status code parameter to this 
	 * request context
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		checkLegalInput();
		this.statusCode = statusCode;
	}


	/**
	 * Sets status text parameter to this 
	 * request context
	 * @param statusText the statusText to set
	 */
	public void setStatusText(String statusText) {
		checkLegalInput();
		this.statusText = statusText;
	}


	/**
	 * Sets mime type parameter to this 
	 * request context
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		checkLegalInput();
		this.mimeType = mimeType;
	}


	/**
	 * Class representing cookie.
	 * Cookie is determened with name, value
	 * domain, path and maximum age for cookie
	 * @author Teo Toplak
	 *
	 */
	public static class RCCookie {
		/**
		 * cookie name
		 */
		String name;
		/**
		 * cookie value
		 */
		String value;
		/**
		 * cookie domain
		 */
		String domain;
		/**
		 * cookie path
		 */
		String path;
		/**
		 * maximum age for cookie
		 */
		Integer maxAge;

		/**
		 * Constructor setting all parameters for cookie.
		 * @param name cookie name
		 * @param value cookie value
		 * @param maxAge maximum age for cookie
		 * @param domain cookie domain
		 * @param path cookie path
		 */
		public RCCookie(String name, String value, Integer maxAge,
				String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Returns name for cookie
		 * @return cookie name
		 */
		public String getName() {
			return name;
		}
		/**
		 * Returns cookie value
		 * @return cookie
		 */
		public String getValue() {
			return value;
		}
		/**
		 * Returns cookie domain
		 * @return cookie domain
		 */
		public String getDomain() {
			return domain;
		}
		/**
		 * Returns cookie path
		 * @return cookie path
		 */
		public String getPath() {
			return path;
		}
		/**
		 * Returns maximum age for cookie
		 * @return maximum for cookie
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		
		
	}
	
	
}
