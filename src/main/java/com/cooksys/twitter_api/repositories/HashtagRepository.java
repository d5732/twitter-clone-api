package com.cooksys.twitter_api.repositories;

import com.cooksys.twitter_api.entities.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	Optional<Hashtag> findByLabelAndDeletedFalse(String label);

	Optional<Hashtag> findByIdAndDeletedFalse(Long id);

	List<Hashtag> findAllByDeletedFalse();

}
