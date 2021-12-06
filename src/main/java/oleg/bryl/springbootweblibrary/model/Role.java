package oleg.bryl.springbootweblibrary.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rolename;

    @ManyToMany(mappedBy = "roleList", fetch = FetchType.EAGER)
    private List<User> userList = new ArrayList<>();

    /**
     *
     * @param rolename
     */
    public Role(String rolename) {
        this.rolename = rolename;
    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String role) {
        this.rolename = role;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "Role{" +
                ", rolename='" + rolename + '\'' +
                '}';
    }
}