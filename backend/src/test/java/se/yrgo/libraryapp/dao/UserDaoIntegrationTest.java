package se.yrgo.libraryapp.dao;

import com.radcortez.flyway.test.annotation.H2;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import se.yrgo.libraryapp.entities.LoginInfo;
import se.yrgo.libraryapp.entities.User;
import se.yrgo.libraryapp.entities.UserId;

import javax.sql.DataSource;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@H2
public class UserDaoIntegrationTest {
    private static DataSource ds;

    @BeforeAll
    static void initDataSource() {
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        UserDaoIntegrationTest.ds = ds;
    }

    @Test
    void getUserById() {
        final String username = "test";
        final UserId userId = UserId.of(1);

        UserDao userDao = new UserDao(ds);
        Optional<User> maybeUser = userDao.get(Integer.toString(userId.getId()));

        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.get().getName()).isEqualTo(username);
        assertThat(maybeUser.get().getId()).isEqualTo(userId);
    }

    @Test
    void getNonExistingUserById() {
        final UserId userId = UserId.of(99);

        UserDao userDao = new UserDao(ds);
        Optional<User> maybeUser = userDao.get(Integer.toString(userId.getId()));

        assertThat(maybeUser.isPresent()).isFalse();
        assertThat(maybeUser).isEmpty();
    }

    @Test
    void getLoginInfo() {
        final String username = "test";
        final String passwordHash = "$argon2i$v=19$m=16,t=2,p=1$MTIzNDU2Nzg5MDEyMzQ1NjA$LmFqTZeUWwqsnbZCS2E8XQ";

        UserDao userDao = new UserDao(ds);
        Optional<LoginInfo> maybeLoginInfo = userDao.getLoginInfo(username);

        assertThat(maybeLoginInfo.isPresent()).isTrue();
        assertThat(maybeLoginInfo.get().getPasswordHash()).isEqualTo(passwordHash);
        final int userId = maybeLoginInfo.get().getUserId().getId();
        Optional<User> maybeUser = userDao.get(Integer.toString(userId));
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.get().getName()).isEqualTo(username);
    }

    @Test
    void getNonExistingLoginInfo() {
        final String username = "bosse";

        UserDao userDao = new UserDao(ds);
        Optional<LoginInfo> maybeLoginInfo = userDao.getLoginInfo(username);

        assertThat(maybeLoginInfo.isPresent()).isFalse();
        assertThat(maybeLoginInfo).isEmpty();
    }

    @Test
    void isNameAvailableNameAvailable() {
        // no same name already
        final String name = "testtest";
        UserDao userDao = new UserDao(ds);
        assertThat(userDao.isNameAvailable(name)).isTrue();
    }

    @Test
    void isNameAvailableNameTaken() {
        // user already exists with the same name
        final String name = "test";
        UserDao userDao = new UserDao(ds);
        assertThat(userDao.isNameAvailable(name)).isFalse();
    }
}
