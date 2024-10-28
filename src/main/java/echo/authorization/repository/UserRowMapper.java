package echo.authorization.repository;

import echo.authorization.dto.MainUserInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class UserRowMapper implements RowMapper<MainUserInfo> {
    private String login;
    private String password;
    private short roleId;

    @Override
    public MainUserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        MainUserInfo user = new MainUserInfo();
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setRoleName(rs.getString("name"));
        return user;
    }
}

