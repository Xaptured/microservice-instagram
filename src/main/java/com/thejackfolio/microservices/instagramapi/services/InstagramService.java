package com.thejackfolio.microservices.instagramapi.services;

import com.thejackfolio.microservices.instagramapi.clients.TheJackFolioDBClient;
import com.thejackfolio.microservices.instagramapi.exceptions.*;
import com.thejackfolio.microservices.instagramapi.mappers.InstagramTokenMapper;
import com.thejackfolio.microservices.instagramapi.models.InstagramPostsResponse;
import com.thejackfolio.microservices.instagramapi.models.Instagram_Token;
import com.thejackfolio.microservices.instagramapi.models.LongLivedTokenResponse;
import com.thejackfolio.microservices.instagramapi.servicehelpers.IncomingValidation;
import com.thejackfolio.microservices.instagramapi.servicehelpers.InstagramServiceHelper;
import com.thejackfolio.microservices.instagramapi.utilities.StringConstants;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

@Service
public class InstagramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramService.class);

    @Autowired
    private TheJackFolioDBClient dbClient;
    @Autowired
    private InstagramServiceHelper helper;
    @Autowired
    private IncomingValidation validation;
    @Autowired
    private InstagramTokenMapper mapper;

    public InstagramPostsResponse getInstagramPosts() throws DataBaseOperationException, MapperException, PostException, ResponseException, ValidationException {
        InstagramPostsResponse instagramPostsResponse = null;

        ResponseEntity<Instagram_Token> response = dbClient.getInstagramToken();
        Instagram_Token token = response.getBody();

        if(token != null && token.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(token.getMessage());
        }
        else if(token != null && token.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(token.getMessage());
        }
        else if(token != null && Strings.isNotBlank(token.getToken()) && Strings.isNotEmpty(token.getToken())){
            Integer expirationDuration = calculateDaysBetweenDates(token);

            if(expirationDuration>2){
                instagramPostsResponse = getPostsUsingOldToken(token);
            }
            else {
                instagramPostsResponse = processTokensAndReturnPostsResponse(token.getToken(), false);
            }
        }
        return instagramPostsResponse;
    }

    private Integer calculateDaysBetweenDates(Instagram_Token token){
        Date expirationDate = token.getExpirationDate();
        Date currentDate = new Date(System.currentTimeMillis());

        LocalDate expirationLocalDate = expirationDate.toLocalDate();
        LocalDate currentLocalDate = currentDate.toLocalDate();

        Period period = Period.between(currentLocalDate, expirationLocalDate);

        return period.getDays();
    }

    public InstagramPostsResponse getPostsUsingOldToken(Instagram_Token token) throws ResponseException, PostException {
        InstagramPostsResponse response = helper.getPostsUsingOldToken(token);
        validation.validateInstagramPostsGraphAPIResponse(response);
        return response;
    }

    public LongLivedTokenResponse getRefreshedAccessToken(String token) throws ResponseException, ValidationException {
        LongLivedTokenResponse response = helper.getRefreshedAccessToken(token);
        validation.validateAccessToken(response);
        return response;
    }

    public InstagramPostsResponse getPostsUsingRefreshedToken(Instagram_Token token) throws PostException, ResponseException {
        return getPostsUsingOldToken(token);
    }

    public InstagramPostsResponse getPostsUsingLongLivedToken(Instagram_Token token) throws PostException, ResponseException {
        return getPostsUsingOldToken(token);
    }

    public LongLivedTokenResponse getLongLivedAccessToken(String token) throws ValidationException, ResponseException {
        LongLivedTokenResponse response = helper.getLongLivedAccessToken(token);
        validation.validateAccessToken(response);
        return response;
    }

    public InstagramPostsResponse processTokensAndReturnPostsResponse(String token, boolean isLongLivedToken) throws ValidationException, ResponseException, MapperException, DataBaseOperationException, PostException {
        InstagramPostsResponse instagramPostsResponse = null;
        LongLivedTokenResponse response = null;
        if(isLongLivedToken){
            response = getLongLivedAccessToken(token);
        } else {
            response = getRefreshedAccessToken(token);
        }
        Instagram_Token instagramToken = mapper.longLivedAccessTokenToInstagramToken(response);
        ResponseEntity<Instagram_Token> instagramTokenResponse = dbClient.saveInstagramToken(instagramToken);
        Instagram_Token responseBody = instagramTokenResponse.getBody();
        if(responseBody != null && responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody != null && responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        }
        else if(responseBody != null && responseBody.getMessage().equals(StringConstants.REQUEST_PROCESSED)){
            if(isLongLivedToken){
                instagramPostsResponse = getPostsUsingLongLivedToken(responseBody);
            } else {
                instagramPostsResponse = getPostsUsingRefreshedToken(responseBody);
            }
        }
        return instagramPostsResponse;
    }
}
