package com.thejackfolio.microservices.instagramapi.servicehelpers;

import com.thejackfolio.microservices.instagramapi.clients.InstagramGraphClient;
import com.thejackfolio.microservices.instagramapi.exceptions.ResponseException;
import com.thejackfolio.microservices.instagramapi.models.InstagramPostsResponse;
import com.thejackfolio.microservices.instagramapi.models.Instagram_Token;
import com.thejackfolio.microservices.instagramapi.models.LongLivedTokenResponse;
import com.thejackfolio.microservices.instagramapi.utilities.PropertiesReader;
import com.thejackfolio.microservices.instagramapi.utilities.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstagramServiceHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramServiceHelper.class);

    @Autowired
    private InstagramGraphClient graphClient;

    public InstagramPostsResponse getPostsUsingOldToken(Instagram_Token token) throws ResponseException {
        InstagramPostsResponse response = null;
        try{
            response = graphClient.getPosts(PropertiesReader.getProperty(StringConstants.FIELDS),
                    token.getToken(), Integer.valueOf(PropertiesReader.getProperty(StringConstants.LIMIT)));
        } catch (Exception exception){
            LOGGER.info("Bad request to Instagram Graph API while getting posts using old token", exception);
            throw new ResponseException("Bad request to Instagram Graph API while getting posts using old token", exception);
        }
        return  response;
    }

    public LongLivedTokenResponse getRefreshedAccessToken(String token) throws ResponseException {
        LongLivedTokenResponse response = null;
        try{
            response = graphClient.getRefreshAccessToken(PropertiesReader.getProperty(StringConstants.GRANT_TYPE_REFRESH_ACCESS_TOKEN), token);
        } catch (Exception exception){
            LOGGER.info("Bad request to Instagram Graph API while refreshing access token", exception);
            throw new ResponseException("Bad request to Instagram Graph API while refreshing access token", exception);
        }
        return response;
    }

    public LongLivedTokenResponse getLongLivedAccessToken(String access_code) throws ResponseException {
        LongLivedTokenResponse response = null;
        try{
            response = graphClient.getLongLivedAccessToken(PropertiesReader.getProperty(StringConstants.GRANT_TYPE_LONG_LIVE_ACCESS_TOKEN),
                    PropertiesReader.getProperty(StringConstants.CLIENT_SECRET), access_code);
        } catch (Exception exception){
            LOGGER.info("Bad request to Instagram Graph API while refreshing access token", exception);
            throw new ResponseException("Bad request to Instagram Graph API while refreshing access token", exception);
        }
        return response;
    }
}
