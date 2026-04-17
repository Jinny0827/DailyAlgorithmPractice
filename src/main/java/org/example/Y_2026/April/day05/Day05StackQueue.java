package org.example.Y_2026.April.day05;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 스택/큐
 *
 * 일반 프린터는 순서대로 출력하지만, 이 프린터는 중요도가 높은 문서를 먼저 출력합니다.
 *
 * 1. 대기 목록(priorities)에서 첫 번째 문서를 꺼낸다
 * 2. 대기 목록에서 자신보다 중요도가 높은 문서가 있으면 → 맨 뒤로 보낸다
 * 3. 없으면 → 바로 인쇄한다
 * 4. 대기 목록에서 location 번째 문서가 몇 번째로 인쇄되는지 구하세요.
 *
 * 제한 조건
 * 1. 대기 목록 크기: 1 ~ 100
 * 2. 중요도: 1 ~ 9
 * 3. location: 0 ~ (대기 목록 크기 - 1)
 */
public class Day05StackQueue {
    // 누어있는 원통 (First In First Out)
    public int solution(int[] priorities, int location) {
        int answer = 0;

        // 큐 사용
        Queue<int[]> queue = new LinkedList<>();

        for(int i = 0; i < priorities.length; i++) {
            queue.add(new int[]{priorities[i], i});
        }

        while(!queue.isEmpty()) {
            int[] front = queue.poll();
            int max = front[0];
            for(int[] item : queue) {
                // 첫값으로 지정된 max 수치를 queue의 값들을 배열로 치환시켜 비교함
                // 제일 큰 값을 max에 넣음
                max = Math.max(max, item[0]);
            }

            if(front[0] < max) {
                // max 값이 더 크면 작은 값을 뒤로 보낸다.
                // poll로 뺏으니 다시 집어넣으면 제일 뒤로감(add)
                queue.add(front);
            } else {
                // 아니면 인쇄
                answer++;
                if(front[1] == location) {
                    return answer;
                }
            }
        }

        return answer;
    }


}
