package com.cg.repository;

import com.cg.model.Category;
import com.cg.model.CategoryGroup;
import com.cg.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {

    Boolean existsBySlugEqualsAndCategoryEquals(String slug, Category category);

    Boolean existsBySlugEqualsAndCategoryEqualsAndIdIsNot(String slug, Category category, Long id);

    CategoryGroup findAllBySlug(String slug);

}
