package uk.co.certait.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uk.co.certait.spring.data.repository.UserRepository;

@Service(value = "authenticationService")
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return userRepository.findByEmailAddress(userName);
		
		// generatePassword();

		/*
		 * User user = new User(); user.setForename("Alan");
		 * user.setSurname("Smith"); user.setEmailAddress("alan@smith.net");
		 * 
		 * user.setPassword(
		 * "c439140800b343398cce9ab114a561e79e19434f9093bf330937fff145e51b95");
		 * user.setSalt("123456789"); user.addAuthority("ROLE_USER");
		 */
		// user.addAuthority("ROLE_ADMIN");
	}

	@SuppressWarnings("unused")
	private void generatePassword() {
		PasswordEncoder e = new ShaPasswordEncoder(256);
		System.out.println(e.encodePassword("password", "123456789"));
	}
}
