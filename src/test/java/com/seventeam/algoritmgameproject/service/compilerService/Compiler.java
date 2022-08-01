package com.seventeam.algoritmgameproject.service.compilerService;

import com.seventeam.algoritmgameproject.service.compilerService.generatedTemplate.QuestionsStr;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

public class Compiler {

    @Test
    void test(){
        JSONObject obj = new JSONObject();
        obj.put("codeStr", QuestionsStr.code3);
        System.out.println(obj.toJSONString());

    }
}
