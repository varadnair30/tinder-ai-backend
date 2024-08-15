package io.javabrains.tinder_ai_backend.conversations;
import java.util.*;
import java.time.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.javabrains.tinder_ai_backend.profiles.ProfileRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;


    public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository){
        this.conversationRepository=conversationRepository;
        this.profileRepository=profileRepository;
    }

    @PostMapping("/coversations")
    public Conversation createNewConversation(@RequestBody CreateConversationRequest request)
    {
        profileRepository.findById(request.profileId()).orElseThrow(()-> new ResponseStatusException(
            HttpStatus.NOT_FOUND,"Unable to find profile ID "+ request.profileId()));
        
        Conversation conversation = new Conversation(
			UUID.randomUUID().toString(),
			request.profileId(),
            new ArrayList<>()
			
			);
			conversationRepository.save(conversation);
            return conversation;
   }

   @PostMapping("/conversations/{conversationId}")
   public Conversation addMessageToConversation(
            @PathVariable String conversationId,
            @RequestBody ChatMessage chatMessage
            ) {
              Conversation conversation =  conversationRepository.findById(conversationId).
              orElseThrow(()-> new ResponseStatusException(
              HttpStatus.NOT_FOUND,"Unable to find conversation ID "+ conversationId
              ));
             
              profileRepository.findById(chatMessage.authorId()).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,"Unable to find profile ID "+ chatMessage.authorId()
              ));
     
                    
                ChatMessage messageWithTime = new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(),
                LocalDateTime.now()
                );
                conversation.messages().add(messageWithTime);
                conversationRepository.save(conversation);
                return conversation;

   }
    public record CreateConversationRequest(
        String profileId 
    ){}
}
