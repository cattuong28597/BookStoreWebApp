package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @NotBlank(message = "Name can't not be blank!")
    @Pattern(regexp = "^[\\pL .,0-9()_:-]{2,50}$", message = "Name must be between 2 and 50 character without special character!")
    private String name;

    @NotBlank(message = "Address can't not be blank!")
    private String address;

    @NotBlank(message = "Phone can't not be blank!")
    @Pattern(regexp = "^[0][1-9][0-9]{8,9}$", message = "phone number length must be 10 or 11 and start with 0!")
    private String phone;

    @NotBlank(message = "Email can't not be blank!")
    @Pattern(regexp = "^[a-z][a-z0-9_\\\\.]{3,32}@[a-z0-9]{2,}(\\\\.[a-z0-9]{2,7}){1,7}$", message = "Email is invalid!")
    private String email;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
