package com.thejackfolio.microservices.instagramapi.clients;

import com.thejackfolio.microservices.instagramapi.models.Instagram_Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "DATABASE-SERVICE")
public interface TheJackFolioDBClient {

    @PostMapping("/instagram-token/save-token")
    public ResponseEntity<Instagram_Token> saveInstagramToken(@RequestBody Instagram_Token instagramToken);

    @GetMapping("/instagram-token/get-token")
    public ResponseEntity<Instagram_Token> getInstagramToken();
}
