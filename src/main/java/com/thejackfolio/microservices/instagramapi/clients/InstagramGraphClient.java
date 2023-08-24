package com.thejackfolio.microservices.instagramapi.clients;

import com.thejackfolio.microservices.instagramapi.models.InstagramPostsResponse;
import com.thejackfolio.microservices.instagramapi.models.LongLivedTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "GRAPH-SERVICE", url = "https://graph.instagram.com")
public interface InstagramGraphClient {

    @GetMapping("/me/media")
    public InstagramPostsResponse getPosts(@RequestParam(value = "fields") String fields,
                                           @RequestParam(value = "access_token") String access_token,
                                           @RequestParam(value = "limit") Integer limit);

    @GetMapping("/refresh_access_token")
    public LongLivedTokenResponse getRefreshAccessToken(@RequestParam(value = "grant_type") String grant_type,
                                                        @RequestParam(value = "access_token") String access_token);

    @GetMapping("/access_token")
    public LongLivedTokenResponse getLongLivedAccessToken(@RequestParam(value = "grant_type") String grant_type,
                                                          @RequestParam(value = "client_secret") String client_secret,
                                                          @RequestParam(value = "access_token") String access_token);
}
