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

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "users", path = "users", excerptProjection = UserProjection.class)
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("select u from User u where email = cast(:email as string)")
    @RestResource(exported = false)
    User findByEmail(@Param("email") String email);

    @Override
    @RestResource(exported = false)
    Optional<User> findById(Long aLong);

    @Override
    Iterable<User> findAll(Sort sort);

    @Override
    Page<User> findAll(Pageable pageable);

    @Override
    <S extends User> S save(S s);

    @Override
    <S extends User> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    boolean existsById(Long aLong);

    @Override
    Iterable<User> findAll();

    @Override
    Iterable<User> findAllById(Iterable<Long> iterable);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(User user);

    @Override
    void deleteAll(Iterable<? extends User> iterable);

    @Override
    void deleteAll();
}
