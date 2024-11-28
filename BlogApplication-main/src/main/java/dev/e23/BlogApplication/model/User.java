package dev.e23.BlogApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role = "user";  // 默认角色为 "user"

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean hasRole(String role) {
        return this.role != null && this.role.equalsIgnoreCase(role);
    }
}
