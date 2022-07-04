package com.seventeam.algoritmgameproject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCase {

    @Test
    @DisplayName("예시 테스트 케이스")
    public void 예시입력테스트() {
        Solution sol = new Solution();
        sol.solution1ByEx();
        sol.solution2ByEx();
        sol.solution3ByEx();
        sol.solution4ByEx();
        sol.solution5ByEx();
    }

    @Test
    @DisplayName("랜덤 테스트 케이스")
    public void 랜덤입력테스트() {
        Solution sol = new Solution();
        sol.solution1();
        sol.solution2();
        sol.solution3();
        sol.solution4();
        sol.solution5();
    }

    @Test
    @DisplayName("랜덤 유틸리티 테스트")
    public void 테스트1() {
        TestCaseUtil util = new TestCaseUtil();
        System.out.println(util.randomStr(10));
        System.out.println(util.randomNumAndStr(10));

    }
}
