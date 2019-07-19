package com.stasdev.backend.entitys;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String username;
    private String password;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "User_Role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "User_Stock",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "stockId") }
    )
    private List<Stock> stocks = new ArrayList<>();

    public ApplicationUser(String s, String pass, Set<Role> roles) {
        this.username = s;
        this.password = pass;
        this.roles = roles;
    }

    public ApplicationUser(String s, String pass) {
        this.username = s;
        this.password = pass;
    }

    public ApplicationUser(){

    }

    public ApplicationUser withPassword(String pass){
        this.setPassword(pass);
        return this;
    }

    public Long getUser_id() {
        return user_id;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public ApplicationUser setStocks(List<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }
}
