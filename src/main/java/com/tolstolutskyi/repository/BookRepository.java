package com.tolstolutskyi.repository;

import com.tolstolutskyi.model.Book;
import com.tolstolutskyi.model.projection.BookProjection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "books", path = "books", excerptProjection = BookProjection.class)
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    <S extends Book> S save(S s);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    <S extends Book> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Optional<Book> findById(Long aLong);

    @Override
    @RestResource(exported = false)
    boolean existsById(Long aLong);

    @Override
    @RestResource(exported = false)
    long count();

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteById(Long aLong);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(Book book);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteAll(Iterable<? extends Book> iterable);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteAll();
}
