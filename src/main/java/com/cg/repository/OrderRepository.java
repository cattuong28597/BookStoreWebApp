package com.cg.repository;

import com.cg.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByDeletedIsFalse() ;


    @Query(value = "SELECT o FROM Order o WHERE o.deleted = false order by o.createdAt DESC ")
    List<Order> findAllByDeletedIsFalseAndOrderByCreatedAtConfirmDesc() ;
}
