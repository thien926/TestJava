package com.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.exam.service.UserService;

@SpringBootApplication
public class ExamServerApplication implements CommandLineRunner{
	
	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(ExamServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting Code");
		
//		User user = new User();
//		user.setFirstName("Thien");
//		user.setLastName("Ngoc");
//		user.setUsername("thien111");
//		user.setPassword("123456");
//		user.setEmail("thien113@gmail.com");
//		user.setProfile("default.png");
//		
//		Role role1 = new Role();
////		role1.setId(1L);
//		role1.setRoleName("MANAGER");
//		
//		Set<UserRole> userRoles = new HashSet<>();
//		UserRole userRole = new UserRole();
//		userRole.setRole(role1);
//		userRole.setUser(user);
//		userRoles.add(userRole);
//		
//		User user1 = this.userService.createUser(user, userRoles);
//		System.out.println(user1.getUsername());
	}
}
