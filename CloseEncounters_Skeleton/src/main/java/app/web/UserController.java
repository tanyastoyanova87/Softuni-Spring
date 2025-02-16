package app.web;

import app.user.model.User;
import app.user.service.UserService;
import app.web.dto.EditRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}/profile")
    public ModelAndView getUserProfile(@PathVariable UUID id) {
        User user = userService.getById(id);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("edit-profile");
        modelAndView.addObject("user", user);
        modelAndView.addObject("editRequest", EditRequest.builder().build());

        return modelAndView;
    }

    @PutMapping("/{id}/profile")
    public ModelAndView editUserProfile(@PathVariable UUID id, @Valid EditRequest editRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("edit-profile");

            User user = userService.getById(id);
            modelAndView.addObject("user", user);
            modelAndView.addObject("editRequest", editRequest);

            return modelAndView;
        }

        userService.editUserProfile(id, editRequest);

        return new ModelAndView("redirect:/home");
    }
}
