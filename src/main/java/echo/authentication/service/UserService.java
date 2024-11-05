package echo.authentication.service;

import echo.authentication.validator.UserValidator;
import echo.authentication.dto.AuthenticationPrimaryInfoDto;
import echo.authentication.dto.MainUserInfo;
import echo.authentication.dto.RegistrationInfoDto;
import echo.authentication.dto.UserRole;
import echo.authentication.entity.User;
import echo.authentication.exception.AuthenticationErrorType;
import echo.authentication.exception.AuthenticationException;
import echo.authentication.exception.GlobalErrorType;
import echo.authentication.exception.RegistrationErrorType;
import echo.authentication.exception.RegistrationException;
import echo.authentication.repository.UserRepository;
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
                throw new AuthenticationException(AuthenticationErrorType.LOGIN_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND);
            }
            throw new AuthenticationException(AuthenticationErrorType.EMAIL_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND);
        }
        MainUserInfo user = optUsers.get();

        if (securityService.isCorrectPassword(authenticationSecondaryInfoDto, user)) {
            return securityService.createNewJwt(user);
        } else {
            throw new AuthenticationException(AuthenticationErrorType.PASSWORD_NOT_CORRECT, HttpStatus.UNAUTHORIZED);
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
//    ⠀⠀⠀⠀⠀⠀⠀⣠⣤⣤⣤⣤⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⡀⠀⠀⠀⠀⢰⡿⠋⠁⠀⠀⠈⠉⠙⠻⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠇⠀⠀⠀⢀⣿⠇⠈⢀⣴⣶⡾⠿⠿⠿⢿⣿⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠃⠀⣀⣀⣸⡿⠀⠀⢸⣿⣇⠀⠀⠀⠀⠀⠀⠙⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠇⣾⡟⠛⣿⡇⠀⠀⢸⣿⣿⣷⣤⣤⣤⣤⣶⣶⣿⠇⠀⠀⠀⠀⠀⠀⠀⣀⠀⠀
//            ⢅⣿⠀⢀⣿⡇⠀⠀⠀⠻⢿⣿⣿⣿⣿⣿⠿⣿⡏⠀⠀⠀⠀⢴⣶⣶⣿⣿⣿⣆
//            ⣺⣿⠀⢸⣿⡇⠀⠀⠀⠀⠀⠈⠉⠁⠀⠀⠀⣿⡇⣀⣠⣴⣾⣮⣝⠿⠿⠿⣻⡟
//            ⢺⣿⠀⠘⣿⡇⠀⠀⠀⠀⠀⠀⠀⣠⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠁⠉⠀
//            ⠼⣿⠀⠀⣿⡇⠀⠀⠀⠀⠀⣠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠉⠀⠀⠀⠀
//            ⠅⠻⣷⣶⣿⣇⠀⠀⠀⢠⣼⣿⣿⣿⣿⣿⣿⣿⣛⣛⣻⠉⠁⠀⠀⠀⠀⠀⠀⠀
//            ⡂⠀⠀⠀⢸⣿⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠀⠀⠀⠀⢸⣿⣀⣀⣀⣼⡿⢿⣿⣿⣿⣿⣿⡿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀
//            ⠀⠀⠀⠀⠀⠙⠛⠛⠛⠋⠁⠀⠙⠻⠿⠟⠋⠑⠛⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
}
