package org.example.Y_2026.June;

import java.util.*;

/**
 * Day37 최소비용 구하기
 *
 * 도시 N개, 버스 M개가 있다.
 *
 * A 도시에서 B 도시까지 가는 최소 비용을 구하라.
 *
 * dist = distance(거리)
 */
public class Day37MinCost {

    public static void main(String[] args) {

        //------------------------------초기화 영역 시작----------------------------------

        Scanner scanner = new Scanner(System.in);

        // 도시
        int N = scanner.nextInt();
        
        // 버스
        int M = scanner.nextInt();

        // 방문하지 않은 도시는(무한값으로) 배열 초기화
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // ("출발 도시", [도착도시, 비용]) 쌍을 리스트화
        List<List<int[]>> graph = new ArrayList<>();
        
        // 1단계 도시수만큼 빈 리스트 공간 생성
        for(int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }
        
        // 2단계 버스 갯수만큼 해당 도시 리스트에 추가
        // 출발 도시 : [도착도시, 비용]
        for(int i =0; i < M; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int c = scanner.nextInt();

            graph.get(a).add(new int[] {b, c});
        }

        //------------------------------초기화 영역 종료----------------------------------

        // graph로 리스트업된 도시별 목적지 도시에 대한 가중치 값을 PriorityQueue에 삽입
        // 삽입 시 비용 순으로 오름차순 처리한다.

        // 최소비용을 구하기 위한 출발 -> 도착 도시 입력값
        int start = scanner.nextInt();
        int end = scanner.nextInt();
        dist[start] = 0;

        // PriorityQueue[도시번호, 비용] 비용 오름차순
        // PriorityQueue = 우선순위 큐
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        // 우선순위 큐에 원소를 추가. 값 추가 실패 시 false를 반환
        pq.offer(new int[] {start, 0});

        while(!pq.isEmpty()) {

            // 정렬된 pq에서 최소 값을 뽑아낸다.
            int[] cur = pq.poll();
            int node = cur[0];
            int cost = cur[1];

            // 무한대로 지정해놓은 dist 배열 값과 cost를 비교해  cost가 큰 값은 회피
            if (cost > dist[node]) continue;

            // 도착 도시를 돌면서 다음 노드와 코스트를 구한다.
            for (int[] next : graph.get(node)) {
                // 현재 도시(node)에 대한 다음 목적 도시에 대한 내용(nextNode)
                int nextNode = next[0];
                /// 기존의 cost(현재 도시까지 오는데 든 비용) + next[1](다음 도시로 가는 버스 비용)
                int nextCost = cost + next[1];

                // 새로 계산한 비용(nextCost)에 기존 dist 배열 저장 비용보다 작다면? -> 더 저렴한 길을 찾은 것
                if(nextCost < dist[nextNode]) {
                    // 최소 비용 배열을 더 작은 값으로 갱신
                    dist[nextNode] = nextCost;

                    // 이 도시를 통해서 또 다른 도시로 가는길을 찾기 위해 PriorityQueue에 넣는다.
                    pq.offer(new int[] {nextNode, nextCost});
                }
            }
        }
        //------------------------------결과 출력 영역----------------------------------
        // 최종적으로 dist 배열의 end 인덱스에 저장된 값이 시작점에서 도착점까지의 최소 비용입니다.
        System.out.println(dist[end]);
    }

}
