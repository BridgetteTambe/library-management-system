package com.drycode.web;

import com.drycode.models.Order;
import com.drycode.services.OrderService;
import com.drycode.utils.ConstantString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class OrderResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

    private static final String ENTITY = "Order";
    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/place-order")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) throws Exception {
        LOGGER.info(ConstantString.REST_SAVE_LOG.replaceAll("#ENTITY", ENTITY), order);
        if (order.getId() != null) {
            throw new Exception(ConstantString.ID_EXISTS.replaceAll("#ID", String.valueOf(order.getId()))
                    .replaceAll("#ENTITY", ENTITY));
        }
        Order save = this.orderService.placeOrder(order);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/order/approve-order")
    public ResponseEntity<Order> approveOrder(@RequestBody Order order) throws Exception {
        LOGGER.info(ConstantString.REST_UPDATE_LOG.replaceAll("#ENTITY", ENTITY), order);
        if (order.getId() == null) {
            throw new Exception(ConstantString.ID_NULL.replaceAll("#ID", null)
                    .replaceAll("#ENTITY", ENTITY));
        }
        Order save = this.orderService.approveOrder(order);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/order/cancel-order")
    public ResponseEntity<Order> cancelOrder(@RequestBody Order order) throws Exception {
        LOGGER.info(ConstantString.REST_UPDATE_LOG.replaceAll("#ENTITY", ENTITY), order);
        if (order.getId() == null) {
            throw new Exception(ConstantString.ID_NULL.replaceAll("#ID", null)
                    .replaceAll("#ENTITY", ENTITY));
        }
        Order save = this.orderService.cancelOrder(order);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/order")
    public ResponseEntity<Page<Order>> findAllBook(Pageable pageable) {
        LOGGER.info(ConstantString.REST_GET_LOG.replaceAll("#ENTITY", ENTITY), pageable);
        return ResponseEntity.ok(this.orderService.findAll(pageable));
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        LOGGER.info(ConstantString.REST_GET_LOG.replaceAll("#ENTITY", ENTITY), id);
        return ResponseEntity.ok(this.orderService.findById(id));
    }


}
