package boot.service;

import boot.model.Role;
import boot.model.User;
import boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User findUserById(Long id) {
        return userRepository.getOne(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.delete(findUserById(id));
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).get();
    }
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + userName + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                new BCryptPasswordEncoder().encode(user.getPassword()),
                getAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles().stream().map(Role::getName).toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(userRoles);
    }


}
