package com.dictionaryapp.web;

import com.dictionaryapp.language.model.LanguageName;
import com.dictionaryapp.language.service.LanguageService;
import com.dictionaryapp.user.model.User;
import com.dictionaryapp.user.service.UserService;
import com.dictionaryapp.web.dto.LoginRequest;
import com.dictionaryapp.web.dto.RegisterRequest;
import com.dictionaryapp.word.model.Word;
import com.dictionaryapp.word.service.WordService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {

    private final UserService userService;
    private final WordService wordService;

    public IndexController(UserService userService, WordService wordService) {
        this.userService = userService;
        this.wordService = wordService;
    }

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("registerRequest", RegisterRequest.builder().build());

        return modelAndView;
    }

    @PostMapping("/register")
    public String registerUser(@Valid RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        this.userService.registerUser(registerRequest);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("loginRequest", LoginRequest.builder().build());

        return modelAndView;
    }

    @PostMapping("/login")
    public String loginUser(@Valid LoginRequest loginRequest, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        User user = this.userService.loginUser(loginRequest);
        session.setAttribute("user_id", user.getId());

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        User user = this.userService.getById(userId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("user", user);

        List<Word> allWords = this.wordService.getAllWords();
        modelAndView.addObject("allWords", allWords);

        List<Word> frenchWords = this.wordService.findAllWordsByLanguage(LanguageName.FRENCH);
        List<Word> germanWords = this.wordService.findAllWordsByLanguage(LanguageName.GERMAN);
        List<Word> spanishWords = this.wordService.findAllWordsByLanguage(LanguageName.SPANISH);
        List<Word> italianWords = this.wordService.findAllWordsByLanguage(LanguageName.ITALIAN);
        modelAndView.addObject("frenchWords", frenchWords);
        modelAndView.addObject("germanWords", germanWords);
        modelAndView.addObject("spanishWords", spanishWords);
        modelAndView.addObject("italianWords", italianWords);

        return modelAndView;
    }
}
