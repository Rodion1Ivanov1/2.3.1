package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;


//Заходить на страницу по /users
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String getUsers(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }


    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable("id") int id, Model model) {
        User user = userService.showUser(id);
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(User user, @PathVariable("id") int id) {
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @GetMapping("/new")
    public String getUserForm(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }


    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/users";
    }


}