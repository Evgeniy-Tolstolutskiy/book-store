package com.tolstolutskyi.repository;

import com.tolstolutskyi.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    @RestResource(exported = false)
    @Query("select o from Order o where userId = :userId")
    Page<Order> findOrdersByUserId(@Param("userId") Long userId, Pageable pageable);

    @Override
    @RestResource(exported = false)
    <S extends Order> S save(S s);

    @Override
    @RestResource(exported = false)
    Iterable<Order> findAll(Sort sort);

    @Override
    @RestResource(exported = false)
    Page<Order> findAll(Pageable pageable);

    @Override
    @RestResource(exported = false)
    <S extends Order> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    Optional<Order> findById(Long aLong);

    @Override
    @RestResource(exported = false)
    boolean existsById(Long aLong);

    @Override
    @RestResource(exported = false)
    Iterable<Order> findAll();

    @Override
    @RestResource(exported = false)
    Iterable<Order> findAllById(Iterable<Long> iterable);

    @Override
    @RestResource(exported = false)
    long count();

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Order order);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Order> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
