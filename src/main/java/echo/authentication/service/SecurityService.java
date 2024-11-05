package echo.authentication.service;

import echo.authentication.validator.UserValidator;
import echo.authentication.dto.AuthenticationPrimaryInfoDto;
import echo.authentication.dto.AuthenticationSecondaryInfoDto;
import echo.authentication.dto.UserJwtDto;
import echo.authentication.dto.UserRole;
import echo.authentication.dto.MainUserInfo;
import echo.authentication.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public static AuthenticationSecondaryInfoDto getSecondaryAuthenticationInfoDto(AuthenticationPrimaryInfoDto authentication) {
        if (UserValidator.isLogin(authentication)) {
            return new AuthenticationSecondaryInfoDto(null, authentication.getLoginOrEmail(), authentication.getPassword());
        }
        return new AuthenticationSecondaryInfoDto(authentication.getLoginOrEmail(), null, authentication.getPassword());
    }

    public boolean isCorrectPassword(AuthenticationSecondaryInfoDto authenticationSecondaryInfoDto, MainUserInfo user) {
        return passwordEncoder.matches(authenticationSecondaryInfoDto.getPassword(), user.getPassword());
    }

    public String createNewJwt(MainUserInfo user) {
        return jwtProvider.generateToken(new UserJwtDto(user.getLogin(), setUserRole(user)));
    }

    private static UserRole setUserRole(MainUserInfo user) {
        if (Objects.equals(user.getRoleName(), "USER")) {
            return UserRole.USER;
        }
        if (Objects.equals(user.getRoleName(), "ADMIN")) {
            return UserRole.ADMIN;
        }
        return UserRole.USER;
        }
    }
