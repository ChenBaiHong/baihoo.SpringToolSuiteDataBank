package com.baihoo.bootstrap.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.baihoo.bootstrap.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	@Test
	public void testFindByUsername() {
		User user = userRepository.findByUsername("baihoo");
		String encodePwd = new BCryptPasswordEncoder().encode("12345");
		System.out.println("================================================================================");
		System.out.println(encodePwd);
		//$2a$10$qcEMlD0TEdO4I5uE4OBpI.2ijZC8pZ7hJuFxiZofSLacLJfHWBe6O
		System.out.println("================================================================================");
	}

}
