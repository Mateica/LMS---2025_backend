package main.dto;

import java.util.List;

public class RegisteredUserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean active;
    private List<String> roleNames = List.of();

    public RegisteredUserDTO() {
        super();
    }

    public RegisteredUserDTO(String username, String password, String email) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public RegisteredUserDTO(String username, String password, String email, List<String> roleNames) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleNames = roleNames;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }
}