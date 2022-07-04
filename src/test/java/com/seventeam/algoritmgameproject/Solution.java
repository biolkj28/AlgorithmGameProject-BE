package com.seventeam.algoritmgameproject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Random;

public class Solution {
    TestCaseUtil util = new TestCaseUtil();
    Random random = new Random();

    /*
     * 하 문제
     */
    //1번 가운데 글자 가져오기
    public String solution1(String s) {
        int len = s.length();
        int idx = len / 2;
        return ((len & 1) == 1) ? String.valueOf(s.charAt(idx)) : s.substring(idx - 1, idx + 1);
    }

    void solution1ByEx() {
        String[] ans = {"abcde", "qwer"};
        solution1Core(ans);
    }

    void solution1() {
        String[] ans = {"abcde", "qwer", "qwrq", "dgsahdafahsdhsd", "dergsgseg", "GSDgagdsg", "sdffdasf", "sdfgeegbj", "adfsasfd", "asdfadf"};
        solution1Core(ans);
    }

    void solution1Core(String[] ans) {
        String type = "String";
        String ansType = "String";
        for (int i = 0; i < ans.length; i++) {
            TestCase testCase = new TestCase(ans[i],solution1(ans[i]),type,ansType);
            System.out.println(testCase.toString());
        }

    }

    //2번 문자열을 두 정수로 바꾸기
    public int solution2(String s) {
        int answer = 0;
        int result = 0;
        boolean sign = true;

        for (char i : s.toCharArray()) {
            if (i == '-') {
                sign = false;
            } else if (i != '+') {
                result = result * 10 + (i - '0');
            }
        }
        answer = (sign ? 1 : -1) * result;
        return answer;
    }

    void solution2ByEx() {
        String[] ans = {"1234"};
        solution2Core(ans);
    }

    void solution2() {
        String[] ans = {"1234", "-1234", "1231", "-1231", "2432", "-2432", "347", "-347", "52", "-25"};
        solution2Core(ans);
    }

    void solution2Core(String[] ans) {
        String type = "String";
        String ansType = "int";
        for (int i = 0; i < ans.length; i++) {
            TestCase testCase = new TestCase(ans[i],String.valueOf(solution2(ans[i])),type,ansType);
            System.out.println(testCase.toString());
        }
    }

    //3번 핸드폰 번호 가리기
    public String solution3(String phone_number) {
        char[] ch = phone_number.toCharArray();
        for (int i = 0; i < ch.length - 4; i++) {
            ch[i] = '*';
        }
        return String.valueOf(ch);
    }

    void solution3ByEx() {
        String[] input = {"01033334444", "027778888"};
        for (String s : input) {
            solution3Core(s);
        }

    }

    void solution3() {
        for (int i = 0; i < 8; i++) {
            String randNumber = util.randomPhoneNumber();
            solution3Core(randNumber);
        }
    }

    void solution3Core(String phone) {
        //예시 테스트 케이스
        String type = "String";
        String ansType = "String";
        TestCase testCase = new TestCase(phone,solution3(phone),type,ansType);
        System.out.println(testCase.toString());

    }

    //x만큼 간격이 있는 n개의 숫자
    public long[] solution4(int x, int n) {
        long[] answer = new long[n];
        int multiple = 1;
        for (int i = 0; i < n; i++) {
            answer[i] = (long) x * multiple;
            multiple++;
        }
        return answer;
    }

    void solution4ByEx() {
        int[][] ans = {{2, 5}, {4, 3}, {-4, 2}};
        for (int[] an : ans) {
            solution4Core(an[0],an[1]);
        }
    }

    void solution4() {

        for (int i = 0; i < 7; i++) {
            int x = random.nextInt(10000001);
            int n = random.nextInt(1001) + 1;
            if ((x & 1) == 1) {
                x = x * -1;
            }
            solution4Core(x, n);
        }
    }

    void solution4Core(int x, int n) {
        StringBuffer params = new StringBuffer();
        StringBuffer answers = new StringBuffer();
        String type = "int2";
        String ansType = "long[]";

        params.append(x).append(",").append(n);
        answers.append(Arrays.toString(solution4(x, n)));
        TestCase testCase = new TestCase(params.toString(), answers.toString(), type, ansType);
        System.out.println(testCase.toString());
    }

    /*
     * 중 문제
     */
    //5. 부족한 금액 계산하기
    public long solution5(int price, int money, int count) {
        long answer = money;

        for (int i = 1; i <= count; i++) {
            answer -= ((long) price * i);
        }
        return answer < 0 ? (answer * -1) : 0;
    }

    void solution5ByEx() {
        solution5Core(3,20,4);
    }

    void solution5() {
        for (int i = 0; i < 10; i++) {
            int price = random.nextInt(2500) + 1;
            int money = random.nextInt(1000000000) + 1;
            int count = random.nextInt(2500) + 1;
            solution5Core(price,money,count);
        }
    }
    void solution5Core(int price, int money, int count){
        StringBuffer params = new StringBuffer();
        StringBuffer answers = new StringBuffer();
        String type = "int3";
        String ansType = "long";
        params.append(price).append(",").append(money).append(",").append(count);
        answers.append(solution5(price, money, count));

        TestCase testCase = new TestCase(params.toString(),answers.toString(),type,ansType);
        System.out.println(testCase.toString());

    }
    @Getter
    @Setter
    @ToString
    static class TestCase {
        String params;
        String answers;
        String type;
        String ansType;

        public TestCase(String params, String answers, String type, String ansType) {
            this.params = params;
            this.answers = answers;
            this.type = type;
            this.ansType = ansType;
        }
    }
}
