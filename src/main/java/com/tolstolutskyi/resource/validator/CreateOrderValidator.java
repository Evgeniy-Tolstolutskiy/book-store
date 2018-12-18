package com.tolstolutskyi.resource.validator;

import com.tolstolutskyi.model.Book;
import com.tolstolutskyi.model.BookOrder;
import com.tolstolutskyi.model.Order;
import com.tolstolutskyi.repository.BookRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateOrderValidator implements Validator {
    private final BookRepository bookRepository;

    public CreateOrderValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Order.class.equals(aClass);
    }

    @Override
    public void validate(@Nullable Object o, Errors errors) {
        Order order = (Order) o;

        Iterable<Book> books = bookRepository.findAllById(
            order.getBookOrders().stream().map(bookOrder -> bookOrder.getBook().getId()).collect(Collectors.toList()));
        Double total = 0.0;
        for (Book book : books) {
            Integer countFromClient = getBookById(order.getBookOrders(), book.getId()).getCount();
            if (book.getCount() < countFromClient) {
                errors.reject("Wrong request data");
                return;
            }
            total += book.getPrice() * countFromClient;
        }
        if (!total.equals(order.getPrice())) {
            errors.reject("Wrong request data");
        }
    }

    private Book getBookById(List<BookOrder> bookOrders, Long bookId) {
        for (BookOrder bookOrder : bookOrders) {
            if (bookOrder.getBook().getId().equals(bookId)) {
                return bookOrder.getBook();
            }
        }
        return null;
    }
}
