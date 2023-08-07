package com.drycode.services;

import com.drycode.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Order placeOrder(Order order) throws Exception;
    Order approveOrder(Order order) throws Exception;
    Order findById(Long id);
    Order cancelOrder(Order order) throws Exception;
    Page<Order> findAll(Pageable pageable) ;
}
