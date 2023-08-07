package com.drycode.services.impl;

import com.drycode.models.Book;
import com.drycode.models.Order;
import com.drycode.models.Status;
import com.drycode.repositories.OrderRepository;
import com.drycode.services.BookService;
import com.drycode.services.GenerateIDService;
import com.drycode.services.OrderService;
import com.drycode.services.StatusService;
import com.drycode.utils.ConstantString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String ENTITY = "Order";
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);
    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final StatusService statusService;
    private final GenerateIDService generateSequence;

    public OrderServiceImpl(OrderRepository orderRepository, BookService bookService, GenerateIDService generateSequence,
                            StatusService statusService) {
        this.bookService = bookService;
        this.orderRepository = orderRepository;
        this.generateSequence = generateSequence;
        this.statusService = statusService;
    }

    @Override
    public Order placeOrder(Order order) throws Exception {
        LOGGER.info(ConstantString.SAVE_LOG.replaceAll("#ENTITY", ENTITY), order);
        order.setId(generateSequence.generateSequence(Book.SEQUENCE_NAME));
        order.setOrderPlacedDate(LocalDateTime.now());
        if (!ObjectUtils.isEmpty(order.getBooks()) && order.getBooks().size() > 0) {
            Set<Book> booksToBeUpdated = new HashSet<>();
            order.getBooks().forEach(orderedBook -> {
                Book existingBook = this.bookService.findById(orderedBook.getId());
                if (ObjectUtils.isEmpty(existingBook)) {
                    throw new RuntimeException("Selected book does not exists: " + orderedBook.getName());
                }
                int quantity = existingBook.getQuantity() - 1;
                if (quantity >= 0) {
                    existingBook.setQuantity(quantity);
                    booksToBeUpdated.add(existingBook);
                } else {
                    throw new RuntimeException("Selected book is out of stock: " + orderedBook.getName());
                }
            });
            setOrderStatus(order, "CREATED");
            updateBooks(booksToBeUpdated);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Can not place an order with out any book selected: ");
        }
    }

    private void setOrderStatus(Order order, String status) throws Exception {
        List<Status> orderStatuses = this.statusService.findByName(status);
        if (ObjectUtils.isEmpty(orderStatuses)) {
            Status orderStatus = new Status();
            orderStatus.setName(status);
            orderStatus.setDescription(status);
            orderStatus = this.statusService.save(orderStatus);
            order.setStatus(orderStatus);
            return;
        }
        order.setStatus(orderStatuses.get(0));
    }

    private void updateBooks(Set<Book> booksToBeUpdated) throws Exception {
        this.bookService.saveAll(booksToBeUpdated);
    }

    @Override
    public Order approveOrder(Order order) throws Exception {
        LOGGER.info(ConstantString.SAVE_LOG.replaceAll("#ENTITY", ENTITY), order);
        setOrderStatus(order, "APPROVED");
        return this.orderRepository.save(order);
    }

    @Override
    public Order findById(Long id) {
        return this.orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order cancelOrder(Order order) throws Exception {
        LOGGER.info(ConstantString.SAVE_LOG.replaceAll("#ENTITY", ENTITY), order);
        if (!ObjectUtils.isEmpty(order.getBooks()) && order.getBooks().size() > 0) {
            Set<Book> booksToBeUpdated = new HashSet<>();
            order.getBooks().forEach(orderedBook -> {
                Book existingBook = this.bookService.findById(orderedBook.getId());
                if (ObjectUtils.isEmpty(existingBook)) {
                    throw new RuntimeException("Selected book does not exists: " + orderedBook.getName());
                }
                int quantity = existingBook.getQuantity() + 1;
                existingBook.setQuantity(quantity);
                booksToBeUpdated.add(existingBook);
            });
            setOrderStatus(order, "CANCELLED");
            updateBooks(booksToBeUpdated);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Can not place an order with out any book selected: ");
        }
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        LOGGER.info(ConstantString.FIND_BY_LOG.replaceAll("#ENTITY", ENTITY), pageable);
        return orderRepository.findAll(pageable);
    }
}
