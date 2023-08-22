package com.thejackfolio.microservices.instagramapi.services;

import com.thejackfolio.microservices.instagramapi.clients.TheJackFolioDBClient;
import com.thejackfolio.microservices.instagramapi.exceptions.MapperException;
import com.thejackfolio.microservices.instagramapi.exceptions.ResponseException;
import com.thejackfolio.microservices.instagramapi.exceptions.ValidationException;
import com.thejackfolio.microservices.instagramapi.mappers.InstagramTokenMapper;
import com.thejackfolio.microservices.instagramapi.models.Instagram_Token;
import com.thejackfolio.microservices.instagramapi.models.LongLivedTokenResponse;
import com.thejackfolio.microservices.instagramapi.utilities.StringConstants;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SchedulingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulingService.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Autowired
    private TheJackFolioDBClient dbClient;
    @Autowired
    private InstagramService service;
    @Autowired
    private InstagramTokenMapper mapper;

    @Scheduled(cron = "0 15 13 * * *")
    public void executeRefreshToken(){
        LOGGER.info(StringConstants.STARTING_SCHEDULER, FORMATTER.format(LocalDateTime.now()));
        ResponseEntity<Instagram_Token> response = dbClient.getInstagramToken();
        Instagram_Token token = response.getBody();

        if(token != null && token.getMessage().equals(StringConstants.DATABASE_ERROR)){
            LOGGER.info(StringConstants.DATABASE_ERROR);
        }
        else if(token != null && token.getMessage().equals(StringConstants.MAPPING_ERROR)){
            LOGGER.info(StringConstants.MAPPING_ERROR);
        }
        else if(token != null && Strings.isNotBlank(token.getToken()) && Strings.isNotEmpty(token.getToken())){
            Long expirationDuration = service.calculateDaysBetweenDates(token);

            if(expirationDuration>2){
                LOGGER.info(StringConstants.NO_NEED_REFRESH_TOKEN, expirationDuration);
            }
            else {
                try {
                    LongLivedTokenResponse refreshedToken = service.getRefreshedAccessToken(token.getToken());
                    Instagram_Token instagramToken = mapper.longLivedAccessTokenToInstagramToken(refreshedToken);
                    ResponseEntity<Instagram_Token> instagramTokenResponse = dbClient.saveInstagramToken(instagramToken);
                    Instagram_Token responseBody = instagramTokenResponse.getBody();
                    if(responseBody != null && responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
                        LOGGER.info(StringConstants.DATABASE_ERROR + StringConstants.SAVE_REFRESH_TOKEN);
                    }
                    else if(responseBody != null && responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
                        LOGGER.info(StringConstants.MAPPING_ERROR + StringConstants.SAVE_REFRESH_TOKEN);
                    }
                    else if(responseBody != null && responseBody.getMessage().equals(StringConstants.REQUEST_PROCESSED)){
                        LOGGER.info(StringConstants.SUCCESSFULLY_SAVED_REFRESH_TOKEN);
                    }
                } catch (ResponseException | ValidationException | MapperException exception) {
                    LOGGER.error(StringConstants.EXCEPTION_REFRESH_TOKEN, exception);
                }
            }
        }
        else {
            LOGGER.info(StringConstants.NO_NEED_TO_REFRESH_TOKEN);
        }
    }
}
