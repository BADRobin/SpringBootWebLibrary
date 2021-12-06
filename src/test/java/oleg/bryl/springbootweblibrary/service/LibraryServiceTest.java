package oleg.bryl.springbootweblibrary.service;

import oleg.bryl.springbootweblibrary.model.Book;
import oleg.bryl.springbootweblibrary.model.Role;
import oleg.bryl.springbootweblibrary.model.User;
import oleg.bryl.springbootweblibrary.repository.BookRepository;
import oleg.bryl.springbootweblibrary.repository.RoleRepository;
import oleg.bryl.springbootweblibrary.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class LibraryServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    LibraryService libraryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveUser() {
        when(roleRepository.findByRolename(anyString())).thenReturn(new Role("rolename"));

        User result = libraryService.saveUser(new User(null, "password", null, Arrays.<Role>asList(new Role("rolename"))));
        Assertions.assertEquals(new User(null, "password", null, Arrays.<Role>asList(new Role("rolename"))), result);
    }

    @Test
    void testDeleteUser() {
        libraryService.deleteUser(Long.valueOf(1));
    }

    @Test
    void testFindAllUser() {
        List<User> result = libraryService.findAllUser();
        Assertions.assertEquals(Arrays.<User>asList(new User("username", "password", "email", Arrays.<Role>asList(new Role("rolename")))), result);
    }

    @Test
    void testCheckUniqueUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        boolean result = libraryService.checkUniqueUsername("username");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testFindAllBooks() {
        List<Book> result = libraryService.findAllBooks();
        Assertions.assertEquals(Arrays.<Book>asList(new Book("title", "author", "ISBN")), result);
    }

    @Test
    void testFindBookByUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        List<Book> result = libraryService.findBookByUser("username");
        Assertions.assertEquals(Arrays.<Book>asList(new Book("title", "author", "ISBN")), result);
    }

    @Test
    void testSaveNewBook() {
        Book result = libraryService.saveNewBook(new Book("title", "author", "ISBN"));
        Assertions.assertEquals(new Book("title", "author", "ISBN"), result);
    }

    @Test
    void testSaveEditedBook() {
        Book result = libraryService.saveEditedBook(new Book("title", "author", "ISBN"));
        Assertions.assertEquals(new Book("title", "author", "ISBN"), result);
    }

    @Test
    void testDeleteBook() {
        libraryService.deleteBook(Long.valueOf(1));
    }

    @Test
    void testGetBookById() {
        Book result = libraryService.getBookById(Long.valueOf(1));
        Assertions.assertEquals(new Book("title", "author", "ISBN"), result);
    }

    @Test
    void testReturnBook() {
        libraryService.returnBook(Long.valueOf(1));
    }

    @Test
    void testBorrowBook() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        libraryService.borrowBook("username", Long.valueOf(1));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme