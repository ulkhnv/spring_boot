package boot.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;
 
    @Column
    private String lastName;

    @Column
    private Integer age;

    @Column
    private String email;

    @Column
    private String password;

    @ManyToMany(cascade=CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(
            name="user_role",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private Collection<Role> roles;

    public User() {
    }

    public User(String firstName, String lastName, Integer age, String email, String password,Collection<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles=roles;
    }

    public User(Long id, String firstName, String lastName, Integer age, String email, String password,Collection<Role> roles) {
        this(firstName, lastName, age, email, password,roles);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getStringRoles() {
        StringBuffer buffer = new StringBuffer();
        for (Role role : getRoles()) {
            if (role.getId().equals(1)) {
                buffer.append("ADMIN ");
            }
            else if (role.getId().equals(2)) {
                buffer.append("USER");
            }
        }
        return buffer.toString();
    }

    public boolean isAdmin() {
        return getStringRoles().contains("ADMIN");
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + age + " " + password+" "+getStringRoles();
    }
}


