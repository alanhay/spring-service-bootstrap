package uk.co.certait.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.certait.spring.data.domain.QUser;
import uk.co.certait.spring.data.domain.User;
import uk.co.certait.spring.data.repository.UserRepository;

import com.mysema.query.types.Predicate;

@Service
@Transactional(readOnly = true)
public class UserService {

	@Autowired
	private UserRepository repository;

	@PreAuthorize("#id == authentication.principal.id or hasRole('ROLE_ADMIN')")
	public User findById(Long id) {
		return repository.findOne(id);
	}

	public User findByEmailAddress(String emailAddress) {
		return repository.findOne(QUser.user.emailAddress.eq(emailAddress));
	}

	public List<User> findAllActiveUsers() {
		return repository.findAll(QUser.user.deleted.eq(false));
	}

	public Page<User> findAllActiveUsers(Pageable pageable) {
		return repository.findAll(QUser.user.deleted.eq(false), pageable);
	}

	public List<User> findUsersByCriteria(Predicate predicate) {
		return repository.findAll(predicate);
	}

	public Page<User> getUsersByCriteria(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public List<String> getUniqueUserSurnames(String query, int limit) {
		return repository.findUniqueUserSurnames(query + "%", new PageRequest(0, limit));
	}

	public List<String> getUniqueUserLocations(String query, int limit) {
		return repository.findUniqueUserLocations(query + "%", new PageRequest(0, limit));
	}

	@Transactional
	public User updateUser(User user) {
		return repository.save(user);
	}
}
