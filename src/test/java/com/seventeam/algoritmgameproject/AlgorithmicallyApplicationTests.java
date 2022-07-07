package com.seventeam.algoritmgameproject;

import com.seventeam.algoritmgameproject.web.service.compilerService.generatedTemplate.GeneratedTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class AlgorithmicallyApplicationTests {

    @Autowired
    Map<String, GeneratedTemplate> map;
    @Test
    void contextLoads() {
        for (String s : map.keySet()) {
            System.out.println(s);
        }
    }

}
