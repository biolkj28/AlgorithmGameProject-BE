package com.seventeam.algoritmgameproject.service.compilerService.generatedTemplate;

public class QuestionStrToPython {
    public static final String code1 = "def string_middle(str):\n" +
            "    # 함수를 완성하세요\n" +
            "\n" +
            "    return str[(len(str)-1)//2:len(str)//2+1]";
    public static final String code2 = "def strToInt(str):\n" +
            "    result = 0\n" +
            "\n" +
            "    for idx, number in enumerate(str[::-1]):\n" +
            "        if number == '-':\n" +
            "            result *= -1\n" +
            "        else:\n" +
            "            result += int(number) * (10 ** idx)\n" +
            "\n" +
            "    return result";
    public static final String code3 = "def hide_numbers(s):\n" +
            "    return \"*\"*(len(s)-4) + s[-4:]";
    public static final String code4 = "def number_generator(y, n):\n" +
            "    # 함수를 완성하세요\n" +
            "    return [i * x + x for i in range(n)]\n";
    public static final String code5 = "def solution(price, money, count):\n" +
            "    return max(0,price*(count+1)*count//2-money)";
    public static final String code6 = "def getDayName(a,b):\n" +
            "    months = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]\n" +
            "    days = ['FRI', 'SAT', 'SUN', 'MON', 'TUE', 'WED', 'THU']\n" +
            "    return days[(sum(months[:a-1])+b-1)%7]";
    public static final String code7 = "def numPY(s):\n" +
            "    # 함수를 완성하세요\n" +
            "    return s.lower().count('p') == s.lower().count('y')";
    public static final String code8 = "def solution(n):\n" +
            "    tmp = ''\n" +
            "    while n:\n" +
            "        tmp += str(n % 3)\n" +
            "        n = n // 3\n" +
            "\n" +
            "    answer = int(tmp, 3)\n" +
            "    return answer";
    public static final String code9 = "def no_continuous(s):\n" +
            "    a = []\n" +
            "    for i in s:\n" +
            "        if a[-1:] == [i]: continue\n" +
            "        a.append(i)\n" +
            "    return a\nprint(3)\nprint(3)\nprint(3)\nprint(3)\nprint(3)";
}
