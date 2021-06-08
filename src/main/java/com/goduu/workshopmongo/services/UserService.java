package com.goduu.workshopmongo.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.goduu.workshopmongo.dto.UserRegistryDTO;
import com.goduu.workshopmongo.models.User;
import com.goduu.workshopmongo.models.UserPrincipal;
import com.goduu.workshopmongo.repository.UserRepository;
import com.goduu.workshopmongo.services.exception.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenAuthenticationService authService;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public User register(User obj) {
		return repo.insert(obj);
	}

	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

	public User update(User obj) {
		User newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}

	public User fromRegistryDTO(UserRegistryDTO objDto) {
		User user = new User();
		System.out
				.println("user------------------------" + objDto.getEmail() + objDto.getName() + objDto.getPassword());
		user.setName(objDto.getName());
		user.setEmail(objDto.getEmail());
		user.setHashedpw(passwordEncoder.encode(objDto.getPassword()));

		return user;
	}

	/**
	 * Authenticates the user by storing an entry in the sessions collection. In
	 * case authentication is unsuccessful, return null User.
	 *
	 * @param email    - identifies the user
	 * @param password - user password
	 * @param results  - map to collect any relevant message
	 * @return User object that matches the provided email and password.
	 */
	public User authenticate(String email, String password, Map<String, String> results) {
		String jwt = generateUserToken(email, password);
		if (!userDao.createUserSession(email, jwt)) {
			results.put("msg", "unable to login user");
			return null;
		}
		results.put("auth_token", jwt);
		return userDao.getUser(email);
	}

	public User findUserByName(String name) {
		return repo.findUserByName(name);
	}

	private String generateUserToken(String email, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		return authService.generateToken(authentication);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findUserByName(username);
		if (user == null || user.isEmpty()) {
			throw new UsernameNotFoundException("Cannot find username.");
		}
		return UserPrincipal.create(user);

	}
}
