package com.goduu.stocksstudies.services;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.goduu.stocksstudies.dto.UserRegistryDTO;
import com.goduu.stocksstudies.models.User;
import com.goduu.stocksstudies.models.UserPrincipal;
import com.goduu.stocksstudies.repository.UserRepository;
import com.goduu.stocksstudies.services.exception.ObjectNotFoundException;

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
		obj.getRoles().add("user");
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
		user.setName(objDto.getName());
		user.setEmail(objDto.getEmail());
		user.setPassword(passwordEncoder.encode(objDto.getPassword()));

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
	 * @throws UnsupportedEncodingException
	 */
	public User authenticate(String email, String password, Map<String, String> results) throws UnsupportedEncodingException {
		String jwt = generateUserToken(email, password);
		// if (!userDao.createUserSession(email, jwt)) {
		// 	results.put("msg", "unable to login user");
		// 	return null;
		// }
		results.put("authToken", jwt);
		return repo.findUserByEmail(email);
	}

	public User findUserByName(String name) {
		return repo.findUserByName(name);
	}

	private String generateUserToken(String email, String password) throws UnsupportedEncodingException {
		UsernamePasswordAuthenticationToken usat = new UsernamePasswordAuthenticationToken(email, password);
		Authentication authentication = authenticationManager
				.authenticate(usat);
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


	
    // if (!passwordEncoder.matches(password, hpwd)) {
	// 	results.put("msg", "passwords do not match");
	// 	return false;
	//   }
}
