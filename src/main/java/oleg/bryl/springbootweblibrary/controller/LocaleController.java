package oleg.bryl.springbootweblibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
@Controller
public class LocaleController {
    /**
     *
     * @param request
     * @return
     */
    @GetMapping("/locale")
    public String locale(HttpServletRequest request) {
        String lastUrl = request.getHeader("referer");

        return "redirect:".concat(lastUrl);
    }
}
