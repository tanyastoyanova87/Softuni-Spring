package app.web;

import app.story.model.Story;
import app.story.service.StoryService;
import app.user.model.User;
import app.user.service.UserService;
import app.web.dto.AddStoryRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/stories")
public class StoryController {

    private final StoryService storyService;
    private final UserService userService;

    public StoryController(StoryService storyService, UserService userService) {
        this.storyService = storyService;
        this.userService = userService;
    }

    @GetMapping("/new")
    public ModelAndView getNewStoryPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add-story");
        modelAndView.addObject("addStoryRequest", AddStoryRequest.builder().build());

        return modelAndView;
    }

    @PostMapping("/new")
    public String addNewStory(@Valid AddStoryRequest addStoryRequest, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "add-story";
        }

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = userService.getById(userId);

        storyService.addNewStory(user, addStoryRequest);

        return "redirect:/home";
    }

    @DeleteMapping("/{id}")
    public String deleteStoryById(@PathVariable UUID id) {
        storyService.deleteStory(id);

        return "redirect:/home";
    }

    @PostMapping("/{id}/visibility")
    public String shareStoryById(@PathVariable UUID id) {
        Story story = storyService.getById(id);
        storyService.makeStoryVisible(story);
        storyService.addStoryToSharedStories(story);

        return "redirect:/home";
    }

    @GetMapping("/{id}")
    public ModelAndView readStoryById(@PathVariable UUID id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("story");

        Story story = storyService.getById(id);

        modelAndView.addObject("story", story);

        return modelAndView;
    }
}
