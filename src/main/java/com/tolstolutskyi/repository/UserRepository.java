package com.tolstolutskyi.repository;

import com.tolstolutskyi.model.User;
import com.tolstolutskyi.model.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "users", path = "usersRepository", excerptProjection = UserProjection.class)
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE email = CAST(:email AS string)")
    @RestResource(exported = false)
    User findByEmail(@Param("email") String email);

    @Override
    @RestResource(exported = false)
    Optional<User> findById(Long aLong);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Iterable<User> findAll(Sort sort);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Query(value = "SELECT * FROM user_info WHERE CAST(id AS VARCHAR) <> ?#{principal}", nativeQuery = true)
    Page<User> findAll(Pageable pageable);

    @Override
    @RestResource(exported = false)
    <S extends User> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    boolean existsById(Long aLong);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Iterable<User> findAll();

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Iterable<User> findAllById(Iterable<Long> iterable);

    @Override
    @RestResource(exported = false)
    long count();

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(User user);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteAll(Iterable<? extends User> iterable);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteAll();

    @Override
    @RestResource(exported = false)
    <S extends User> S save(S s);
}
