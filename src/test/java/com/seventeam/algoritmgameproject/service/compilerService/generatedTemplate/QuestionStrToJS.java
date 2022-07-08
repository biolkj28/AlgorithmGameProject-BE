package com.seventeam.algoritmgameproject.service.compilerService.generatedTemplate;

public class QuestionStrToJS {
    public static final String code1 = "function solution(s) {\n" +
            "    return s.substr(Math.ceil(s.length / 2) - 1, s.length % 2 === 0 ? 2 : 1);\n" +
            "}console.log(2)console.log(2)console.log(2)console.log(2)console.log(2)console.log(2)console.log(2)";
    public static final String code2 = "function solution(str){\n" +
            "  return str/1\n" +
            "  }";
    public static final String code3 ="function solution(s) {\n" +
            "  return s.replace(/\\d(?=\\d{4})/g, \"*\");\n" +
            "}\n";
    public static final String code4 ="function solution(x, n) {\n" +
            "    return Array(n).fill(y).map((v, i) => (i + 1) * v)\n" +
            "}\n";
    public static final String code5 = "function solution(price, money, count) {\n" +
            "    const tmp = price * count * (count + 1) / 2 - money;\n" +
            "    return tmp > 0 ? tmp : 0;\n" +
            "}\n";
    public static final String code6 = "function solution(a,b){\n" +
            "  var date = new Date(2016, (a - 1), b);\n" +
            "    return date.toString().slice(0, 3).toUpperCase();\n" +
            "}";

    public static final String code7 = "function solution(s){\n" +
            "  //함수를 완성하세요\n" +
            "    return s.toUpperCase().split(\"P\").length === s.toUpperCase().split(\"Y\").length;\n" +
            "}";
    public static final String code8 = "const solut = (n) => {\n" +
            "    return parseInt([...n.toString(3)].reverse().join(\"\"), 3);\n" +
            "};";
    public static final String code9 = "function solution(arr)\n" +
            "{\n" +
            "    return arr.filter((val,index) => val != arr[index+1]);\n" +
            "}";
}
