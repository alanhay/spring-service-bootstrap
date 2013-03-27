package uk.co.certait.spring.service;

import org.springframework.beans.factory.annotation.Autowired;

import uk.co.certait.spring.data.domain.User;
import uk.co.certait.spring.data.repository.UserRepository;

public class PasswordResetService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	public void resetUserPassword(User user) {
		// set salt
		// set password
		userRepository.save(user);
	}
}
