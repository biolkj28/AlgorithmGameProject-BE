package com.seventeam.algoritmgameproject.service.crawlingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCase {

    Solution sol;
    @BeforeEach
    void set(){
         sol = new Solution();
    }
    @Test
    @DisplayName("예시 테스트 케이스")
    public void 예시입력테스트() {

        sol.solution1ByEx();
   
//        sol.solution2ByEx();
//        sol.solution3ByEx();
//        sol.solution4ByEx();
//        sol.solution5ByEx();
//        sol.solution6ByEx();
//        sol.solution7ByEx();
//        sol.solution9ByEx();
//        sol.solution10ByEx();

    }

    @Test
    @DisplayName("랜덤 테스트 케이스")
    public void 랜덤입력테스트() {

        sol.solution1TestCases();
        sol.solution2TestCases();
        sol.solution3TestCases();
        sol.solution4TestCases();
        sol.solution5TestCases();
        sol.solution6TestCases();
        sol.solution7TestCases();
        sol.solution9TestCases();
        sol.solution10TestCases();

    }

    @Test
    @DisplayName("랜덤 유틸리티 테스트")
    public void 테스트1() {
        TestCaseUtil util = new TestCaseUtil();
        System.out.println(util.randomStr(10));
        System.out.println(util.randomNumAndStr(10));

    }

}
