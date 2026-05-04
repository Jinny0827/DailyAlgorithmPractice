package org.example.Y_2026.April.day14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 배달
 *
 * N개의 마을이 있고, 각 마을 사이에는 도로가 연결되어 있습니다.
 * 마을 1번에서 음식을 배달하려 할 때, 배달 가능한 거리 K 이하인 마을의 수를 구하세요.
 * 도로는 양방향이며, 두 마을 사이에 여러 도로가 존재할 수 있습니다.
 */
public class Day14Delivery {

    public int solution(int N, int[][] road, int K) {

        // 1번 마을에서 각 마을까지의 최단 거리를 구하고 K로 비교해서 카운트처리
        int count = 0;

        // 1단계 그래프 만들기
        // 각 마을에 연결된 [이웃마을, 거리] 목록
        List<List<int[]>> graph = new ArrayList<>();
        // 마을 갯수만큼 그래프(리스트) 생성
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] r : road) {
            // 예를 들면 [1,2,1], [2,3,3]
            // 배열을 쪼개서 출발,도착,거리 값으로 나눈다.
            // 첫번째 배열값은 출발마을, 도착마을, 거리로 나눠 i번 마을마다 그래프에 삽입한다.
            graph.get(r[0]).add(new int[]{r[1], r[2]});
            graph.get(r[1]).add(new int[]{r[0], r[2]});
        }

        // 2단계 dist 초기화 + PriorityQueue 시작
        // 1번 마을에서 각 마을까지 최단 거리
        // K가 0을 고려하지 않은 숫자이므로 1을 더해준다.
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE); // 가지 않은 마을 (갯수 미정의로 인해 무한대로 임시 정의)
        dist[1] = 0; // 출발점은 0 

        // [마을번호, 기준] 거리 기준 오름차순
        // PriorityQueue의 매개변수는 어떤 값을 먼저 꺼낼지(poll할지)
        // pq 안: [[2,1], [4,2], [3,5]]
        // 꺼내면 → [2,1] 먼저! (거리=1 이 제일 작으니까)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        // 출발점을 미리 넣어야 루프가 시작됨 (루프 안에서 가공된 graph를 넣어줄 예정)
        pq.offer(new int[] {1, 0});

        // 3단계 핵심 루프 (다익스트라)
        while(!pq.isEmpty()) {
            
            // 거리짧은 마을(현재 마을) 꺼내기
            int[] cur = pq.poll();
            int node = cur[0]; 
            int cost = cur[1];

            // 이미 더 짧은 거리로 방문했다면 스킵
            if(cost > dist[node]) { continue; }

            // 현재 마을의 이웃 마을 탐색
            for (int[] next : graph.get(node)) {
                int nextNode = next[0]; // 이웃 마을 번호 (원 형태는 이웃마을번호, 거리)
                int nextCost = cost + next[1]; // 현재 거리 + 이웃 마을 거리
                
                // 더 짧은 경로 발견 시 갱신
                if(nextCost < dist[nextNode]) {
                    dist[nextNode] = nextCost;
                    pq.offer(new int[] {nextNode, nextCost});
                }
            }
        }
        
        for (int i = 1; i <= N; i++) {
            if(dist[i] <= K) {
                count++;
            }
        }

        return count;
    }

}
