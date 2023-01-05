package com.cooksys.twitter_api.repositories;

import com.cooksys.twitter_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);

    List<User> findAllByDeletedFalse();

    List<User> findAllByDeletedFalseAndIdIn(List<Long> ids);

    Optional<User> findByCredentialsUsername(String username);

    Optional<User> findByIdAndDeletedFalse(Long id);
	

}
