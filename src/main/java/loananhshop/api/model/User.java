package loananhshop.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")

public class User {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column
    private String address;

    @Column
    private String age;

    @Column
    private String gender;

    private String photoUrl;
    private String provider;

    @Column (nullable = false)
    private boolean activeStatus = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
