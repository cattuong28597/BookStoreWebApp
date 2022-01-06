package com.cg.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category_groups")
public class CategoryGroup extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category group name can't not be blank")
    @Pattern(regexp = "^[\\pL .,0-9()_:-]{2,50}$", message = "Category group name must be between 2 and 50 character without special character!")
    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String slug;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "categoryGroup", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;

}
