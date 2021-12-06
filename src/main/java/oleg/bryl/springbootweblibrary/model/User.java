package oleg.bryl.springbootweblibrary.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
//    @Size(min=2, max = 10, message = "length should be beetwen 2 to 10 letters")
    private String username;

    @NotBlank
//    @Size(min=2, max = 10, message = "length should be beetwen 2 to 10 letters")
    private String password;

    @NotEmpty(message = "e-mail can't be empty")
    @Email(regexp = "^(.+)@(.+)$", message = "invalid e-mail pattern")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roleList= new ArrayList();

    @OneToMany(mappedBy = "user")
    private List<Book> bookList = new ArrayList<>();

    public User() {
    }

    /**
     *
     * @param username
     * @param password
     * @param email
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     *
     * @param username
     * @param password
     * @param email
     * @param roleList
     */
    public User(String username, String password, String email, List<Role> roleList) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleList = roleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roleList=" + roleList +
                ", bookList=" + bookList +
                '}';
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        String ROLE_PREFIX = "ROLE_";

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        for(Role role:roleList){
            list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRolename()));
        }
        return list;
    }
}