package uk.co.certait.spring.service.email;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service
public class EmailService {
	private static final Logger logger = Logger.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private VelocityEngine velocityEngine;

	public void sendEmail(Email email) {
		try {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			setBaseProperties(helper, email);
			addAttachments(helper, email);

			sender.send(helper.getMimeMessage());
		}
		catch (Exception ex) {
			logger.error("Error sending mail", ex);
		}
	}

	private void setBaseProperties(MimeMessageHelper helper, Email email) throws MessagingException, UnsupportedEncodingException {
		helper.setFrom(email.getOriginator().getInternetAddress());
		helper.setSubject(email.getSubject());
		helper.setText(createMessage(email.getMessageBody()), email.getContentType().equals(EmailContentType.HTML));
		helper.setTo(email.getToAddresses());

		if (!StringUtils.isEmpty(email.getOriginator().getReplyToAddress())) {
			helper.setReplyTo(email.getOriginator().getReplyToAddress());
		}

		if (email.hasCcRecipients()) {
			helper.setCc(email.getCcAddresses());
		}

		if (email.hasBccRecipients()) {
			helper.setBcc(email.getBccAddresses());
		}
	}

	private void addAttachments(MimeMessageHelper helper, Email email) throws MessagingException {
		if (email.hasAttachments()) {
			for (EmailAttachment attachment : email.getAttachments()) {
				File f = new File(attachment.getFilePath());
				helper.addAttachment(f.getName(), f);
			}
		}
	}

	protected String createMessage(String message) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("content", message);
		model.put("path", "http://www.braids-united.co.uk");

		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/templates/email/emailWrapper.vm", model);
	}

	public void setJavaMailSenderImpl(JavaMailSenderImpl sender) {
		this.sender = sender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
}
