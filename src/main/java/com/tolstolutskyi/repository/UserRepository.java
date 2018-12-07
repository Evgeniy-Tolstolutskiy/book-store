package com.tolstolutskyi.repository;

import com.tolstolutskyi.model.User;
import com.tolstolutskyi.model.projection.UserProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "users", path = "users", excerptProjection = UserProjection.class)
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("select u from User u where email = cast(:email as string)")
    User findByEmail(@Param("email") String email);

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    Optional<User> findById(Long aLong);
}
