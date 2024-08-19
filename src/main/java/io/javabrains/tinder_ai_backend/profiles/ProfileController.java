package io.javabrains.tinder_ai_backend.profiles;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    private ProfileRepository profileRepository;
    
    @GetMapping("/profiles/random")
    public Profile getRandomProfile(){
        return profileRepository.getRandomProfile();
    }

}
