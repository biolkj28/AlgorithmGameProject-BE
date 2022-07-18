package com.seventeam.algoritmgameproject.service.compilerService;

import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import com.seventeam.algoritmgameproject.service.compilerService.generatedTemplate.QuestionStrToJS;
import com.seventeam.algoritmgameproject.service.compilerService.generatedTemplate.QuestionStrToPython;
import com.seventeam.algoritmgameproject.service.compilerService.generatedTemplate.QuestionsStr;
import com.seventeam.algoritmgameproject.service.crawlingService.Solution;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class JdoodleServiceTest {
    private String basic_java = "public class Solution {\n" +
            "    public int solution(int i) {\n" +
            "        int answer = i;\n" +
            "        return answer;\n" +
            "    }\n" +
            "}";

    //json 적재 속도
    @Test
    @DisplayName("jdoodle api test")
    // 프로그래머스 느낌으로 하기
    // 각 언어별 메인 파이썬
    void compile() {

        String url = "https://api.jdoodle.com/v1/execute";
        String clientId = "586fb20a7ae6a3c3aae8f290321d36f1"; //Replace with your client ID
        String clientSecret = "e8d153f387750de0adf43b141c4fa0353d6c040a47b8d41a3f3fbc19d836949e"; //Replace with your client Secret
        String script = basic_java;
        String language = "nodejs";
        String versionIndex = "0";

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("User-Agent", "Application");
            headers.add("Content-Type", "application/json");

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
            System.out.println(res != null ? res.toJSONString() : "반환값 없음");


        } catch (HttpClientErrorException e) {
            System.out.println("input이 잘못 됨");

        }
    }

    @Test
    @DisplayName("자바 테스트 케이스 대입 코드 문자열 메서드")
    void lastcheck() {
        String params = "1";

        int i = basic_java.lastIndexOf('}');
        String tmp = "public static void main(String[] args) {\n" +
                "        Solution s = new Solution();\n";
        StringBuffer buffer = new StringBuffer(basic_java).deleteCharAt(i).append(tmp);

        for (int j = 0; j < 10; j++) {
            buffer.append("System.out.println(s.solution(").append(params).append("))\n");
        }
        buffer.append("}}");
        System.out.println(buffer);

    }


    @Test
    @DisplayName("파이썬")
    void lastcheckPy() {
        String params = "1";
        String tmp = "def solution(v):\n" +
                "    answer = 'Hello Python'\n" +
                "    return answer\n";
        StringBuffer buffer = new StringBuffer(tmp);
        int start = buffer.indexOf("f");
        int end = buffer.indexOf("(");
        String substring = buffer.substring(start+1, end).trim();
//        buffer.append("print(solution(");
//        buffer.append(params);
//        buffer.append("))");
        System.out.println(substring);
        //System.out.println(buffer);

    }


    @Test
    @DisplayName("자바스크립트 console.log 제거")
    void lastcheckJS() {
        String code1 = QuestionStrToJS.code1;
        if(code1.contains("console.log")){
            System.out.println("출력문을 작성하지 마세요!");
        }
        int i = code1.lastIndexOf("console.log(");
        StringBuilder str = new StringBuilder(code1);
        int start = str.indexOf("}")+1;
        str.delete(start, str.length());
        System.out.println(str);
    }

    @Test
    @DisplayName("자바 메인 메소드 제거")
    void deleteMain(){

        String code2 = QuestionsStr.code2;
        if(code2.contains("main")){
            System.out.println("출력문을 작성하지 마세요!");
        }
//        int start = code2.lastIndexOf("public static void main(String[] args)");
//        StringBuilder str = new StringBuilder(code2);
//
//        str.delete(start, str.length()-1);
//        System.out.println(str);
    }

    @Test
    @DisplayName("파이썬 print 제거")
    void deletePrint(){
        String code2 = QuestionStrToPython.code9;
        if(code2.contains("print")){
            System.out.println("출력문을 작성하지 마세요!");
        }
//
//        int start = code2.lastIndexOf("return");
//        int end = code2.indexOf("print");
//        StringBuilder str = new StringBuilder(code2);
//
//        str.delete(start, str.length()-1);
//        System.out.println(str);
    }
    @Test
    @DisplayName("암거나")
    void del(){
        String s = QuestionLevel.EASY.toString();
        System.out.println(s);
    }

}
