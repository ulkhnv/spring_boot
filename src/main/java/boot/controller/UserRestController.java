package boot.controller;

import boot.model.Role;
import boot.model.User;
import boot.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/admin")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody Map<String,String> data) {
        List<Role> roles = new ArrayList<>();
        User user = new User(
                data.get("firstName"),
                data.get("lastName"),
                Integer.parseInt(data.get("age")),
                data.get("email"),
                data.get("password")
        );
        if (data.get("role").contains("ADMIN")) {
            roles.add(new Role(1,"ROLE_ADMIN"));
        } else {
            roles.add(new Role(2,"ROLE_USER)"));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@RequestBody Map<String,String> data, @PathVariable Long id) {
        List<Role> roles = new ArrayList<>();
        User user = new User(
                id,
                data.get("firstName"),
                data.get("lastName"),
                Integer.parseInt(data.get("age")),
                data.get("email"),
                data.get("password")
        );
        if (data.get("role").contains("ADMIN")) {
            roles.add(new Role(1,"ROLE_ADMIN"));
        } else {
            roles.add(new Role(2,"ROLE_USER)"));
        }
        user.setRoles(roles);
        try {
            userService.findUserById(id);
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteUser{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}