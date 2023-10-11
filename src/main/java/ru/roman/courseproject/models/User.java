package ru.roman.courseproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "username")
    @Size(min = 2, max = 50, message = "Логин должен быть от 2 до 50 символов")
    private String username;

    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "email")
    @Size(min = 2, max = 100, message = "Email должен быть от 2 до 100 символов")
    @Email(message = "Email задан не корректно")
    private String email;

    @NotNull
    @Column(name = "full_name")
    @Size(min = 2, max = 100, message = "Имя должен быть от 2 до 100 символов")
    private String fullName;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles = new ArrayList<>();
}
