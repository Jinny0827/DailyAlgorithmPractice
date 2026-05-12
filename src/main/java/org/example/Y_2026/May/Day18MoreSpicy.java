package org.example.Y_2026.May;

import java.util.PriorityQueue;

/**
 * Day 18 더 맵게
 *
 * 모든 음식의 스코빌 지수를 K 이상으로 만들고 싶습니다.
 * 스코빌 지수가 가장 낮은 두 음식을 아래 방식으로 섞습니다.
 *
 * 섞은 음식의 스코빌 지수 = 가장 낮은 스코빌 + (두 번째로 낮은 스코빌 × 2)
 */
public class Day18MoreSpicy {

    public int solution(int[] scoville, int K) throws NullPointerException {
        // 최소 값만 확인하면 그 이상값은 K 이상
        // PriorityQueue
        // 넣을 때 → 자동으로 정렬
        // 꺼낼 때 → 항상 최솟값이 나옴
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int s : scoville) {
            pq.offer(s);
        }

        // K보다 최소값이 높아지면 카운트 증가
        int count = 0;

        // 자동 정렬된 pq에서 최소값과 K를 비교
        while (pq.peek() < K) {
            // pq의 원소가 1개 남았는데 K 미만이면 무한루프 발생
            // 이를 방지하기 위해 원소가 1개면 -1을 return
            if (pq.size() < 2) return -1;

            // 최소 값과 최소 값 다음 값을 poll 해온다.
            int first = pq.poll();
            int second = pq.poll();

            // 두번째 값에 X2를 하고 첫값과 더한 후 pq에 넣어준다. -> 자동 배열 최소값 변경
            // 시도 횟수(count) 증가
            // K와 같거나 K보다 크면 while문 미수행 
            pq.offer(first + second * 2);
            count++;
        }


        System.out.println(count);
        return count;
    }
}
