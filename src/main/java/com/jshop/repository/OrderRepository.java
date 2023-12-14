package com.jshop.repository;

import com.jshop.entity.Order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findById(Long id);

    List<Order> findByIdAccount(Integer idAccount, Pageable pageble);

    int countByIdAccount(Integer idAccount);
}
