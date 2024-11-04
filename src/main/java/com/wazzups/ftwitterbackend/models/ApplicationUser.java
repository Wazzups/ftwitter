package com.wazzups.ftwitterbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    private String phone;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_junction",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> authorities;

    private Boolean enabled;

    @JsonIgnore
    private Long verificationCode;

    public ApplicationUser() {
        this.authorities = new HashSet<>();
        this.enabled = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(Long verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", dateOfBirth=" + dateOfBirth +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", authorities=" + authorities +
            '}';
    }
}
