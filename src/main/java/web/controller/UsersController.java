package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping("/")
public class UsersController {
    private final UserService userServiceImpl;

    public UsersController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("")
    public String startPageView() {
        return "index";
    }

    @GetMapping("add-user")
    public String addUserForm(@ModelAttribute("user") User user) {
        return "add-user";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userServiceImpl.createUser(user);
        return "redirect:/all-users";
    }

    @GetMapping("all-users")
    public String readAllUsers(Model model) {
        model.addAttribute("users", userServiceImpl.readAllUsers());
        return "all-users";
    }

    @GetMapping("/edit-users")
    public String editUsersTable(Model model) {
        model.addAttribute("users", userServiceImpl.readAllUsers());
        return "edit-users";
    }

    @GetMapping("/edit-user/{id}")
    public String editUserForm(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("user", userServiceImpl.readUser(id));
        return "edit-user";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userServiceImpl.updateUser(user);
        return "redirect:/edit-users";
    }

    @GetMapping("/delete-users")
    public String deleteUsersTable(Model model) {
        model.addAttribute("users", userServiceImpl.readAllUsers());
        return "delete-users";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/delete-users";
    }
}
