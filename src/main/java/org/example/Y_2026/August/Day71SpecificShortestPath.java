package org.example.Y_2026.August;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Day71 특별한 최단 경로
 *
 * 개념 브리핑 >>
 * 한 지점에서 모든 지점까지의 최단 거리를 구할때 사용하는 알고리즘
 * 1. 방문하지 않은 정점 중 거리가 가장 짧은 정점을 우선순위 큐로 뽑아 확정
 * 2. 인접 정점의 거리를 갱신(완화)하는 방식으로 동작
 * 3. 시간 복잡도는 우선순위 큐 사용시 O(E log V)
 * -> 이번 문제처럼 "특정 정점을 반드시 거쳐야"하는 조건은 다익스트라를 여러 번 돌려 구간 별 최단거리 조합해 해결
 *
 * 문제 설명 >>
 * 방향성이 없는 가중치 그래프가 주어진다.
 * 세준이는 1번 정점에서 N번 정점으로 최단 거리로 이동하려고 하는데, 반드시 주어진 두 정점 v1, v2를 순서에 상관없이 모두 거쳐서 이동해야 한다.
 * 이때 최단 거리를 구하는 프로그램을 작성하시오. 같은 두 정점 사이에 여러 개의 간선이 존재할 수 있다.
 */
public class Day71SpecificShortestPath {

    static int N;
    static int E;
    
    // graph = 여러 번의 dijkstra 호출이 공유해야 하는건 -> 멤버
    // N <= 800으로 작아서 인접행렬도 가능하지만 간선이 최대 20만개(중복 포함)이라 인접리스트 적합 { 연결된 정점, 가중치 }
    static List<int[]>[] graph;

    public static void main(String[] args) throws Exception {
        // 이 호출에서만 사용하고 버려질 변수 -> 로컬
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 1번부터 N번 정점까지의 최단거리
        N = Integer.parseInt(st.nextToken());
        // 정점 간 관계
        E = Integer.parseInt(st.nextToken());

        graph = new List[N + 1];
        // 정점별 관계 정점에 대한 정보를 담을 리스트 공간 생성
        for(int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        // 정점 관계를 graph 객체에담기
        for(int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            // 정점, 관계정점, 가중치
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 정점간의 관계를 양쪽으로 삽입
            graph[a].add(new int[] {b, c});
            graph[b].add(new int[] {a, c});
        }
        
        // v1, v2 입력받기
        st = new StringTokenizer(br.readLine());
        int v1 = Integer.parseInt(st.nextToken());
        int v2 = Integer.parseInt(st.nextToken());

        // 다익스트라 메서드 설계
        // 다익스트라 3번 호출 -> 1번 정점, v1, v2 각각을 시작점으로 해서 세 번 호출(전체 최단 거리 배열 3개 확보)
        long[] distFrom1 = dijkstra(1);
        long[] distFromV1 = dijkstra(v1);
        long[] distFromV2 = dijkstra(v2);

        // 두 경로 조합 비교 (1 -> v1 -> v2 -> N) || (1-> v2 -> v1-> N) 더 짧은값 계산
        long pathA = distFrom1[v1] + distFromV1[v2] + distFromV2[N];
        long pathB = distFrom1[v2] + distFromV2[v1] + distFromV1[N];

        long answer = Math.min(pathA, pathB);

        // 예외 처리 및 출력 -> 두 경로 다갈 수 없는 경우(INF) -1 출력 / 아니면 최솟 값 출력
        if(answer >= Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(answer);
        }

    }

    private static long[] dijkstra(int start) {
        // 시작 정점 하나를 매개변수로 받고, 그 정점부터 전체 정점까지의 최단 거리 배열(long[])을 리턴하는 형태
        // 1. dist 배열로 배열 공간 정점 갯수만큼 배열 변수 선언
        long[] dist = new long[N + 1];
        
        // 2. dist 배열 INF로 초기화, dist[start] = 0 -> 초기값 무한으로 설정 및 시작값은 0으로 세팅
        for(int i = 1; i <= N; i++ ){
            dist[i] = Integer.MAX_VALUE;
        }

        dist[start] = 0;
        
        // 3. PriorityQueue<long[]> pq -> { 누적거리, 정점 } 오름차순
        // Priority 사용 이유는 가장 짧은 정점을 매번 골라야하는데 배열로 최소값을 찾으면 따로 sort해줄 필요가없다
        // Priority의 정렬은 int로 반환하는데 long 타입으로 넣었으므로 Long 객체의 compare 사용
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));

        // 4. pq에 { 0, start } 넣고 시작
        pq.add(new long[] {0, start});

        // 5. pq가 빌때까지 poll하면서
        while(!pq.isEmpty()) {
            long[] cur = pq.poll();
            
            // 누적 거리
            long curDist = cur[0];
            // 현재 정점 (배열 인덱스로 쓰려면 int 캐스팅)
            int curNode = (int) cur[1]; 

            // 5-1. 이미 확정된 (dist보다 큰) 누적 거리면 Skip
            if(dist[curNode] < curDist) {
                continue;
            }

            // 5-2. graph[현재정점]을 순회하며 완화(relax) 조건 만족 시 dist 갱신 + pq에 추가
            for(int[] edge : graph[curNode]) {
                // 현재 정점에 대한 인접 리스트 순회 { 정점, 가중치 }
                int next = (int) edge[0];
                long weight = edge[1];
                long newDist = curDist + weight;

                // curNode를 거쳐 이웃 정점(next) 이동 시 이웃의 기존 dist[next]보다 짧아지는가
                if (newDist < dist[next]) {
                    // 다음 정점에 대한 정보 갱신
                    dist[next] = newDist;
                    // pq에 새 후보 추가
                    pq.add(new long[]{newDist, next});
                }
            }
        }

        return dist;
    }

}
