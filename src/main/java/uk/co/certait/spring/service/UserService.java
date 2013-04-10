package uk.co.certait.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.certait.spring.data.domain.User;
import uk.co.certait.spring.data.domain.specification.UserSpecifications;
import uk.co.certait.spring.data.repository.UserRepository;

import com.mysema.query.types.Predicate;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Transactional(readOnly = true)
	public User findByEmailAddress(String emailAddress) {
		return repository.findOne(UserSpecifications.userHasEmailAddress(emailAddress));
	}

	@Transactional(readOnly = true)
	public List<User> getAllActiveUsers() {
		return repository.findAll(UserSpecifications.userIsActive());
	}

	@Transactional(readOnly = true)
	public Page<User> getAllActiveUsers(Pageable pageable) {
		return repository.findAll(UserSpecifications.userIsActive(), pageable);
	}

	@Transactional(readOnly = true)
	public List<User> getUsersByCriteria(Predicate predicate) {
		return repository.findAll(predicate);
	}

	@Transactional(readOnly = true)
	public Page<User> getUsersByCriteria(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	@Transactional
	public User updateUser(User user) {
		return repository.save(user);
	}
}
