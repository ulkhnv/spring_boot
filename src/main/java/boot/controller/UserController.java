package boot.controller;

import boot.model.User;
import boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/admin")
    public String getAdmin(Model model, HttpSession session) {
        User user = userService.findUserByEmail(session.getAttribute("email").toString());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("roles", user.getStringRoles());
        model.addAttribute("user", new User());
        model.addAttribute("userUpdate", new User());
        return "admin";
    }

    @GetMapping("/user")
    public String getUser(Model model, HttpSession session) {
        User user = userService.findUserByEmail(session.getAttribute("email").toString());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("roles", user.getStringRoles());
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", user.isAdmin());
        return "user";
    }
}
