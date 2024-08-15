package io.javabrains.tinder_ai_backend;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDateTime;
import io.javabrains.tinder_ai_backend.conversations.ConversationRepository;
import io.javabrains.tinder_ai_backend.profiles.Gender;
import io.javabrains.tinder_ai_backend.profiles.Profile;
import io.javabrains.tinder_ai_backend.profiles.ProfileRepository;
import io.javabrains.tinder_ai_backend.conversations.ChatMessage;
import io.javabrains.tinder_ai_backend.conversations.Conversation;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner{

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private ConversationRepository conversationRepository;
	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}
	
	public void run(String... args)
	{

		profileRepository.deleteAll();
		conversationRepository.deleteAll();
		Profile profile = new Profile(
			"1",
			"Kaushik",
			"Kothagal",
			40,
			"Indian",
			Gender.MALE,
			"Software programmer",
			"foo.jpg",
			"INTP"
			);

		profileRepository.save(profile);

		Profile profile2 = new Profile(
			"2",
			"Foo",
			"Bar",
			40,
			"Indian",
			Gender.MALE,
			"Software programmer",
			"foo.jpg",
			"INTP"
			);

		profileRepository.save(profile2);

		profileRepository.findAll().forEach(System.out::println);

		Conversation conversation = new Conversation(
			"1",
			profile.id(),
			List.of(
				new ChatMessage("Hello", profile.id(), LocalDateTime.now())
			)
			
			);

			conversationRepository.save(conversation);
			conversationRepository.findAll().forEach(System.out::println);

	}
}
