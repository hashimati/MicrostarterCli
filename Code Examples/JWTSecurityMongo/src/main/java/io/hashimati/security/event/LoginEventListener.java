package io.hashimati.security.event;

import io.hashimati.security.domains.LoginEvent;
import io.hashimati.security.domains.User;
import io.hashimati.security.repository.UserRepository;
import io.micronaut.context.event.ApplicationEventListener;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LoginEventListener implements ApplicationEventListener<LoginEvent>  {
    private static final Logger log = LoggerFactory.getLogger(LoginEventListener.class);

    @Inject
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(LoginEvent event) {
        log.info("Saving login event: {}", event);
       User user =  userRepository.findByUsername(event.getUsername()).block();
       user.setLastLoginStatus(event.getStatus());
       userRepository.update(user).block();
    }
}
