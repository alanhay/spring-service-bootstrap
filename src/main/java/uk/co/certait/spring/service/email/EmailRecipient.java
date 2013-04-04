/*
EMailRecipient

Created 28 Jan 2011by alanhay

Copyright Certa IT Solutions.
 */

package uk.co.certait.spring.service.email;

public class EmailRecipient {
	private String name;
	private String address;

	public EmailRecipient(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getInternetAddress() {
		String formatted;

		if (name != null) {
			formatted = name + "<" + address + ">";
		}
		else {
			formatted = address;
		}

		return formatted;
	}
}
