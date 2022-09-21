package com.example.security.appuser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "User with email %s not found.";
    private final AppUserRepository appUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public String signUpUser(AppUser user){
        boolean userExists = appUserRepository.findByEmail(user.getEmail()).isPresent();

        if(userExists){
            throw new IllegalStateException("Email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        appUserRepository.save(user);

//        TODO: send confirmation token

        return "it works";
    }
}
