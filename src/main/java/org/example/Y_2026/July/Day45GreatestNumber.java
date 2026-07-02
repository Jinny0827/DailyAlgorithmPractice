package org.example.Y_2026.July;


import java.util.Arrays;

/**
 * Day45 가장 큰 수
 *
 *
 * 0 또는 양의 정수가 주어졌을 때,
 * 이 정수들을 이어 붙여 만들 수 있는 가장 큰 수를 구하라.
 *
 */
public class Day45GreatestNumber {

    public static void main(String[] args) {
        Day45GreatestNumber day45GreatestNumber = new Day45GreatestNumber();
        int[] test = {27, 45, 85};
        System.out.println(day45GreatestNumber.solution(test));
    }

    private String solution(int[] numbers) {
        // 결과 문자 반환 객체
        StringBuilder sb = new StringBuilder();

        // 받아온 int 배열을 string 배열로 변환 처리
        String[] strs = new String[numbers.length];
        for(int i = 0; i < numbers.length; i++) {
            strs[i] =  String.valueOf(numbers[i]);
        }

        // int 배열을 내부에서 Arrays 객체로 compare 처리하여 큰 숫자 순서로 나열
        Arrays.sort(strs, (a, b) -> (b+a).compareTo(a+b));

        // 0,0 을 합쳐서 00으로 만들지 않게 하기 위해 0으로 반환
        if (strs[0].equals("0")) {
            return "0";
        }

        // 나열된 숫자들을 문자열 형식으로 합쳐준다.
        // 예) 85, 45, 27, ... -> 854527...
        for(String s : strs) {
            sb.append(s);
        }

        return sb.toString();
    }


}
