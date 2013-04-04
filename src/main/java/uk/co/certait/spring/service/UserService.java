package uk.co.certait.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.certait.spring.data.domain.User;
import uk.co.certait.spring.data.domain.specification.UserSpecifications;
import uk.co.certait.spring.data.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Transactional(readOnly = true)
	public User findByEmailAddress(String emailAddress) {
		return repository.findOne(UserSpecifications.userHasEmailAddress(emailAddress));
	}
	
	@Transactional
	public User updateUser(User user){
		return repository.save(user);
	}
}
