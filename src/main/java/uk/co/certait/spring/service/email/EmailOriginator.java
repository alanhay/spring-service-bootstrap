package uk.co.certait.spring.service.email;

public class EmailOriginator extends EmailRecipient {

	private String replyToAddress;

	public EmailOriginator(String name, String address) {
		super(name, address);
	}

	public EmailOriginator(String name, String address, String replyToAddress) {
		this(name, address);
		this.replyToAddress = replyToAddress;
	}

	public String getReplyToAddress() {
		return replyToAddress;
	}
}
