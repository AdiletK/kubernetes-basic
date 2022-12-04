package com.example.postservice.externalService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserService {
    @Value("${user.service.host}")
    private String host;

    private RestTemplate restTemplate;

    public UserService() {
        restTemplate = new RestTemplate();
    }

    public void updateUserPostsAmount(Long authorId, Integer count) {
        String uri = host + "/users/post_count/" + authorId + "?count=" + count;
        restTemplate.put(uri, null);
    }
}
