package echo.authorization.service;

import echo.authorization.validator.UserValidator;
import echo.authorization.dto.AuthenticationPrimaryInfoDto;
import echo.authorization.dto.MainUserInfo;
import echo.authorization.dto.RegistrationInfoDto;
import echo.authorization.dto.UserRole;
import echo.authorization.entity.User;
import echo.authorization.exception.AuthorizationErrorType;
import echo.authorization.exception.AuthorizationException;
import echo.authorization.exception.GlobalErrorType;
import echo.authorization.exception.RegistrationErrorType;
import echo.authorization.exception.RegistrationException;
import echo.authorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;

    public void registerNewAccount(RegistrationInfoDto registrationInfoDto) {
        UserValidator.validate(registrationInfoDto);
        checkUserExisting(registrationInfoDto);

        registrationInfoDto.setPassword(passwordEncoder.encode(registrationInfoDto.getPassword()));
        User user = User.builder()
                .email(registrationInfoDto.getEmail())
                .login(registrationInfoDto.getLogin())
                .password(registrationInfoDto.getPassword())
                .roleName(UserRole.USER.name())
                .build();
        userRepository.save(user);
    }

    public String authenticate(AuthenticationPrimaryInfoDto authenticationPrimaryInfoDto) {
        UserValidator.validate(authenticationPrimaryInfoDto);

        var authenticationSecondaryInfoDto = SecurityService.getSecondaryAuthenticationInfoDto(authenticationPrimaryInfoDto);
        Optional<MainUserInfo> optUsers = userRepository.findUser(authenticationSecondaryInfoDto);
        if (optUsers.isEmpty()) {
            if (authenticationSecondaryInfoDto.getLogin() != null) {
                throw new AuthorizationException(AuthorizationErrorType.LOGIN_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND);
            }
            throw new AuthorizationException(AuthorizationErrorType.EMAIL_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND);
        }
        MainUserInfo user = optUsers.get();

        if (!securityService.isCorrectPassword(authenticationPrimaryInfoDto, user)) {
            throw new AuthorizationException(AuthorizationErrorType.PASSWORD_NOT_CORRECT, HttpStatus.UNAUTHORIZED);
        } else {
            return securityService.createNewJwt(user);
        }
    }

    private void checkUserExisting(RegistrationInfoDto registrationInfoDto) {
        List<GlobalErrorType> errorTypes = new ArrayList<>();
        if (userRepository.isEmailExists(registrationInfoDto.getEmail())) {
            errorTypes.add(RegistrationErrorType.EMAIL_ALREADY_EXISTS);
        }

        if (userRepository.isLoginExists(registrationInfoDto.getLogin())) {
            errorTypes.add(RegistrationErrorType.LOGIN_ALREADY_EXISTS);
        }

        if (!errorTypes.isEmpty()) {
            throw new RegistrationException(errorTypes, HttpStatus.BAD_REQUEST);
        }
    }
}
