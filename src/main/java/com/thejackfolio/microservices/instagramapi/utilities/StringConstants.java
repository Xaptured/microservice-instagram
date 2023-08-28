package com.thejackfolio.microservices.instagramapi.utilities;

public class StringConstants {

    // Mapper and Database error messages
    public static final String MAPPING_ERROR = "Error occurred while mapping";
    public static final String DATABASE_ERROR = "Error occurred while database operation";
    public static final String REQUEST_PROCESSED = "Request Processed";
    public static final String VALIDATION_ERROR = "Error occurred while validation";
    public static final String POST_URL_NULL = "Post URL is null in response";
    public static final String BAD_REQUEST_OLD_TOKEN = "Bad request to Instagram Graph API while getting posts using old token";
    public static final String BAD_REQUEST_REFRESH_TOKEN = "Bad request to Instagram Graph API while refreshing access token";
    public static final String BAD_REQUEST_LONGLIVED_TOKEN = "Bad request to Instagram Graph API while getting long-lived access token";
    public static final String GET_POSTS_REFRESH_TOKEN = "Getting posts using refreshed access token";
    public static final String GET_POSTS_LONGLIVED_TOKEN = "Getting posts using long-lived access token";
    public static final String GET_POSTS_MORE_THAN_TWO = "Getting posts using old token as we have {} days more to expire the token";
    public static final String GET_POSTS_LESS_THAN_TWO = "Getting posts using refreshed token as we have less than 2 days to expire thee token";
    public static final String NO_NEED_REFRESH_TOKEN = "No need to refresh the token as we have {} days more to expire the token";
    public static final String SAVE_REFRESH_TOKEN = "while saving refreshed token in DB";
    public static final String SUCCESSFULLY_SAVED_REFRESH_TOKEN = "Refreshed Token Saved Successfully in DB";
    public static final String EXCEPTION_REFRESH_TOKEN = "Exception occurred while getting refreshed token";
    public static final String NO_NEED_TO_REFRESH_TOKEN = "No need to refresh the token as user not authorized the instagram till now";
    public static final String STARTING_SCHEDULER = "Starting refresh token scheduler at {}";

    public static final String FIELDS = "FIELDS";
    public static final String LIMIT = "LIMIT";
    public static final String GRANT_TYPE_REFRESH_ACCESS_TOKEN = "GRANT_TYPE_REFRESH_ACCESS_TOKEN";
    public static final String CLIENT_SECRET = "CLIENT_SECRET";
    public static final String GRANT_TYPE_LONG_LIVE_ACCESS_TOKEN = "GRANT_TYPE_LONG_LIVE_ACCESS_TOKEN";
    public static final String FALLBACK_MESSAGE = "Something went wrong. Please try again later";
    public static final String RETRY_MESSAGE = "Something went wrong. Doing retry...";


    private StringConstants(){}
}
