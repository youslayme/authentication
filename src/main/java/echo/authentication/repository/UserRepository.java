package echo.authentication.repository;

import echo.authentication.dto.AuthenticationSecondaryInfoDto;
import echo.authentication.dto.MainUserInfo;
import echo.authentication.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String IS_EMAIL_EXISTS = """
            SELECT EXISTS (
            SELECT 1
            FROM e_user
            WHERE email = ?
            )
            """;
    private static final String IS_LOGIN_EXISTS = """
            SELECT EXISTS (
            SELECT 1
            FROM e_user
            WHERE login = ?
            )
            """;

    private static final String CREATE_NEW_USER = """
            INSERT INTO e_user (email, login, password, role_id, registration_date)
            VALUES (?, ?, ?, (SELECT r.id FROM e_role r WHERE r.name = ?), CURRENT_DATE);
            """;

    private static final String SELECT_USER_BY_LOGIN = """
            SELECT u.login, u.password, r.name
            FROM e_user u
            JOIN e_role r ON r.id = u.role_id
            WHERE u.login = ?
            """;

    private static final String SELECT_USER_BY_EMAIL = """
            SELECT u.login, u.password, r.name
            FROM e_user u
            JOIN e_role r ON r.id = u.role_id
            WHERE u.email = ?
            """;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isEmailExists(String email) {
        Boolean isEmailExists = jdbcTemplate.queryForObject(IS_EMAIL_EXISTS, Boolean.class, email);
        return isEmailExists != null
                ? isEmailExists 
                : false;
    }
    public boolean isLoginExists(String login) {
        Boolean isLoginExists = jdbcTemplate.queryForObject(IS_LOGIN_EXISTS, Boolean.class, login);
        return isLoginExists != null
                ? isLoginExists
                : false;
    }

    public void save(User user) {
        jdbcTemplate.update(CREATE_NEW_USER, user.getEmail(), user.getLogin(), user.getPassword(), user.getRoleName());
    }

    public Optional<MainUserInfo> findUser(AuthenticationSecondaryInfoDto authenticationSecondaryInfoDto) {

        if (authenticationSecondaryInfoDto.getLogin() != null) {
            return findUserByLogin(authenticationSecondaryInfoDto.getLogin());
        }
        return findUserByEmail(authenticationSecondaryInfoDto.getEmail());
    }

    public Optional<MainUserInfo> findUserByLogin(String login) {
        List<MainUserInfo> users = jdbcTemplate.query(
                SELECT_USER_BY_LOGIN,
                new UserRowMapper(),
                login
        );
        return users.isEmpty()
                ? Optional.empty()
                : Optional.of(users.getFirst());
    }

    public Optional<MainUserInfo> findUserByEmail(String email) {
        List<MainUserInfo> users = jdbcTemplate.query(
                SELECT_USER_BY_EMAIL,
                new UserRowMapper(),
                email
        );
        return users.isEmpty()
                ? Optional.empty()
                : Optional.of(users.getFirst());
    }
}
