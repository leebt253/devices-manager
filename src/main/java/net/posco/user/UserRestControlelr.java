package net.posco.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestControlelr {
    @Autowired
	private UserService userService;
	
	@PostMapping("/users/check_email")
	public String checkExistEmail(@Param("id") Integer id, @Param("email") String email) {
		return userService.isEmailUnique(id, email) ? "Email OK" : "Email is existed";
	}
	
}
