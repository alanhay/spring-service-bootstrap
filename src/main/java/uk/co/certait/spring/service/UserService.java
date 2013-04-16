package uk.co.certait.spring.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.certait.spring.data.domain.QUser;
import uk.co.certait.spring.data.domain.User;
import uk.co.certait.spring.data.domain.specification.UserSpecifications;
import uk.co.certait.spring.data.repository.UserRepository;

import com.mysema.query.types.Predicate;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Transactional(readOnly = true)
	@PreAuthorize("#id == authentication.principal.id or hasRole('ROLE_ADMIN')")
	public User findById(Long id) {
		return repository.findOne(id);
	}
	
	@Transactional(readOnly = true)
	public User findByEmailAddress(String emailAddress){
		try{
			return repository.findOne(QUser.user.emailAddress.eq(emailAddress));
		}
		catch(NoResultException nex){
			return null;
		}
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
	
	@Transactional(readOnly = true)
	public List<String> getUniqueUserSurnames(String query, int limit){
		return repository.findUniqueUserSurnames(query + "%", new PageRequest(0, limit));
	}
	@Transactional(readOnly = true)
	public List<String> getUniqueUserLocations(String query, int limit){
		return repository.findUniqueUserLocations(query + "%", new PageRequest(0, limit));
	}

	@Transactional
	public User updateUser(User user) {
		return repository.save(user);
	}
}
