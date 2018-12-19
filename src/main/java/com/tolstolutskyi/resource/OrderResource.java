package com.tolstolutskyi.resource;

import com.tolstolutskyi.model.BookOrder;
import com.tolstolutskyi.model.Order;
import com.tolstolutskyi.repository.BookOrderRepository;
import com.tolstolutskyi.repository.OrderRepository;
import com.tolstolutskyi.resource.validator.CreateOrderValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/orders")
public class OrderResource {
    private final OrderRepository orderRepository;
    private final BookOrderRepository bookOrderRepository;
    private final CreateOrderValidator createOrderValidator;

    public OrderResource(OrderRepository orderRepository, BookOrderRepository bookOrderRepository,
        CreateOrderValidator createOrderValidator) {
        this.orderRepository = orderRepository;
        this.bookOrderRepository = bookOrderRepository;
        this.createOrderValidator = createOrderValidator;
    }

    @GetMapping("/my")
    public ResponseEntity<Page<Order>> getOrders(Principal principal, Pageable pageable) {
        return ResponseEntity.ok(orderRepository.findOrdersByUserId(Long.valueOf(principal.getName()), pageable));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid Order order, Principal principal, BindingResult bindingResult) {
        order.setUserId(Long.valueOf(principal.getName()));
        order.setDate(new Date());
        createOrderValidator.validate(order, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        orderRepository.save(order);
        for (BookOrder bookOrder : order.getBookOrders()) {
            bookOrder.setOrder(order);
        }
        bookOrderRepository.saveAll(order.getBookOrders());
        return ResponseEntity.ok().build();
    }
}
