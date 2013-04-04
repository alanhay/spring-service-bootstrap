package uk.co.certait.spring.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import uk.co.certait.spring.data.domain.User;
import uk.co.certait.spring.data.domain.specification.UserSpecifications;
import uk.co.certait.spring.data.repository.UserRepository;
import uk.co.certait.spring.service.email.Email;
import uk.co.certait.spring.service.email.EmailContentType;
import uk.co.certait.spring.service.email.EmailOriginator;
import uk.co.certait.spring.service.email.EmailRecipient;
import uk.co.certait.spring.service.email.EmailService;
import uk.co.certait.spring.service.util.BCryptPasswordEncoder;

@Service
public class ResetPasswordService {

	@Value("${reset.password.email.from.address}")
	private String resetPasswordEmailFromAddress;

	@Value("${reset.password.email.subject}")
	private String resetPasswordEmailSubject;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Autowired
	private VelocityEngine velocityEngine;

	@Transactional
	public void resetUserPassword(String emailAddress) throws UserNotFoundException {

		User user = userRepository.findOne(UserSpecifications.userHasEmailAddress(emailAddress));

		if (user == null) {
			throw new UserNotFoundException("No User found with email address " + emailAddress);
		}

		String password = RandomStringUtils.randomAlphabetic(10);
		user.setPassword(passwordEncoder.encodePassword(password, null));

		emailService.sendEmail(createResetPasswordEmail(user, password));
	}

	private Email createResetPasswordEmail(User user, String password) {

		Email mail = new Email();
		mail.setOriginator(new EmailOriginator("Admin", resetPasswordEmailFromAddress));
		mail.setSubject(resetPasswordEmailSubject);
		mail.setContentType(EmailContentType.HTML);
		mail.setMessageBody(createResetPasswordEmailContent(user, password));
		mail.addToRecipient(new EmailRecipient(user.getName(), user.getEmailAddress()));

		return mail;
	}

	private String createResetPasswordEmailContent(User user, String password) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", user);
		model.put("password", password);

		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/templates/email/resetPassword.vm", model);
	}

	public class UserNotFoundException extends Exception {
		public UserNotFoundException(String message) {
			super(message);
		}
	}
}
