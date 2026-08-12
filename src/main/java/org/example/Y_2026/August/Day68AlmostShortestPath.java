package org.example.Y_2026.August;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Day68 거의 최단 경로
 *
 * 방향 그래프에서 시작점 S부터 도착점 D까지의 "거의 최단 경로"의 길이를 구하세요.
 * 거의 최단 경로란, 기존 최단 경로에 사용된 도로(간선)를 하나도 포함하지 않는 경로 중 가장 짧은 것입니다.
 * 최단 경로가 여러 개면 그 경로들에 쓰인 간선을 모두 제외해야 합니다.
 * 입력은 여러 테스트 케이스로 구성되며, N과 M이 모두 0이면 종료합니다.
 * 
 * 제일 빠른길이 아니라 그 다음길인데 제일 빠른길과 경로가 겹치지 않는 도로
 */
public class Day68AlmostShortestPath {

    // 최단 경로 계산값을 뺀 다음 경로에 대한 가중치 구하는 문제
    
    // 시작점(S)에서 모든 정점까지의 최단 거리를 구하고(dist1[]), 도착점(D)에서 모든 정점까지의 최단거리(dist2[])를 구한다.
    // 그 다음 어떤 간선이 S -> D 최단 경로 위에 있는지 판별 (dist1[u] + w + dist2[v] == dist1[D] 성립 시 최단경로 중 하나에 포함)
    // 0 0이 입력되면 종료
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // 입력 하나에 테스트 케이스가 여러 뭉텅이로 들어와서 반복해서 로직 처리
        while(true) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            // 정점수 N, 간선수 M
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            // 종료 조건
            if(N == 0 && M == 0) {
                break;
            }

            st = new StringTokenizer(br.readLine());

            // 시작점 S, 도착점 D
            int S = Integer.parseInt(st.nextToken());
            int D = Integer.parseInt(st.nextToken());

            // 원본 그래프 (graph[u]에 {v, weight} 저장)
            List<int[]>[] graph = new List[N];

            // 역방향 그래프(rgraph[v]에 {u, weight} 저장 — u→v를 뒤집어서)
            List<int[]>[] rgraph = new List[N];

            // 점선 갯수만큼 그래프 공간 생성
            for(int i = 0; i < N; i++) {
                graph[i] = new ArrayList<>();
                rgraph[i] = new ArrayList<>();
            }
            
            // 간선 원본 저장용 배열 (지울 간선 판별 시 전체 간선 목록을 다시 순회)
            // u, v, w 저장
            int[][] edges = new int[M][3];

            // 입력 값 저장
            for(int i =0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                // 출발 정점번호 u, 도착 정점 번호 v, 그 도로의 가중치 w
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                graph[u].add(new int[] {v, w});
                rgraph[v].add(new int[] {u, w});
                edges[i] = new int[] {u, v, w};
            }

            // 정방향 다익스트라 / 역방향 다익스트라
            int[] dist1 = dijkstra(S, graph, N);
            int[] dist2 = dijkstra(D, rgraph, N);

            // S -> D의 최단거리
            int sd = dist1[D];

            // 최단경로 간선이 빠진 그래프에서 시작점(S) 기준으로 다시 최단경로 구해서 담을 그릇
            // -> 최단경로를 피해 D까지 가는 가장 짧은 길
            List<int[]>[] newGraph = new List[N];
            for(int i = 0; i < N; i++ ){
                newGraph[i] = new ArrayList<>();
            }

            for(int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int w = edge[2];

                // 정방향 다익스트라 시작점에서 간선의 시작점까지 가중치
                // + 간선별 가중치
                // + 역방향 다익스트라의 목적지에서 간선의 목적지까지 가중치를 더했을때 최단 경로 가중치와 같다면
                if(dist1[u] + w + dist2[v] == sd) {
                    // 최단 경로에 포함된 간선 -> 제거 (넣지 않음)
                    continue;
                }
                
                // 도착, 가중치 삽입 (살아남은 간선만 추가)
                newGraph[u].add(new int[] {v, w});
            }
            
            // 세 번째 다익스트라 호출 및 결과 저장
            int[] dist3 = dijkstra(S, newGraph, N);

            // 목적지까지
            int answer = dist3[D];
            if(answer == Integer.MAX_VALUE) {

                // 목적지 계산이 안되서 기존에 설정한 무한값이 반환됬으면 -1로 없음 처리
                answer = -1;
            }

            sb.append(answer).append("\n");
        }

        System.out.println(sb);
    }

    private static int[] dijkstra(int start, List<int[]>[] g, int N) {
        
        // 정점 갯수만큼 다익스트라를 거칠 배열
        int[] dist = new int[N];
        // 비교를 위해 초기값으로 무한대 값으로 초기화
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        // 우선순위 큐 돌리면서 dist 갱신
        // 우선순위 큐 -> 거리 기준 오름차순 comparator
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        // 시작점 초기화
        pq.add(new int[] {start, 0});

        while(!pq.isEmpty()) {
            // 큐에서 꺼낸것
            int[] cur = pq.poll();
            // 현재 정점 번호
            int curNode = cur[0];
            // 그 정점까지의 (꺼낸 시점) 거리
            int curDist = cur[1];

            // 현재 거리가 다익스트라를 거친 배열의 정점번호의 거리보다 크면 회피
            if (curDist > dist[curNode]) {
                continue;
            }

            // 현재 정점과 연결된 간선 하나
            for(int[] edge : g[curNode]) {
                // 이웃 정점 번호
                int nextNode = edge[0];
                // 그 간선의 가중치
                int weight = edge[1];
                // 이웃까지의 새 후보 거리
                int newDist = curDist + weight;

                if (newDist < dist[nextNode]) {
                    dist[nextNode] = newDist;
                    pq.add(new int[] {nextNode, newDist});
                }
            }
        }

        // 완성된 dist 배열 반환
        return dist;
    }


}
