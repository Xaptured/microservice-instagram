package com.thejackfolio.microservices.instagramapi.controllers;

import com.thejackfolio.microservices.instagramapi.exceptions.*;
import com.thejackfolio.microservices.instagramapi.models.InstagramPostsResponse;
import com.thejackfolio.microservices.instagramapi.models.InstagramPostsResponseWrapper;
import com.thejackfolio.microservices.instagramapi.services.InstagramService;
import com.thejackfolio.microservices.instagramapi.utilities.StringConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Instagram", description = "Get Instagram Posts")
@RestController
@RequestMapping("/instagram")
public class InstagramController {

    @Autowired
    private InstagramService service;

    @Operation(
            summary = "Get Posts using old token",
            description = "Gets top 5 YouTube Video according to date from my channel"
    )
    @GetMapping("/get-posts")
    public ResponseEntity<InstagramPostsResponseWrapper> getInstagramPostsUsingOldAuthCode(){
        InstagramPostsResponseWrapper wrapper = null;
        try{
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
        return ResponseEntity.status(HttpStatus.OK).body(wrapper);
    }

    @Operation(
            summary = "Get Posts using new token",
            description = "Gets top 5 YouTube Video according to date from my channel"
    )
    @GetMapping("/get-posts-new-token")
    public ResponseEntity<InstagramPostsResponseWrapper> getPostsUsingNewToken(@RequestParam(value = "access_token")String token){
        InstagramPostsResponseWrapper wrapper = null;
        try{
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
        return ResponseEntity.status(HttpStatus.OK).body(wrapper);
    }
}