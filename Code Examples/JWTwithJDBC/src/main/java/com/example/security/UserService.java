package com.example.security;


import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Singleton
@Transactional
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private RoleRepository roleRepository;
    @Inject
    StrongPasswordEncryptor strongPasswordEncryptor;

    public User save(String role, User user) throws Exception {


        if(userRepository.existsByUsername(user.getUsername()))
            throw new Exception(user.getUsername() +" is already exist!");

        Optional<Role> roleObj = roleRepository.findByName(role);
        if(roleObj.isPresent()){
            user.getRoles().add(roleObj.get());

        }
        else
        {
            Role r = new Role();
            r.setName(role);
            roleRepository.save(r);

            roleObj = roleRepository.findByName(role);



        }


        user.setPassword(strongPasswordEncryptor.encryptPassword(user.getPassword()));
        user.setExpiration(Date.from(LocalDate.now().plusYears(10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        user.setPasswordExpiration(Date.from(LocalDate.now().plusYears(10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        user.setLastLoginStatus(LoginStatus.JUST_REGISTERED);
        User result =  userRepository.save(user);
        Role r = roleObj.get();
        r.getUsers().add(user);

        roleRepository.update(r);
        return result;
        //Add your logic here.





    }
    public Iterable<User> findAll()
    {
        return userRepository.findAll();
    }
}


