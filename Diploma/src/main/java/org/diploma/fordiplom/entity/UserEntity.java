package org.diploma.fordiplom.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("id_user")
    private long id_user;
    @Column(name = "email", nullable = false,unique = true)
    @Name("email")
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name="position")
    @Name("position")
    private String position;
    @Column(name="organization")
    @Name("organization")
    private String organization;
    @Column(name = "username")
    private String username;
    @Transient
    private String confirmPassword;
    @ManyToMany(mappedBy = "emails")
//    @JsonBackReference
    @JsonIgnore
    private Set<TeamEntity> teams = new HashSet<>();
    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<ProjectEntity> projects = new HashSet<>();
}
