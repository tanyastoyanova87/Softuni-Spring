package com.dictionaryapp.web;

import com.dictionaryapp.user.model.User;
import com.dictionaryapp.user.service.UserService;
import com.dictionaryapp.web.dto.AddWordRequest;
import com.dictionaryapp.word.service.WordService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping(("/words"))
public class WordController {

    private final WordService wordService;
    private final UserService userService;

    public WordController(WordService wordService, UserService userService) {
        this.wordService = wordService;
        this.userService = userService;
    }

    @GetMapping("/new")
    public ModelAndView getAddWordPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("word-add");
        modelAndView.addObject("addWordRequest", AddWordRequest.builder().build());

        return modelAndView;
    }

    @PostMapping("/new")
    public String addWord(@Valid AddWordRequest addWordRequest, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "word-add";
        }

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = this.userService.getById(userId);

        this.wordService.addWord(addWordRequest, user);

        return "redirect:/home";
    }

    @DeleteMapping("/{id}")
    public String removeWord(@PathVariable UUID id) {
        this.wordService.removeWordById(id);

        return "redirect:/home";
    }

    @DeleteMapping("/allWords")
    public String removeAllWord() {
        this.wordService.removeAllWords();

        return "redirect:/home";
    }
}
