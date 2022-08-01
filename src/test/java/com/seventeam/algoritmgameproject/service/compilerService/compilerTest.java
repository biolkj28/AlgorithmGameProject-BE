package com.seventeam.algoritmgameproject.service.compilerService;

import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import org.junit.jupiter.api.Test;

public class compilerTest {

    @Test
    void test() {
        String name = QuestionLevel.NORMAL.name();
        System.out.println(name);
//        try (Context context = Context.create()) {
//
//            // 2 출력
//            Value js = context.eval("js", "2");
//            System.out.println("값:"+js);
//        } catch (Exception e) {
//            System.err.println();
//        }
    }
}

