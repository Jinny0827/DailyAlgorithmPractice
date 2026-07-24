package org.example.Y_2026.July;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Day56 k번째 최단 경로 구하기
 *
 * 방향 그래프에서 1번 정점을 출발점으로 하여
 * 각 정점까지의 "K번째로 빠른 최단경로"의 소요시간을 구하는 문제.
 * 같은 정점에 도달하는 경로가 여러 개 있을 수 있고, 그중 K번째로 짧은 값을 출력한다.
 * 만약 K번째 경로가 존재하지 않으면 -1을 출력한다.
 */
public class Day56KthShortestPath {

    public static void main(String[] args) throws Exception {
        // 정점별 간선 입력줄이 많으니 BufferedReader 사용
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 정점별 간선 저장할 정점리스트 -> 간선,가중치 저장 리스트
        List<List<int[]>> graph = new ArrayList<>();

        // 그래프 내에 최종적인 정점/간선수, ~번째 최단경로를 구할지
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // 정점리스트 정점 만큼 저장 (0을 비우고 1~갯수만큼 -> 정점이 1부터 시작하기때문)
        for(int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        // 간선(점선 -> 점선)/가중치 받기
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            // 점선1, 점선2, 가중치
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 간선 저장
            // 정점별 간선 저장
            graph.get(a).add(new int[] {b, c});
        }


        // PriorityQueue로 정렬 큐 사용 -> 삽입시 선언 규칙에 맞게 정렬
        // [누적거리, 현재정점] 형태로 저장 할 예정 -> 누적거리 기준 오름차순 처리
        PriorityQueue<long[]> pq = new PriorityQueue<>(((o1, o2) -> Long.compare(o1[0], o2[0])));
        // 시작점 초기화 누적거리 : 0, 현재 정점 1 (시작 점)
        pq.add(new long[] {0, 1});

        // pq가 누적거리를 오름차순으로 저장해놓기 때문에 pop과 동시에
        // 각 정점마다 개별적으로 몇번째 도착인지 세면서 진행중에 기록
        // 정점 별 도착 횟수
        int[] cnt = new int[N + 1];
        long[] answer = new long[N + 1];
        // 기본값 -1로 채운다.
        Arrays.fill(answer, -1);

        // 첫 시작은 1 -> ~ 정점
        while(!pq.isEmpty()) {
            // 누적거리가 낮은 순으로 뽑아온다.
            long[] cur = pq.poll();
            // 누적거리
            long dist = cur[0];
            // 현재 정점
            int node = (int) cur[1];

            // 누적된 정점 방문횟수가 K번째보다 클 경우 도달 시 회피 (이미 K번째 도달했음)
            if(cnt[node] >= K) {
                continue;
            }
            
            // 정점 도달 처리 => K번째 미도달 시 -1이 기본 값인 노드에 숫자 추가
            cnt[node]++;

            // 누적 정점 방문횟수가 K번에 도달 시 해당 정점에 누적거리 삽입
            if(cnt[node] == K) {
                answer[node] = dist;
            }


            // 현재 정점에서 간선으로 연결된 다음 정점으로 이동
            for(int[] edge : graph.get(node)) {
                int next = edge[0];
                long weight = edge[1];
                // 다음 정점의 누적 방문횟수가 K보다 작을 경우 검사 큐인 pq에 넣지 않는다.
                if(cnt[next] < K) {
                    pq.add(new long[] {dist + weight, next});
                }
            }
        }

        // 각 정점별 K번째 최단 경로 배열을 출력한다.
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= N; i++) {
            // 한줄띄기 섞어서
            sb.append(answer[i]).append("\n");
        }

        System.out.print(sb);

    }

}
