package oleg.bryl.springbootweblibrary.controller;

import javassist.NotFoundException;
import oleg.bryl.springbootweblibrary.paginator.PageRender;
import oleg.bryl.springbootweblibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import oleg.bryl.springbootweblibrary.model.Book;
import oleg.bryl.springbootweblibrary.service.LibraryService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private LibraryService libraryService;


    /**
     *
     * @param libraryService
     */
    @Autowired
    public UserController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     *
     * @param model
     * @return
     * @throws NotFoundException
     */

    @GetMapping("userpanel")
    public String getUserPanel(@RequestParam(name = "page", defaultValue = "0") int page, Model model) throws NotFoundException {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("currentUser", currentUser);
        List<Book> allBooks = libraryService.findAllBooks();
        model.addAttribute("allBooks", allBooks);
        List<Book> bookByUser = libraryService.findBookByUser(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("bookByUser", bookByUser);
        return "userpanel";
    }

    /**
     *
     * @param bookid
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/userpanel/borrow/{bookid}")
    public String borrow (@PathVariable(name="bookid") Long bookid) throws NotFoundException {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        libraryService.borrowBook(currentUser,bookid);

        return "redirect:/userpanel";
    }

    /**
     *
     * @param bookid
     * @return
     */
    @GetMapping("/userpanel/return/{bookid}")
    public String returnBook ( @PathVariable (name="bookid") Long bookid) {
        libraryService.returnBook(bookid);
        return "redirect:/userpanel";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/userpanel/showBooksInLibrary")
    public String showAvailableBooks (Model model){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("currentUser", currentUser);
        List<Book> actualBooks = libraryService.findAllBooks();
        model.addAttribute("actualBooks", actualBooks);
        return "borrowBook";
    }

}
