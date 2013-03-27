package uk.co.certait.spring.service.util;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class PasswordDigest {

	public static void main(String[] args) {
		PasswordEncoder endcoder = new ShaPasswordEncoder(256);
		System.out.println(endcoder.encodePassword("password", "salt"));
	}
}
