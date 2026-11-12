package se.yrgo.libraryapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.*;
import se.yrgo.libraryapp.dao.UserDao;
import se.yrgo.libraryapp.entities.LoginInfo;
import se.yrgo.libraryapp.entities.UserId;
import se.yrgo.libraryapp.services.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @SuppressWarnings("deprecation")
    void correctLogin() {
        final String userId = "1";
        final UserId id = UserId.of(userId);
        final String username = "testuser";
        final String password = "password";
        final String passwordHash = "password";
        final LoginInfo info = new LoginInfo(id, passwordHash);

        final PasswordEncoder encoder = org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();

        when(userDao.getLoginInfo(username)).thenReturn(Optional.of(info));

        UserService userService = new UserService(userDao, encoder);
        assertThat(userService.validate(username, password)).isEqualTo(Optional.of(id));
    }

    @Test
    void registerHandlesApostrophe() {
        when(passwordEncoder.encode("password")).thenReturn("password");
        when(userDao.register("O'Malley", "Bosse O\\'Malley", "password")).thenReturn(true);
        UserService userService = new UserService(userDao, passwordEncoder);

        assertThat(userService.register("O'Malley", "Bosse O'Malley", "password")).isTrue();
    }

    @Test
    void isNameAvailableInvalidNameReturnsFalse() {
        UserService userService = new UserService(userDao, passwordEncoder);
        assertThat(userService.isNameAvailable("Bo")).isFalse();
    }

    @Test
    void isNameAvailableNullNameReturnFalse() {
        UserService userService = new UserService(userDao, passwordEncoder);
        assertThat(userService.isNameAvailable(null)).isFalse();
    }

    @Test
    void isNameAvailableValidNameReturnsTrue() {
        when(userDao.isNameAvailable("Bosse")).thenReturn(true);
        UserService userService = new UserService(userDao, passwordEncoder);
        assertThat(userService.isNameAvailable("Bosse")).isTrue();
    }

    @Test
    void isNameAvailableValidBoundaryCaseReturnsTrue() {
        when(userDao.isNameAvailable("Bob")).thenReturn(true);
        UserService userService = new UserService(userDao, passwordEncoder);
        assertThat(userService.isNameAvailable("Bob")).isTrue();
    }
}

