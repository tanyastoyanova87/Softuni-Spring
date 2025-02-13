package com.paintingscollectors.web;

import com.paintingscollectors.painting.service.PaintingService;
import com.paintingscollectors.user.model.User;
import com.paintingscollectors.user.service.UserService;
import com.paintingscollectors.web.dto.CreateNewPainting;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("paintings")
public class PaintingsController {

    private final PaintingService paintingService;
    private final UserService userService;

    @Autowired
    public PaintingsController(PaintingService paintingService, UserService userService) {
        this.paintingService = paintingService;
        this.userService = userService;
    }

    @GetMapping("/new")
    public ModelAndView getPaintingsPage(HttpSession session) {
        UUID id = (UUID) session.getAttribute("user_id");
        User user = this.userService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new-painting");
        modelAndView.addObject("user", user);
        modelAndView.addObject("createNewPainting", CreateNewPainting.builder().build());

        return modelAndView;
    }

    @PostMapping
    public String createPainting(@Valid CreateNewPainting createNewPainting, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "new-painting";
        }

        UUID id = (UUID) session.getAttribute("user_id");
        User user = this.userService.getById(id);

        this.paintingService.createPainting(createNewPainting, user);
        return "redirect:/home";
    }

    @DeleteMapping("/{id}")
    public String deletePainting(@PathVariable UUID id) {

        this.paintingService.deletePaintingById(id);
        return "redirect:/home";
    }

    @PostMapping("/favourites/{id}")
    public String makeFavouritePainting(@PathVariable UUID id, HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = this.userService.getById(userId);

        this.paintingService.makeFavouritePainting(user, id);
        return "redirect:/home";
    }

    @DeleteMapping("/favourites/{id}")
    public String deleteFavouritePainting(@PathVariable UUID id) {
        System.out.println(id);
        this.paintingService.deleteFavouritePaintingById(id);

        return "redirect:/home";
    }

    @PutMapping("/{id}/votes")
    public String updateVotes(@PathVariable UUID id) {
        this.paintingService.incrementVotesByOne(id);

        return "redirect:/home";
    }
}
