package com.cg.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[a-z][a-z0-9_]{6,16}$", message = "Username must be between 6 and 16 character and start with word!")
    private String username;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\\\d)[A-Za-z\\\\d]{6,}$", message = "Password must be at least 6 character without special character!")
    private String password;

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

}
