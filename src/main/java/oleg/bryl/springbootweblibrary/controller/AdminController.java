package oleg.bryl.springbootweblibrary.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
