package com.example.security.registration;

import com.example.security.appuser.AppUser;
import com.example.security.appuser.AppUserRole;
import com.example.security.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.email());

        if (!isValidEmail){
            throw new IllegalStateException("Email not valid.");
        }

//        TODO: change RegistrationRequest;
        return appUserService.signUpUser(new AppUser(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.password(),
                AppUserRole.USER
        ));
    }
}
