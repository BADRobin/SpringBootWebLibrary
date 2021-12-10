package oleg.bryl.springbootweblibrary.controller;

import javassist.NotFoundException;
import oleg.bryl.springbootweblibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import oleg.bryl.springbootweblibrary.model.Book;
import oleg.bryl.springbootweblibrary.model.User;
import oleg.bryl.springbootweblibrary.service.LibraryService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {

    private LibraryService libraryService;


    @Autowired
    public AdminController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     *
     * @param model
     * @return
     */

    @GetMapping("/adminpanel")
    public String getAdminPanel(Model model){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("currentUser", currentUser);
        List<Book> allBooks = libraryService.findAllBooks();
        model.addAttribute("allBooks", allBooks);
        List<User> allUsers = libraryService.findAllUser();
        model.addAttribute("allUsers", allUsers);
        return "adminpanel";
    }

    @GetMapping("/adminpanel/addNewBook")
    public String getFormToAddBook(Model model){
        Book book = new Book();
        model.addAttribute("book", book);
        return "addBook";
    }

    @PostMapping("/adminpanel/addNewBook")
    public String addNewBook(@Valid @ModelAttribute("book")Book book, Errors errors){
        if (errors.hasErrors()) {
            return "addBook";
        }
        book.setAvailable(true);
        libraryService.saveNewBook(book);
        return "redirect:/adminpanel";
    }

    /**
     *
     * @param id
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/adminpanel/editBook/{id}")
    public ModelAndView editBook(@PathVariable(name="id") Long id) throws NotFoundException {
        ModelAndView mav = new ModelAndView("editBook");
        Book book = libraryService.getBookById(id);
        mav.addObject("book", book);
        return mav;
    }

    @PostMapping("/adminpanel/editBook")
    public String saveBook(@Valid @ModelAttribute("book")Book book, Errors errors){
        if (errors.hasErrors()) {
            return "editBook";
        }
        libraryService.saveEditedBook(book);
        return "redirect:/adminpanel";
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/adminpanel/deleteBook/{id}")
    public String deleteBook(@PathVariable (name="id") Long id) {
        libraryService.deleteBook(id);
        return "redirect:/adminpanel";
    }

    @GetMapping("/adminpanel/deleteUser/{id}")
    public String deleteUser (@PathVariable (name="id") Long id) throws NotFoundException {
        libraryService.deleteUser(id);
        return "redirect:/adminpanel";
    }

}
