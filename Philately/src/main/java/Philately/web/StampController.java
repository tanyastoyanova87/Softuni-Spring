package Philately.web;

import Philately.stamp.service.StampService;
import Philately.user.model.User;
import Philately.user.service.UserService;
import Philately.web.dto.AddStampRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/stamps")
public class StampController {

    private final UserService userService;
    private final StampService stampService;

    public StampController(UserService userService, StampService stampService) {
        this.userService = userService;
        this.stampService = stampService;
    }

    @GetMapping("/new")
    public ModelAndView getAddStampPage(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        User user = this.userService.getById(userId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add-stamp");
        modelAndView.addObject("addStampRequest", AddStampRequest.builder().build());
        modelAndView.addObject("user", user);


        return modelAndView;
    }

    @PostMapping
    public String addNewStamp(@Valid AddStampRequest addStampRequest, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "add-stamp";
        }

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = this.userService.getById(userId);

        this.stampService.addStamp(addStampRequest, user);

        return "redirect:/home";
    }


    @PostMapping("/{id}/wishlist")
    public String addToWishlist(@PathVariable UUID id, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        User user = this.userService.getById(userId);

        this.stampService.addStampToWishList(id, user);

        return "redirect:/home";
    }

    @DeleteMapping("/{id}")
    public String deleteFromWishlist(@PathVariable UUID id) {
        this.stampService.deleteStampFromWishlist(id);

        return "redirect:/home";
    }
}
