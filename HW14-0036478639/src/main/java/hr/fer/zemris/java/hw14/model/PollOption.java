package hr.fer.zemris.java.hw14.model;

/**
 * Simple implementation of poll voting option
 * @author Teo Toplak
 *
 */
public class PollOption {

	/**
	 * id
	 */
	private long id;
	/**
	 * title
	 */
	private String optionTitle;
	/**
	 * link 
	 */
	private String optionLink;
	/**
	 * id
	 */
	private long pollID;
	/**
	 * votes count
	 */
	private String votesCount;
	
	/**
	 * Constructor taking no arguments
	 */
	public PollOption() {
	}

	/**
	 * id getter
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * id setter
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * title getter
	 * @return the optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * title setter
	 * @param optionTitle the optionTitle to set
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * link getter
	 * @return the optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * link setter
	 * @param optionLink the optionLink to set
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * id getter
	 * @return the pollID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * id setter
	 * @param pollID the pollID to set
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * votes count getter
	 * @return the votesCount
	 */
	public String getVotesCount() {
		return votesCount;
	}

	/**
	 * votes count setter
	 * @param votesCount the votesCount to set
	 */
	public void setVotesCount(String votesCount) {
		this.votesCount = votesCount;
	}
	
	
	
}
