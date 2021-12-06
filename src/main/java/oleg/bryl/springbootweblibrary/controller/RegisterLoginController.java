package oleg.bryl.springbootweblibrary.controller;

import javassist.NotFoundException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import oleg.bryl.springbootweblibrary.model.User;
import oleg.bryl.springbootweblibrary.service.LibraryService;


import javax.validation.Valid;

@Controller
public class RegisterLoginController {


    private LibraryService libraryService;

    /**
     *
     * @param libraryService
     */
    @Autowired
    public RegisterLoginController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     *
     * @return
     */
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    /**
     *
     * @param model
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/welcome")
    public String getWelcome(Model model) throws NotFoundException {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            return "redirect:/adminpanel";
        }
        return "redirect:/userpanel";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String registerUSer(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    /**
     *
     * @param user
     * @param errors
     * @param model
     * @return
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, Errors errors, Model model) {

        if(libraryService.checkUniqueUsername(user.getUsername())){
            model.addAttribute("exist",true );
            return "register";
        }
        else if (errors.hasErrors()) {
            return "register";
        }
        else {
            libraryService.saveUser(user);
            model.addAttribute("registerOK", "Registration OK");
            return "login";
        }
    }

}
