package com.mcb;

import com.mcb.users.model.User;
import com.mcb.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class McbApplicationTests {
	@Autowired
	private  UserRepository repository;


	@Test
	void contextLoads() {
	}



	@Test
	public void testDisableUser() {
		long userId = 1;
		repository.disableUser(userId);
	}

	@Test
	public void testUpdateFailedAttempt() {
		String email = "juyelhushen@gmail.com";
		int failedAttempt = 2;
		repository.updateFailedAttempt(failedAttempt, email);
		long id = 1;
		User user = repository.findById(id).get();
		assertEquals(failedAttempt, user.getFailedAttempt());
	}

}
