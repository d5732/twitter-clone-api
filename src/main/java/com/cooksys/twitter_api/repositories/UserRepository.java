package com.cooksys.twitter_api.repositories;

import com.cooksys.twitter_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndDeletedFalse(String username);

    List<User> findAllByDeletedFalse();

    List<User> findAllByDeletedFalseAndIdIn(List<Long> ids);

    Optional<User> findByUsername(String username);

}
