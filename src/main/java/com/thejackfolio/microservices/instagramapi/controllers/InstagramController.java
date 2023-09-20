package com.thejackfolio.microservices.instagramapi.controllers;

import com.thejackfolio.microservices.instagramapi.exceptions.*;
import com.thejackfolio.microservices.instagramapi.models.InstagramPostsResponse;
import com.thejackfolio.microservices.instagramapi.models.InstagramPostsResponseWrapper;
import com.thejackfolio.microservices.instagramapi.services.InstagramService;
import com.thejackfolio.microservices.instagramapi.utilities.StringConstants;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Instagram", description = "Get Instagram Posts")
@RestController
@RequestMapping("/instagram")
public class InstagramController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramController.class);
    private boolean isRetryEnabled = false;

    @Autowired
    private InstagramService service;

    @Operation(
            summary = "Get Posts using old token",
            description = "Gets top 5 Posts from my instagram account"
    )
    @GetMapping("/get-posts")
    @Retry(name = "get-posts-retry", fallbackMethod = "getPostsRetry")
    //@CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<InstagramPostsResponseWrapper> getInstagramPostsUsingOldAuthCode(){
        InstagramPostsResponseWrapper wrapper = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            InstagramPostsResponse response = service.getInstagramPosts();
            wrapper = new InstagramPostsResponseWrapper();
            wrapper.setResponse(response);
            wrapper.setMessage(StringConstants.REQUEST_PROCESSED);
        } catch (ResponseException exception){
            wrapper = new InstagramPostsResponseWrapper();
            wrapper.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(wrapper);
        } catch (PostException | DataBaseOperationException | MapperException | ValidationException exception){
            wrapper = new InstagramPostsResponseWrapper();
            wrapper.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(wrapper);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(wrapper);
    }

    public ResponseEntity<InstagramPostsResponseWrapper> getPostsRetry(Exception exception){
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        InstagramPostsResponseWrapper wrapper = new InstagramPostsResponseWrapper();
        wrapper.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(wrapper);
    }

    @Operation(
            summary = "Get Posts using new token",
            description = "Gets top 5 Posts from my instagram account"
    )
    @GetMapping("/get-posts-new-token")
    @Retry(name = "get-posts-new-token-retry", fallbackMethod = "getPostsNewTokenRetry")
    public ResponseEntity<InstagramPostsResponseWrapper> getPostsUsingNewToken(@RequestParam(value = "access_token")String token){
        InstagramPostsResponseWrapper wrapper = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            InstagramPostsResponse response = service.processTokensAndReturnPostsResponse(token, true);
            wrapper = new InstagramPostsResponseWrapper();
            wrapper.setResponse(response);
            wrapper.setMessage(StringConstants.REQUEST_PROCESSED);
        } catch (ResponseException exception){
            wrapper = new InstagramPostsResponseWrapper();
            wrapper.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(wrapper);
        } catch (PostException | DataBaseOperationException | MapperException | ValidationException exception){
            wrapper = new InstagramPostsResponseWrapper();
            wrapper.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(wrapper);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(wrapper);
    }

    public ResponseEntity<InstagramPostsResponseWrapper> getPostsNewTokenRetry(Exception exception){
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        InstagramPostsResponseWrapper wrapper = new InstagramPostsResponseWrapper();
        wrapper.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(wrapper);
    }
}
