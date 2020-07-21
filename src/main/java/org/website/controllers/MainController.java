package org.website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.website.models.Reviews;
import org.website.models.Roles;
import org.website.models.User;
import org.website.repo.ReviewRepository;
import org.website.repo.UserRepo;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        model.put("name", "name");
        return "greeting";
    }
    @GetMapping("/about")
    public String about(Map<String, Object> model) {
        model.put("title", "Window about");
        return "about";
    }
    @GetMapping("/reviews")
    public String reviews(Map<String, Object> model) {
        Iterable<Reviews> reviews = reviewRepository.findAll();
        model.put("title", "Window about");
        model.put("reviews", reviews);
        return "reviews";
    }

    @PostMapping("/reviews-add")
    public String reviewsAdd(@RequestParam String title, @RequestParam String text, Map<String, Object> model){
        Reviews reviews = new Reviews(title, text);
        reviewRepository.save(reviews);

        return "redirect:/reviews";
    }

    @GetMapping("/reviews/{id}")
    public String reviewInfo(@PathVariable(value = "id") long reviewId, Map<String, Object> model) {
       Optional<Reviews> review =  reviewRepository.findById(reviewId);

        ArrayList<Reviews> result = new ArrayList<>();
        review.ifPresent(result::add);
        model.put("review", result);
        return "review-info";
    }

    @GetMapping("/reviews/{id}/update")
    public String reviewUpdate(@PathVariable(value = "id") long reviewId, Map<String, Object> model) {
        Optional<Reviews> review =  reviewRepository.findById(reviewId);

        ArrayList<Reviews> result = new ArrayList<>();
        review.ifPresent(result::add);
        model.put("review", result);
        return "review-update";
    }
    @PostMapping("/reviews/{id}/update")
    public String reviewsUpdate(@PathVariable(value = "id") long reviewId, @RequestParam String title, @RequestParam String text, Map<String, Object> model) throws ClassNotFoundException {
        Reviews reviews = reviewRepository.findById(reviewId).orElseThrow(() -> new ClassNotFoundException());
        reviews.setTitle(title);
        reviews.setText(text);
        reviewRepository.save(reviews);

        return "redirect:/reviews/" + reviewId;
    }

    @PostMapping("/reviews/{id}/delete")
    public String reviewsDelete(@PathVariable(value = "id") long reviewId, Map<String, Object> model) throws ClassNotFoundException {
        Reviews reviews = reviewRepository.findById(reviewId).orElseThrow(() -> new ClassNotFoundException());

        reviewRepository.delete(reviews);

        return "redirect:/reviews";
    }
    @GetMapping("/reg")
    public String reg() {
        return "reg";
    }
    @PostMapping("/reg")
    public String addUser(User user, Map<String, Object> model){
        user.setEnabled(true);
        user.setRoles(Collections.singleton(Roles.USER));
        userRepo.save(user);
        return "redirect: login";
    }

}