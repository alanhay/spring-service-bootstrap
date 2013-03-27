package uk.co.certait.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.certait.spring.data.domain.User;
import uk.co.certait.spring.data.repository.UserRepository;

@Service
public class UserRegistrationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	public void registerUser(User user) {
		// set salt
		userRepository.save(user);
	}
}
