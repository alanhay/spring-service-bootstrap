/*
EmailAttachment

Created 28 Jan 2011by alanhay

Copyright Certa IT Solutions.
 */

package uk.co.certait.spring.service.email;

public class EmailAttachment {
	private String filePath;

	public EmailAttachment(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}
}
