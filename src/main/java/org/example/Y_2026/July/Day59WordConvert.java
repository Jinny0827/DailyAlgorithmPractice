package org.example.Y_2026.July;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Day59 단어 변환
 *
 * 단어 begin, target과 단어 집합 words가 주어집니다.
 * 한 번에 알파벳 한 개만 바꿀 수 있고,
 * 반드시 words 안에 있는 단어로만 변환할 수 있을 때,
 * begin에서 target까지 최소 몇 단계에 변환할 수 있는지 구하세요. 변환이 불가능하면 0을 반환합니다.
 */
public class Day59WordConvert {

    public static int solution(String begin, String target, String[] words) {
        // 방문한 단어를 저장할 집합체 준비
        Set<String> visited = new HashSet<>();

        // 큐 준비 -> (현재 단어, 변환 횟수) 형태로 초기 값으로 begin과 0을 넣고 출발
        // 큐 사용 이유 -> 선입선출 (add, poll), 불필요하게 인덱스에 자원을 할애하지 않아도됨
        Queue<String> wordQueue = new LinkedList<>();
        Queue<Integer> countQueue = new LinkedList<>();
        wordQueue.add(begin);
        countQueue.add(0);

        
        // 큐가 빌때까지 반복
        while(!wordQueue.isEmpty()) {
            // 1. 큐에서 하나 꺼냄 (현재 단어, 횟수)
            String word = wordQueue.poll();
            int count = countQueue.poll();

            // 2. 현재단어가 target이면 그 횟수를 반환하고 종료
            if(word.equals(target)) {
                return count;
            }

            // 3. words 목록을 순회하며
            for(String w : words) {
                // 1. 아직 방문 안했고 현재 단어 && 2. 알파벳이 정확히 다른 1개만 다른 단어 찾기
                if(!visited.contains(w) && diffByOne(word, w)) {
                    // 3-1. 방문 표시하고, (그 단어, 횟수+1)을 큐에 넣음
                    visited.add(w);
                    wordQueue.add(w);
                    countQueue.add(count + 1);
                }
            }

        }
        
        // 큐가 다 빌때까지 target을 못찾으면 0반환
        return 0;
    }

    private static boolean diffByOne(String a, String b) {
        // 다른 글자 개수를 셀 변수 diffCount를 0으로 초기화
        int diffCount = 0;
        
        // a의 길이만큼 반복하며..
        // 각 인덱스 i에 대해 a의 i번째 글자가 b의 i번째 글자가 다르면 diffCount 증가
        for(int i = 0; i < a.length(); i++) {
            if(a.charAt(i) != b.charAt(i)) {
                diffCount++;
            }
        }

        //반복이 끝난 후 diffCount가 1이면 true 아니면 false
        return diffCount == 1;
    }

}
