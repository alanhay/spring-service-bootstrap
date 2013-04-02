package uk.co.certait.spring.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.certait.spring.data.domain.User;
import uk.co.certait.spring.data.repository.UserRepository;
import uk.co.certait.spring.service.util.BCryptPasswordEncoder;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User findByEmailAddress(String emailAddress) {
		return repository.findByEmailAddress(emailAddress);
	}

	@Transactional
	public void resetUserPassword(String emailAddress) {

		User user = repository.findByEmailAddress(emailAddress);
		String password = RandomStringUtils.randomAlphabetic(10);
		System.out.println(password);
		user.setPassword(passwordEncoder.encodePassword(password, null));

		//send email
	}
}
