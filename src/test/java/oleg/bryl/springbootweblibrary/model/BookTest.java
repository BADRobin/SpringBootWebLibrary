package oleg.bryl.springbootweblibrary.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class BookTest {
    @Mock
    User user;
    @InjectMocks
    Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSetUser() {
        book.setUser(new User("username", "password", "email", Arrays.<Role>asList(new Role("rolename"))));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme