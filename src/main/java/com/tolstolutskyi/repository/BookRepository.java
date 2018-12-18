package com.tolstolutskyi.repository;

import com.tolstolutskyi.model.Book;
import com.tolstolutskyi.model.projection.BookProjection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "books", path = "books", excerptProjection = BookProjection.class)
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}
