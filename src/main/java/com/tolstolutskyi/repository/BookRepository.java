package com.tolstolutskyi.repository;

import com.tolstolutskyi.model.Book;
import com.tolstolutskyi.model.projection.BookProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "books", path = "books", excerptProjection = BookProjection.class)
@EnableJpaRepositories
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    @Query(value = "SELECT * FROM book WHERE ?#{hasRole('ROLE_ADMIN')} OR (?#{hasRole('ROLE_USER')} AND visible)", nativeQuery = true)
    Page<Book> findAll(Pageable pageable);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    <S extends Book> S save(S s);

    @Modifying
    @Query(value = "UPDATE book SET photo_link = :link WHERE id = :id", nativeQuery = true)
    @RestResource(exported = false)
    void saveImage(@Param("id") Long id, @Param("link") String link);

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
