package com.example.witchblog.security.oauth2;

import lombok.ToString;

import java.util.Arrays;
import java.util.Map;
@ToString
public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        System.out.println(attributes);
        return (String) attributes.get("sub");
    }

    @Override
    public String getFirstName() {
        return Arrays
                .stream(attributes.get("name").toString().split(" "))
                .findFirst()
                .orElse("");
    }

    @Override
    public String getLastName() {
        return Arrays.stream(attributes.get("name").toString().split(" "))
                .skip(1).
                findFirst()
                .orElse("");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

}