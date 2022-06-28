package com.seventeam.algoritmgameproject;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class JdoodleServiceTest {

    //json 적재 속도
    @Test
    @DisplayName("jdoodle api test")
    // 프로그래머스 느낌으로 하기
    // 각 언어별 메인 파이썬
    void compile(){
        String url = "https://api.jdoodle.com/v1/execute";
        String clientId = "586fb20a7ae6a3c3aae8f290321d36f1"; //Replace with your client ID
        String clientSecret = "e8d153f387750de0adf43b141c4fa0353d6c040a47b8d41a3f3fbc19d836949e"; //Replace with your client Secret
        String script = "public class Test{ public static void main(String[] args){String i=\"helloworld\"; System.out.println(i);   }}";
        String language = "java";
        String versionIndex = "0";

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("User-Agent","Application");
            headers.add("Content-Type","application/json");

            Map<String, String> map = new HashMap<>();
            map.put("clientId", clientId);
            map.put("clientSecret", clientSecret);
            map.put("script", script);
            map.put("language", language);
            map.put("versionIndex", versionIndex);

            JSONObject jsonObject = new JSONObject(map);

            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(jsonObject, headers);
            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class);

            JSONObject res = responseEntity.getBody();
            System.out.println(res!=null?res.toJSONString():"반환값 없음");


        } catch (HttpClientErrorException e) {
            System.out.println("input이 잘못 됨");

        }
    }

}
