package uk.co.certait.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.certait.spring.data.domain.specification.UserSpecifications;
import uk.co.certait.spring.data.repository.UserRepository;

@Service(value = "authenticationService")
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return userRepository.findOne(UserSpecifications.userHasEmailAddress(userName).and(UserSpecifications.userIsActive()));
	}
}
