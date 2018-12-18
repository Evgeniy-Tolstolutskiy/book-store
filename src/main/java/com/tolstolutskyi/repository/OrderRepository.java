package com.tolstolutskyi.repository;

import com.tolstolutskyi.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    @RestResource(exported = false)
    @Query("select o from Order o where userId = :userId")
    List<Order> findOrdersByUserId(@Param("userId") Long userId);

    @Override
    @RestResource(exported = false)
    <S extends Order> S save(S s);
}
