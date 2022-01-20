package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.Order;
import com.cg.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByDeletedIsFalse() ;

    List<Order> findAllByCustomerAndDeletedIsFalse(Customer customer);

    List<Order> findAllByCustomer(Customer customer);
}
