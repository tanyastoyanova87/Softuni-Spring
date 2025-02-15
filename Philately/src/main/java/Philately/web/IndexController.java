package Philately.web;

import Philately.stamp.model.Stamp;
import Philately.stamp.service.StampService;
import Philately.user.model.User;
import Philately.user.service.UserService;
import Philately.web.dto.LoginRequest;
import Philately.web.dto.RegisterRequest;
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
    private final StampService stampService;

    public IndexController(UserService userService, StampService stampService) {
        this.userService = userService;
        this.stampService = stampService;
    }

    @GetMapping("/")
    public String getIndexPage() {

        return "index";
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("loginRequest", LoginRequest.builder().build());

        return modelAndView;
    }

    @PostMapping("/login")
    public String loginUser(@Valid LoginRequest loginRequest, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        User user = this.userService.loginUser(loginRequest);
        httpSession.setAttribute("user_id", user.getId());

        return "redirect:/home";
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

    @GetMapping("/home")
    public ModelAndView getHomePage(HttpSession httpSession) {
        List<Stamp> allStamps = this.stampService.getAll();
        UUID userId = (UUID) httpSession.getAttribute("user_id");
        User user = this.userService.getById(userId);


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("user", user);
        modelAndView.addObject("allStamps", allStamps);

        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

