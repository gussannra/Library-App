package se.yrgo.libraryapp.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import se.yrgo.libraryapp.controllers.LoginController;
import se.yrgo.libraryapp.dao.RoleDao;
import se.yrgo.libraryapp.dao.SessionDao;
import se.yrgo.libraryapp.entities.Role;
import se.yrgo.libraryapp.entities.User;
import se.yrgo.libraryapp.entities.UserId;
import se.yrgo.libraryapp.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class LoginControllerTest {
    @Mock
    UserService userService;

    @Mock
    RoleDao roleDao;

    @Mock
    SessionDao sessionDao;

    @Test
    void isLoggedInFailsWithNull() {
        String sessionCookie = null;
        LoginController loginController = new LoginController(userService, roleDao, sessionDao);
        assertThat(loginController.isLoggedIn(sessionCookie)).isEmpty();
    }

    @Test
    void isLoggedInFailsInvalidUuid() {
        LoginController loginController = new LoginController(userService, roleDao, sessionDao);
        assertThat(loginController.isLoggedIn("invalid-UUID")).isEmpty();
    }

    @Test
    void isLoggedInSucceeds() {
        final String sessionCookie = "acde070d-8c4c-4f0d-9d8a-162843c10333";

        UUID uuid = UUID.fromString(sessionCookie);
        UserId userId = UserId.of(1);
        LoginController loginController = new LoginController(userService, roleDao, sessionDao);
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.ADMIN);

        when(sessionDao.validate(uuid)).thenReturn(userId);
        when(roleDao.get(userId)).thenReturn(roleList);

        assertThat(loginController.isLoggedIn(sessionCookie)).isEqualTo(roleList);
    }
}
