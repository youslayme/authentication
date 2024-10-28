package echo.authorization.service;

import echo.authorization.validator.UserValidator;
import echo.authorization.dto.AuthenticationPrimaryInfoDto;
import echo.authorization.dto.AuthenticationSecondaryInfoDto;
import echo.authorization.dto.UserJwtDto;
import echo.authorization.dto.UserRole;
import echo.authorization.dto.MainUserInfo;
import echo.authorization.jwt.JwtProvider;
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

    public boolean isCorrectPassword(AuthenticationPrimaryInfoDto authenticationPrimaryInfoDto, MainUserInfo user) {
        return passwordEncoder.matches(authenticationPrimaryInfoDto.getPassword(), user.getPassword());
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
