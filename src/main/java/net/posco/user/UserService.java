package net.posco.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> listAll() {
		return (List<User>) userRepository.findAll();
	}

	// Pagination
	public Page<User> listByPage(int pageNum, int pageSize, String sortField, String sortDirection, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDirection.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		if (keyword != null) {
			return userRepository.findAll(keyword, pageable);
		}
		return userRepository.findAll(pageable);
	}

	// Check if email is unique
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepository.getUserByEmail(email);
		if (userByEmail == null)
			return true;
		boolean isCreatingNew = (id == null);
		if (isCreatingNew) {
			if (userByEmail != null)
				return false;
		} else if (userByEmail.getId() != id)
			return false;
		return true;
	}

	// Save user information
	public User save(User user) {
		boolean isUpdatingUser = (user.getId() != null);

		// Check if user is existing
		if (isUpdatingUser) {
			User existingUser = userRepository.findById(user.getId()).get();
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				System.out.println("Password is: " + user.getPassword());
				encodePassword(user);
			}
		} else {
			System.out.println("Password is: " + user.getPassword());
			encodePassword(user);
		}
		return userRepository.save(user);
	}

	// Encode the password
	private void encodePassword(User user) {
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
	}

	// Delete user by id
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepository.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		userRepository.deleteById(id);
	}

	// Update the active status of the user
	public void updateUserActiveStatus(Integer id, boolean enabled) {
		userRepository.updateUserActiveStatus(id, enabled);
	}
}
