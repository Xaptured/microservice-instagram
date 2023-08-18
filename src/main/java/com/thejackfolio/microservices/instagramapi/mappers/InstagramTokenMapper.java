package com.thejackfolio.microservices.instagramapi.mappers;

import com.thejackfolio.microservices.instagramapi.exceptions.MapperException;
import com.thejackfolio.microservices.instagramapi.models.Instagram_Token;
import com.thejackfolio.microservices.instagramapi.models.LongLivedTokenResponse;
import com.thejackfolio.microservices.instagramapi.servicehelpers.IncomingValidation;
import com.thejackfolio.microservices.instagramapi.utilities.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;

@Service
public class InstagramTokenMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramTokenMapper.class);

    public Instagram_Token longLivedAccessTokenToInstagramToken(LongLivedTokenResponse response) throws MapperException {
        Instagram_Token token = null;
        try{
            if(response != null){
                Date currentDate = new Date(System.currentTimeMillis());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.SECOND, response.getExpires_in());
                Date expirationDate = new Date(calendar.getTimeInMillis());

                token = new Instagram_Token();
                token.setToken(response.getAccess_token());
                token.setCreationDate(currentDate);
                token.setExpirationDate(expirationDate);
            }
        } catch (Exception exception){
            LOGGER.info(StringConstants.MAPPING_ERROR);
            throw new MapperException(StringConstants.MAPPING_ERROR, exception);
        }
        return token;
    }
}
