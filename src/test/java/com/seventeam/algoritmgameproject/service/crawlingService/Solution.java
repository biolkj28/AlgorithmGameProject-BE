package com.seventeam.algoritmgameproject.service.crawlingService;

import lombok.Getter;
import lombok.ToString;

import java.util.*;

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

        solution1Core(ans, true);
    }

    void solution1TestCases() {
        String[] ans = {"abcde", "qwer", "qwrq", "dgsahdafahsdhsd", "dergsgseg", "GSDgagdsg", "sdffdasf", "sdfgeegbj", "adfsasfd", "asdfadf"};
        solution1Core(ans, false);
    }

    void solution1Core(String[] ans, boolean isExample) {
        String type = "String";
        String ansType = "String";
        for (String an : ans) {
            TestCase testCase = new TestCase(an, solution1(an), type, ansType);
            if (isExample) {
                testCase.setExample();
            }
            System.out.println(testCase);
        }

    }

    //2번 문자열을 두 정수로 바꾸기
    public int solution2(String s) {
        int answer;
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
        solution2Core(ans, true);
    }

    void solution2TestCases() {
        String[] ans = {"1234", "-1234", "1231", "-1231", "2432", "-2432", "347", "-347", "52", "-25"};
        solution2Core(ans, false);
    }

    void solution2Core(String[] ans, boolean isExample) {
        String type = "String";
        String ansType = "int";
        for (String an : ans) {
            TestCase testCase = new TestCase(an, String.valueOf(solution2(an)), type, ansType);
            if (isExample) {
                testCase.setExample();
            }
            System.out.println(testCase);
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
            solution3Core(s, true);
        }

    }

    void solution3TestCases() {
        for (int i = 0; i < 8; i++) {
            String randNumber = util.randomPhoneNumber();
            solution3Core(randNumber, false);
        }
    }

    void solution3Core(String phone, boolean isExample) {
        //예시 테스트 케이스
        String type = "String";
        String ansType = "String";
        TestCase testCase = new TestCase(phone, solution3(phone), type, ansType);
        if (isExample) {
            testCase.setExample();
        }
        System.out.println(testCase);

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
            solution4Core(an[0], an[1], true);
        }
    }

    void solution4TestCases() {

        for (int i = 0; i < 7; i++) {
            int x = random.nextInt(10000001);
            int n = random.nextInt(1001) + 1;
            if ((x & 1) == 1) {
                x = x * -1;
            }
            solution4Core(x, n, false);
        }
    }

    void solution4Core(int x, int n, boolean isExample) {
        String type = "int_2";
        String ansType = "long[]";

        TestCase testCase = new TestCase(x + "," + n, Arrays.toString(solution4(x, n)), type, ansType);
        if (isExample) {
            testCase.setExample();
        }
        System.out.println(testCase);
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
        solution5Core(3, 20, 4, true);
    }

    void solution5TestCases() {
        for (int i = 0; i < 10; i++) {
            int price = random.nextInt(2500) + 1;
            int money = random.nextInt(1000000000) + 1;
            int count = random.nextInt(2500) + 1;
            solution5Core(price, money, count, false);
        }
    }

    void solution5Core(int price, int money, int count, boolean isExample) {

        String type = "int_3";
        String ansType = "long";
        String params = price + "," + money + "," + count;

        TestCase testCase = new TestCase(params, String.valueOf(solution5(price, money, count)), type, ansType);
        if (isExample) {
            testCase.setExample();
        }
        System.out.println(testCase);

    }

    // 문제 6번 2016년

    public String solution6(int a, int b) {

        String[] weekOfDays = {"FRI", "SAT", "SUN", "MON", "TUE", "WED", "THU"};
        int[] daysOfMonth = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int idx;

        int totalDay = 0;
        for (int i = 0; i < a - 1; i++) {
            totalDay += daysOfMonth[i];
        }
        totalDay += b - 1; // 일은 1일 부터 시작 함으로
        idx = totalDay % 7;
        return weekOfDays[idx];
    }

    void solution6ByEx() {
        solution6Core(5, 24, true);
    }

    void solution6TestCases() {
        int[] daysOfMonth = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int i = 0; i <10 ; i++) {
            int month = random.nextInt(12) + 1;
            int day = random.nextInt(daysOfMonth[month-1])+1;
            solution6Core(month,day,false);
        }
    }

    void solution6Core(int a, int b, boolean isExample) {
        String type = "int_2";
        String ansType = "String";
        String params = a + "," + b;

        TestCase testCase = new TestCase(params, solution6(a, b), type, ansType);
        if (isExample) {
            testCase.setExample();
        }
        System.out.println(testCase);
    }

    // 문자열 내 p와 y의 개수
    boolean solution7(String s) {
        boolean answer = true;
        int cnt =0;

        char[] arr = s.toLowerCase().toCharArray();
        for(char i: arr){
            if((i-112)==0){
                cnt++;
            }else if((i-121)==0) {
                cnt--;
            }
        }
        if(cnt==0)return answer;
        else return false;
    }
    void solution7ByEx(){
        String [] ans ={"pPoooyY","Pyy"};
        for (String an : ans) {
            solution7Core(an,true);
        }
    }
    void solution7TestCases(){

        for (int i = 0; i <10 ; i++) {
            int len = random.nextInt(50)+1;
            solution7Core(util.randomStr(len),false);
        }
    }
    void solution7Core(String s, boolean isExample){
        String type = "String";
        String ansType = "boolean";
        TestCase testCase = new TestCase(s, String.valueOf(solution7(s)), type, ansType);
        if (isExample) {
            testCase.setExample();
        }
        System.out.println(testCase);
    }

    public int solution8(int n) {
        int answer = 0;
        int num = 1;
        StringBuilder str = new StringBuilder();

        while(0<n){
            str.append(n%3);
            n = n/3;
        }

        String[] triple = str.toString().split("");
        for(int i =triple.length-1; i>-1 ; i--){
            answer+=Integer.parseInt(triple[i])*num;
            num*=3;
        }
        return answer;
    }
    void solution8ByEx(){
        int[] ans = {45, 125};
        for (int an : ans) {
            solution8Core(an,true);
        }
    }
    void solution8TestCases(){
        for (int i = 0; i <10 ; i++) {
            int n = random.nextInt(100000000)+1;
            solution8Core(n,false);
        }
    }
    void solution8Core(int n, boolean isExample){
        String type = "int";
        String ansType = "int";
        TestCase testCase = new TestCase(String.valueOf(n), String.valueOf(solution8(n)), type, ansType);
        if (isExample) {
            testCase.setExample();
        }
        System.out.println(testCase);
    }

    //문제 9 같은 숫자는 싫어
    public int[] solution9(int []arr) {
        ArrayList<Integer> tempList = new ArrayList<Integer>();
        int preNum = 10;
        for(int num : arr) {
            if(preNum != num)
                tempList.add(num);
            preNum = num;
        }
        int[] answer = new int[tempList.size()];
        for(int i=0; i<answer.length; i++) {
            answer[i] = tempList.get(i);
        }
        return answer;
    }
    void solution9ByEx(){
        int[][] ans = {{1,1,3,3,0,1,1},{4,4,4,3,3}};
        for (int[] an : ans) {
            solution9Core(an,true);
        }
    }
    void solution9TestCases(){
        for (int i = 0; i < 10 ; i++) {
            solution9Core(util.randomArr(),false);
        }
    }
    void solution9Core(int[] arr, boolean isExample){
        String type = "int[]";
        String ansType = "int[]";
        TestCase testCase = new TestCase(Arrays.toString(arr), Arrays.toString(solution9(arr)), type, ansType);
        if (isExample) {
            testCase.setExample();
        }
        System.out.println(testCase);
    }

    //10번 로또의 최고 순위와 최저 순위
    public int[] solution10(int[] lottos, int[] win_nums) {

        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        int zeroCount = 0;

        for(int lotto : lottos) {
            if(lotto == 0) {
                zeroCount++;
                continue;
            }
            map.put(lotto, true);
        }


        int sameCount = 0;
        for(int winNum : win_nums) {
            if(map.containsKey(winNum)) sameCount++;
        }

        int maxRank = 7 - (sameCount + zeroCount);
        int minRank = 7 - sameCount;
        if(maxRank > 6) maxRank = 6;
        if(minRank > 6) minRank = 6;

        return new int[] {maxRank, minRank};
    }
    void solution10ByEx(){
        int[][] lottos = {{44, 1, 0, 0, 31, 25},{0, 0, 0, 0, 0, 0},{45, 4, 35, 20, 3, 9}};
        int[][] win_nums = {{31, 10, 45, 1, 6, 19},{38, 19, 20, 40, 15, 25},{20, 9, 3, 45, 4, 35}};
        for (int i = 0; i < lottos.length ; i++) {
            solution10Core(lottos[i],win_nums[i],true);
        }
    }
    void solution10TestCases(){
        for (int i = 0; i < 10; i++) {
            int[][] params = util.randomParamsSol10();
            solution10Core(params[0],params[1],false);
        }
    }
    void solution10Core(int[] lottos, int[] win_nums, boolean isExample){
        String type = "int[]_2";
        String ansType = "int[]";
        TestCase testCase = new TestCase(Arrays.toString(lottos)+"_"+Arrays.toString(win_nums), Arrays.toString(solution10(lottos,win_nums)), type, ansType);
        if (isExample) {
            testCase.setExample();
        }
        System.out.println(testCase);
    }
    @Getter
    @ToString
    static class TestCase {
        String params;
        String answers;
        String type;
        String ansType;
        boolean isExample = false;

        public TestCase(String params, String answers, String type, String ansType) {
            this.params = params;
            this.answers = answers;
            this.type = type;
            this.ansType = ansType;
        }

        public void setExample() {
            this.isExample = true;
        }
    }



}
