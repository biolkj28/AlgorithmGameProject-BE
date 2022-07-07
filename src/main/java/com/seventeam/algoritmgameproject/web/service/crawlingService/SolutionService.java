package com.seventeam.algoritmgameproject.web.service.crawlingService;

import com.seventeam.algoritmgameproject.domain.model.TestCase;

import java.util.*;

/*
 * 문제별 테스트케이스 자동생성 로직
 * */
public interface SolutionService {

    // 1번
    List<TestCase> solution1ByEx();
    List<TestCase> solution1TestCases();

    //2번
    List<TestCase> solution2ByEx();
    List<TestCase> solution2TestCases();

    //3번 핸드폰 번호 가리기
    List<TestCase> solution3ByEx();
    List<TestCase> solution3TestCases();

    //4번 x만큼 간격이 있는 n개의 숫자
    List<TestCase> solution4ByEx();
    List<TestCase> solution4TestCases();

    /*
     * 중 문제
     */
    //5. 부족한 금액 계산하기
    List<TestCase> solution5ByEx();
    List<TestCase> solution5TestCases();

    // 문제 6번 2016년
    List<TestCase> solution6ByEx();
    List<TestCase> solution6TestCases();

    // 문제 7번 문자열 내 p와 y의 개수
    List<TestCase> solution7ByEx();
    List<TestCase> solution7TestCases();


    /*
     * 상 문제
     */
    //문제 8번
    List<TestCase> solution8ByEx();
    List<TestCase> solution8TestCases();

    //문제 9 같은 숫자는 싫어
    List<TestCase> solution9ByEx();
    List<TestCase> solution9TestCases();

    //10번 로또의 최고 순위와 최저 순위
    List<TestCase> solution10ByEx();
    List<TestCase> solution10TestCases();

    List<TestCase> generatedTestCases(int i);
}
