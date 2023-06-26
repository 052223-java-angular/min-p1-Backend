package com.revature.pokemon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.pokemon.dtos.requests.ModifyUserSignatureRequest;
import com.revature.pokemon.dtos.requests.NewLoginRequest;
import com.revature.pokemon.dtos.requests.NewUserRequest;
import com.revature.pokemon.dtos.responses.Principal;
import com.revature.pokemon.entities.Role;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.UserRepository;

public class UserServiceTest {
    @Mock
    UserRepository userRepo;

    @Mock
    RoleService roleService;

    @Mock
    TokenService tokenService;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        String id = "a real id";
        User user = new User();
        user.setId(id);

        when(userRepo.findById(id)).thenReturn(java.util.Optional.of(user));

        User result = userService.findById(id);

        assertEquals(user, result);
    }

    @Test
    void testIsSamePassword() {
        String password = "first";
        String confirmPassword = "first";

        assertTrue(userService.isSamePassword(confirmPassword, password));
    }

    @Test
    void testIsUniqueUsername() {
        String username = "username";

        when(userRepo.findByUsername(username)).thenReturn(java.util.Optional.empty());

        assertTrue(userService.isUniqueUsername(username));
    }

    @Test
    void testIsValidPasswordSuccess() {
        String password = "password1";

        assertTrue(userService.isValidPassword(password));
    }

    @Test
    void testIsValidPasswordFailure() {
        String password = "password";

        assertTrue(!userService.isValidPassword(password));
    }

    @Test
    void testIsValidUserNameSuccess() {
        String username = "username";

        assertTrue(userService.isValidUserName(username));
    }

    @Test
    void testIsValidUserNameFailure() {
        String username = "user";

        assertTrue(!userService.isValidUserName(username));
    }

    @Test
    void testLogin() {
        NewLoginRequest req = new NewLoginRequest();
        req.setPassword("password1");
        req.setUsername("user");

        Role role = new Role();
        role.setName("user");
        User user = new User();
        user.setUsername(req.getUsername());
        user.setId("a real id");
        user.setEmail("something@google.com");
        user.setRole(role);
        user.setPassword("$2a$10$ciQ6db01s6iKDBMwXyvqb.PES0FdSpEhAiM1QlAMhG0m8KaAQ/cli");

        Principal principal = new Principal(user);

        when(userRepo.findByUsername(req.getUsername())).thenReturn(java.util.Optional.of(user));

        Principal result = userService.login(req).get();

        assertEquals(principal.getUsername(), result.getUsername());
    }

    @Test
    void testModifySignature() {
        ModifyUserSignatureRequest req = new ModifyUserSignatureRequest();
        req.setUserId("a real id");
        User user = new User();
        user.setId(req.getUserId());

        when(userRepo.findById(req.getUserId())).thenReturn(java.util.Optional.of(user));

        userService.modifySignature(req);

        verify(userRepo).save(user);

    }

    @Test
    void testRegister() {
        NewUserRequest req = new NewUserRequest();
        req.setConfirmedPassword("pass");
        req.setPassword("pass");
        req.setEmail("email@google.com");
        req.setUsername("username");

        Role role = new Role();
        role.setName("USER");

        when(roleService.findByName("USER")).thenReturn(role);

        userService.register(req);

    }
}
