package org.example.Y_2026.April.day09;

import java.util.*;

/**
 *
 * 알파벳 소문자로 이루어진 N개의 단어가 주어지면 아래 조건에 따라 정렬하세요.
 *
 * 길이가 짧은 단어 먼저
 * 길이가 같으면 사전 순
 *
 */
public class Day09Alphabet {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. 사용자에게 단어 갯수 입력 받기
        int N = scanner.nextInt();
        HashSet<String> M = new HashSet<>();

        // 2. 입력 받은 숫자를 기반으로 단어들 입력 받기
        for(int i = 0; i < N; i++) {
           M.add(scanner.next());
        }

        // sort하기 위해 List로 담아주고 sort로 정렬 처리
        List<String> list = new ArrayList<>(M);
        
        // 길이순서 -> 사전순서로 조건 처리
        Collections.sort(list, (a, b) -> {
            if(a.length() != b.length()) {
                // 길이가 짧은 순
                return a.length() - b.length();
            }

            // 문자열 순서 비교 -> 알파벳순 (길이가 같을 때만 도달)
            return a.compareTo(b);
        });

       for(String word : list) {
           System.out.println(word);
       }
    }
}
