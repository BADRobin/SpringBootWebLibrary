package oleg.bryl.springbootweblibrary.service;

import oleg.bryl.springbootweblibrary.model.Role;
import oleg.bryl.springbootweblibrary.model.User;
import oleg.bryl.springbootweblibrary.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImpTest {

    @Mock
    private UserRepository mockUserRepository;

    private UserDetailsServiceImp userDetailsServiceImpUnderTest;

    @BeforeEach
    void setUp() {
        userDetailsServiceImpUnderTest = new UserDetailsServiceImp(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername() {
        // Setup
        // Configure UserRepository.findByUsername(...).
        final Optional<User> user = Optional.of(new User("username", "password", "email", Arrays.asList(new Role("rolename"))));
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        // Run the test
        final UserDetails result = userDetailsServiceImpUnderTest.loadUserByUsername("username");

        // Verify the results
    }

    @Test
    void testLoadUserByUsername_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());

        // Run the test
        final UserDetails result = userDetailsServiceImpUnderTest.loadUserByUsername("username");

        // Verify the results
    }


}
