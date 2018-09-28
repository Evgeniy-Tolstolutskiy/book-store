package com.tolstolutskyi.repository;

import com.tolstolutskyi.model.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
}
