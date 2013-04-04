/*
Email

Created 28 Jan 2011by alanhay

Copyright Certa IT Solutions.
 */

package uk.co.certait.spring.service.email;

import java.util.ArrayList;
import java.util.List;

public class Email {

	private EmailOriginator originator;

	private String subject;
	private String messageBody;
	private EmailContentType contentType;

	private List<EmailRecipient> toRecipients;
	private List<EmailRecipient> ccRecipients;
	private List<EmailRecipient> bccRecipients;
	private List<EmailAttachment> attachments;

	public Email() {
		toRecipients = new ArrayList<EmailRecipient>();
		attachments = new ArrayList<EmailAttachment>();
		attachments = new ArrayList<EmailAttachment>();
	}

	public EmailOriginator getOriginator() {
		return originator;
	}

	public void setOriginator(EmailOriginator originator) {
		this.originator = originator;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public EmailContentType getContentType() {
		return contentType;
	}

	public void setContentType(EmailContentType contentType) {
		this.contentType = contentType;
	}

	public List<EmailRecipient> getToRecipients() {
		return toRecipients;
	}

	public void setToRecipients(List<EmailRecipient> toRecipients) {
		this.toRecipients = toRecipients;
	}

	public List<EmailRecipient> getCcRecipients() {
		return ccRecipients;
	}

	public void setCcRecipients(List<EmailRecipient> ccRecipients) {
		this.ccRecipients = ccRecipients;
	}

	public List<EmailRecipient> getBccRecipients() {
		return bccRecipients;
	}

	public void setBccRecipients(List<EmailRecipient> bccRecipients) {
		this.bccRecipients = bccRecipients;
	}

	public List<EmailAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<EmailAttachment> attachments) {
		this.attachments = attachments;
	}

	public void addAttachment(EmailAttachment attachment) {
		attachments.add(attachment);
	}

	public void addToRecipient(EmailRecipient recipient) {
		toRecipients.add(recipient);
	}

	public boolean hasCcRecipients() {
		return ccRecipients != null && ccRecipients.size() > 0;
	}

	public boolean hasBccRecipients() {
		return bccRecipients != null && bccRecipients.size() > 0;
	}

	public boolean hasAttachments() {
		return attachments != null && attachments.size() > 0;
	}

	public String[] getToAddresses() {
		return getInternetAddresses(toRecipients);
	}

	public String[] getCcAddresses() {
		return getInternetAddresses(ccRecipients);
	}

	public String[] getBccAddresses() {
		return getInternetAddresses(bccRecipients);
	}

	private String[] getInternetAddresses(List<EmailRecipient> recipients) {
		String[] addresses = new String[toRecipients.size()];
		int index = 0;

		for (EmailRecipient recipient : recipients) {
			addresses[index] = recipient.getInternetAddress();
			++index;
		}

		return addresses;
	}
}
