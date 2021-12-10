package oleg.bryl.springbootweblibrary.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import oleg.bryl.springbootweblibrary.model.Book;
import oleg.bryl.springbootweblibrary.model.User;
import oleg.bryl.springbootweblibrary.repository.BookRepository;
import oleg.bryl.springbootweblibrary.repository.RoleRepository;
import oleg.bryl.springbootweblibrary.repository.UserRepository;

import java.util.List;

@Service
public class LibraryService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BookRepository bookRepository;
    private PasswordEncoder passwordEncoder;




    /**
     *
     * @param userRepository
     * @param roleRepository
     * @param bookRepository
     * @param passwordEncoder
     */
    @Autowired
    public LibraryService(UserRepository userRepository, RoleRepository roleRepository, BookRepository bookRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookRepository = bookRepository;
    }

    /**
     *
     * @param user
     * @return
     */
    public User saveUser(User user) {
        user.getRoleList().add(roleRepository.findByRolename("USER"));
        String passBCrypt = passwordEncoder.encode(user.getPassword());
        user.setPassword(passBCrypt);
        return userRepository.save(user);
    }

    /**
     *
     * @param id
     * @throws NotFoundException
     */

    public void deleteUser(Long id) throws NotFoundException {
        List<Book> bookList = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found id: " + id)).getBookList();
        for (Book book : bookList) {
            book.setUser(null);
            book.setAvailable(true);
            bookRepository.save(book);
        }
        userRepository.deleteById(id);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public boolean checkUniqueUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> findBookByUser(String username) throws NotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User: " + username + " not found")).getBookList();
    }
    public Book saveNewBook(Book book) {
        book.setAvailable(true);
        return bookRepository.save(book);
    }

    public Book saveEditedBook(Book book) {
        if(book.getUser()==null){
            return bookRepository.save(book);
        }
        else {
            book.setUser(bookRepository.findById(book.getId()).get().getUser());
            return bookRepository.save(book);
        }
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book getBookById(Long id) throws NotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found: " + id));
    }

    public void returnBook(Long bookid) {
        Book book = bookRepository.findById(bookid).get();
        book.setUser(null);
        book.setAvailable(true);
        bookRepository.save(book);
    }

    public void borrowBook(String username, Long bookid) throws NotFoundException {
        Book book = bookRepository.findById(bookid).orElseThrow(() -> new NotFoundException("Book not found with: " + bookid));
        book.setAvailable(false);
        book.setUser(userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found with: " + username)));
        bookRepository.save(book);
    }

}
