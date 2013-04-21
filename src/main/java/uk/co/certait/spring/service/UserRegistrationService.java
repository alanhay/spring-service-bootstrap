package uk.co.certait.spring.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import uk.co.certait.spring.data.domain.Role;
import uk.co.certait.spring.data.domain.User;
import uk.co.certait.spring.data.repository.UserRepository;
import uk.co.certait.spring.service.email.Email;
import uk.co.certait.spring.service.email.EmailContentType;
import uk.co.certait.spring.service.email.EmailOriginator;
import uk.co.certait.spring.service.email.EmailRecipient;
import uk.co.certait.spring.service.email.EmailService;
import uk.co.certait.spring.service.util.BCryptPasswordEncoder;

@Service
public class UserRegistrationService {

	@Value("${registration.confirmation.email.from.address}")
	private String registrationConfirmationEmailFromAddress;

	@Value("${registration.confirmation.email.subject}")
	private String registrationConfirmationEmailSubject;

	@Autowired
	private EmailService emailService;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void registerUser(User user) {
		persistUser(user);
		emailService.sendEmail(createRegistrationEmail(user));
	}

	@Transactional
	private void persistUser(User user) {
		user.setRegistrationDate(new Date());
		user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
		user.addRole(new Role("ROLE_USER"));
		user.addRole(new Role("ROLE_ADMIN"));
		
		userRepository.save(user);
	}

	private Email createRegistrationEmail(User user) {

		Email mail = new Email();
		mail.setOriginator(new EmailOriginator("Admin", registrationConfirmationEmailFromAddress));
		mail.setSubject(registrationConfirmationEmailSubject);
		mail.setContentType(EmailContentType.HTML);
		mail.setMessageBody(createRegistrationEmailContent(user));
		mail.addToRecipient(new EmailRecipient(user.getName(), user.getEmailAddress()));

		return mail;
	}

	private String createRegistrationEmailContent(User user) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", user);

		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/templates/email/registration.vm", model);
	}
}
