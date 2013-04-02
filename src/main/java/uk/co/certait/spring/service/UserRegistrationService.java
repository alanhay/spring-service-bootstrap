package uk.co.certait.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.certait.spring.data.domain.Role;
import uk.co.certait.spring.data.domain.User;
import uk.co.certait.spring.data.repository.UserRepository;
import uk.co.certait.spring.service.util.BCryptPasswordEncoder;

@Service
public class UserRegistrationService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public void registerUser(User user) {

		user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
		user.addRole(new Role("ROLE_USER"));
		
		userRepository.save(user);
	}
}
