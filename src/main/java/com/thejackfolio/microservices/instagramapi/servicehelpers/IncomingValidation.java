package com.thejackfolio.microservices.instagramapi.servicehelpers;

import com.thejackfolio.microservices.instagramapi.exceptions.PostException;
import com.thejackfolio.microservices.instagramapi.exceptions.ValidationException;
import com.thejackfolio.microservices.instagramapi.models.InstagramPostsResponse;
import com.thejackfolio.microservices.instagramapi.models.LongLivedTokenResponse;
import com.thejackfolio.microservices.instagramapi.models.Post;
import com.thejackfolio.microservices.instagramapi.utilities.StringConstants;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class IncomingValidation {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingValidation.class);

    public void validateInstagramPostsGraphAPIResponse(InstagramPostsResponse response) throws PostException {
        if(response != null && response.getData() != null && !response.getData().isEmpty()){
            for(Post post : response.getData()){
                if(Strings.isEmpty(post.getMedia_url()) || Strings.isBlank(post.getMedia_url())){
                    LOGGER.info("Post URL is null in response item:{}", post);
                    throw new PostException("Post URL is null in response");
                }
            }
        }
    }

    public void validateAccessToken(LongLivedTokenResponse response) throws ValidationException {
        if(response == null){
            LOGGER.error("Validation failed in IncomingValidations.java : validateAccessToken for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        validateLongLivedAccessToken(response);
    }

    private void validateLongLivedAccessToken(LongLivedTokenResponse response) throws ValidationException {
        if(Strings.isNotEmpty(response.getAccess_token()) && Strings.isNotBlank(response.getAccess_token()) && response.getExpires_in() != null){
            LOGGER.info("Validation passed for validateLongLivedAccessToken");
        } else {
            LOGGER.error("Validation failed in IncomingValidations.java : validateLongLivedAccessToken for object: {}", response);
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
    }
}
