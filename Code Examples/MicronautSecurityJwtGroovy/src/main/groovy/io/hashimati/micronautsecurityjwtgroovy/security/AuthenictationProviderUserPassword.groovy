package io.hashimati.micronautsecurityjwtgroovy.security

import io.hashimati.micronautsecurityjwtgroovy.domains.User
import io.hashimati.micronautsecurityjwtgroovy.repositories.UserRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.Flowable;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AuthenictationProviderUserPassword implements AuthenticationProvider {

    @Inject
    private UserRepository userRepository;

    @Inject
    private StrongPasswordEncryptor strongPasswordEncryptor ;

    @Override
    Publisher<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        return authenticate(null, authenticationRequest);
    }

    @Override
     Publisher<AuthenticationResponse> authenticate(HttpRequest<?> request, AuthenticationRequest<?, ?> authenticationRequest) {

        if(!userRepository.existsByUsername(authenticationRequest.getIdentity().toString())){
            return Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.USER_NOT_FOUND));
        }


        User user = userRepository.findByUsername(authenticationRequest.getIdentity().toString());

        if(user.isDisabled())
        {

            return Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.USER_DISABLED));
        }

        if(user.getExpiration() != null)
            if(user.getExpiration().toInstant().isBefore(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
            {
                return Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_EXPIRED));
            }
        if(user.getPasswordExpiration() != null)
            if(user.getPasswordExpiration().toInstant().isBefore(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
            {
                return Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.PASSWORD_EXPIRED));

            }
        if(user.isLocked())
        {
            return Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_LOCKED));
        }



        if ( strongPasswordEncryptor.checkPassword(authenticationRequest.getSecret().toString(), user.getPassword())) {
            return Flowable.just(new UserDetails(user.getUsername(),
                    null));
        }

        return Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH));

    }
}