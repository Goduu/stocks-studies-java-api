package com.goduu.stocksstudies.config;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.goduu.stocksstudies.repository.PostRepository;
import com.goduu.stocksstudies.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userReposiroty;
	
	@Autowired
	private UserRepository tickUserRepository;

	@Autowired
	private PostRepository postReposiroty;

	@Override
	public void run(String... arg0) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		// userReposiroty.deleteAll();
		// postReposiroty.deleteAll();
		
		// User maria = new User(null, "Maria Brown", "maria@gmail.com", "passmaria");
		// User alex = new User(null, "Alex Green", "alex@gmail.com", "passalex");
		// User bob = new User(null, "Bob Grey", "bob@gmail.com", "passbob");
		
		// userReposiroty.saveAll(Arrays.asList(maria, alex, bob));

		// Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthDTO(maria));
		// Post post2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz hoje!", new AuthDTO(maria));

		// CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdf.parse("21/03/2018"), new AuthDTO(alex));
		// CommentDTO c2 = new CommentDTO("Aproveite", sdf.parse("22/03/2018"), new AuthDTO(bob));
		// CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("23/03/2018"), new AuthDTO(alex));
		
		// post1.getComments().addAll(Arrays.asList(c1, c2));
		// post2.getComments().addAll(Arrays.asList(c3));
		
		// postReposiroty.saveAll(Arrays.asList(post1, post2));
		
		// maria.getPosts().addAll(Arrays.asList(post1, post2));
		// userReposiroty.save(maria);
	}

}
