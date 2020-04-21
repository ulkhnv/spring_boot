package boot.controller;

import boot.model.Role;
import boot.model.User;
import boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
        model.addAttribute("users", userService.findAllUsers());
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

    @PostMapping("/admin/add")
    public String addUser(User user, @RequestParam(name = "role") String role) {
        List<Role> roles = new ArrayList<>();
        if (role.equals("ADMIN")) {
            roles.add(new Role(1, "ROLE_ADMIN"));
        } else {
            roles.add(new Role(2, "ROLE_USER"));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}
