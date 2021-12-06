package oleg.bryl.springbootweblibrary.controller;

import javassist.NotFoundException;
import oleg.bryl.springbootweblibrary.model.Book;
import oleg.bryl.springbootweblibrary.model.Role;
import oleg.bryl.springbootweblibrary.model.User;
import oleg.bryl.springbootweblibrary.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService mockLibraryService;

    @Test
    void testGetAdminPanel() throws Exception {
        // Setup
        // Configure LibraryService.findAllBooks(...).
        final List<Book> books = Arrays.asList(new Book("title", "author", "ISBN"));
        when(mockLibraryService.findAllBooks()).thenReturn(books);

        // Configure LibraryService.findAllUser(...).
        final List<User> users = Arrays.asList(new User("username", "password", "email", Arrays.asList(new Role("rolename"))));
        when(mockLibraryService.findAllUser()).thenReturn(users);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/adminpanel")
                        .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAdminPanel_LibraryServiceFindAllBooksReturnsNoItems() throws Exception {
        // Setup
        when(mockLibraryService.findAllBooks()).thenReturn(Collections.emptyList());

        // Configure LibraryService.findAllUser(...).
        final List<User> users = Arrays.asList(new User("username", "password", "email", Arrays.asList(new Role("rolename"))));
        when(mockLibraryService.findAllUser()).thenReturn(users);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/adminpanel")
                        .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAdminPanel_LibraryServiceFindAllUserReturnsNoItems() throws Exception {
        // Setup
        // Configure LibraryService.findAllBooks(...).
        final List<Book> books = Arrays.asList(new Book("title", "author", "ISBN"));
        when(mockLibraryService.findAllBooks()).thenReturn(books);

        when(mockLibraryService.findAllUser()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/adminpanel")
                        .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetFormToAddBook() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/adminpanel/addNewBook")
                        .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testAddNewBook() throws Exception {
        // Setup
        when(mockLibraryService.saveNewBook(any(Book.class))).thenReturn(new Book("title", "author", "ISBN"));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/adminpanel/addNewBook")
                        .param("id", "0")
                        .param("title", "title")
                        .param("author", "author")
                        .param("ISBN", "ISBN")
                        .param("available", "false")
                        .param("user_id", "0")
                        .param("user_username", "username")
                        .param("user_password", "password")
                        .param("user_email", "email")
                        .param("user_roleList0.id", "0")
                        .param("user_roleList0.rolename", "rolename")
                        .param("user_roleList0.userList", "value1", "value2")
                        .param("user_bookList", "value1", "value2")
                        .with(csrf())
                        .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockLibraryService).saveNewBook(any(Book.class));
    }

    @Test
    void testEditBook() throws Exception {
        // Setup
        when(mockLibraryService.getBookById(0L)).thenReturn(new Book("title", "author", "ISBN"));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/adminpanel/editBook/{id}", 0)
                        .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testEditBook_LibraryServiceThrowsNotFoundException() throws Exception {
        // Setup
        when(mockLibraryService.getBookById(0L)).thenThrow(NotFoundException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/adminpanel/editBook/{id}", 0)
                        .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testSaveBook() throws Exception {
        // Setup
        when(mockLibraryService.saveEditedBook(any(Book.class))).thenReturn(new Book("title", "author", "ISBN"));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/adminpanel/editBook")
                        .param("id", "0")
                        .param("title", "title")
                        .param("author", "author")
                        .param("ISBN", "ISBN")
                        .param("available", "false")
                        .param("user_id", "0")
                        .param("user_username", "username")
                        .param("user_password", "password")
                        .param("user_email", "email")
                        .param("user_roleList0.id", "0")
                        .param("user_roleList0.rolename", "rolename")
                        .param("user_roleList0.userList", "value1", "value2")
                        .param("user_bookList", "value1", "value2")
                        .with(csrf())
                        .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockLibraryService).saveEditedBook(any(Book.class));
    }

    @Test
    void testDeleteBook() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/adminpanel/deleteBook/{id}", 0)
                        .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockLibraryService).deleteBook(0L);
    }

    @Test
    void testDeleteUser() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/adminpanel/deleteUser/{id}", 0)
                        .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockLibraryService).deleteUser(0L);
    }

    @Test
    void testDeleteUser_LibraryServiceThrowsNotFoundException() throws Exception {
        // Setup
        doThrow(NotFoundException.class).when(mockLibraryService).deleteUser(0L);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/adminpanel/deleteUser/{id}", 0)
                        .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
