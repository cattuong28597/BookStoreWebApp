package com.cg.repository;

import com.cg.model.Category;
import com.cg.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Boolean existsByUsernameEquals(String username);

    Boolean existsByUsernameEqualsAndIdIsNot(String username, long id);
}
