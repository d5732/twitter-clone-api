package com.cooksys.twitter_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.twitter_api.entities.Hashtag;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	Optional<Hashtag> findByLabelAndDeletedFalse(String label);

	Optional<Hashtag> findByIdAndDeletedFalse(Long id);

	List<Hashtag> findAllByDeletedFalse();
	
	Optional<Hashtag> findByLabel(String label);

}
