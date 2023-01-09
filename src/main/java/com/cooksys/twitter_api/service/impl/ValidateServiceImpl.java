package com.cooksys.twitter_api.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.service.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;


    /**
     * GET validate/username/available/@{username} #45
     * <p>
     * Checks whether or not a given username is available
     */
    @Override
    public boolean usernameAvailable(String username) {

        Optional<User> user = userRepository.findByCredentialsUsername(username);

        return user.isEmpty();
    }

    /**
     * GET validate/username/exist/@(username)
     * <p>
     * Checks whether or not a given username exists.
     * <p>
     * Response 'boolean'
     */
    @Override
    public boolean usernameExists(String username) {

        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

        return user.isPresent();
    }


    /**
     * GET validate/tag/exists/{label} endpoint
     * <p>
     * Checks whether or not a given hashtag exists.
     * <p>
     * Response 'boolean'
     */
    @Override
    public boolean hashtagExists(String label) {

        Optional<Hashtag> hashtag = hashtagRepository.findByLabel(label);

        return hashtag.isPresent();
    }

}
