package com.probjj.probjj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.probjj.probjj.models.User;

@Controller
@RequestMapping("/users")
public class UserControllers {

    @GetMapping
    public String listUsers() {
        // Logic to list users
        return "user/list";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id) {
        // Logic to get a user by ID
        return "user/detail";
    }

    @PostMapping
    public String createUser(@RequestBody User user) {
        // Logic to create a new user
        return "redirect:/users";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User user) {
        // Logic to update an existing user
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        // Logic to delete a user
        return "redirect:/users";
    }

}
