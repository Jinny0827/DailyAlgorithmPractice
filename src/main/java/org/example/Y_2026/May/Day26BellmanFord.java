package org.example.Y_2026.May;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Day26 Bellman-Ford
 *
 * 타임머신
 * N개의 도시와 M개의 버스 노선이 있다. 버스 비용은 음수일 수 있다.
 * 1번 도시에서 출발해 나머지 모든 도시로 가는 최단 시간을 구하라.
 *
 *
 * 최단거리 문제에서 거리비용이 -로 들어와서 음수가되면 다익스트라를 사용할 수 없다.
 *
 */
public class Day26BellmanFord {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 도시의 갯수
        int N = Integer.parseInt(st.nextToken());
        
        // 버스 노선의 갯수
        int M = Integer.parseInt(st.nextToken());


        // 노선 수만큼 저장
        // 노선 번호라고 보면 된다.
        int[][] edges = new int[M][3];

        for (int i = 0;  i < M; i++) {
            // 마을에 대한 버스노선 및 거리스택
            st = new StringTokenizer(br.readLine());

            //a
            edges[i][0] = Integer.parseInt(st.nextToken());
            // b
            edges[i][1] = Integer.parseInt(st.nextToken());
            // c
            edges[i][2] = Integer.parseInt(st.nextToken());

        }

        // dist = 1번 도시에서 i번 도시까지 현재까지 알고 있는 최단 비용
        long[] dist = new long[N + 1];
        
        // 초기값으로 무한대 초기화
        Arrays.fill(dist, Long.MAX_VALUE);

        // 출발점만 0으로 초기화
        dist[1] = 0;
        
        // long 사용 이유는 int 오버플로우
        // N - 1을 사용하는 이유는 마을 대비 간선수는 1개 적다.  A -> B -> C 의 경우 간선이 2개
        for(int i = 0; i < N - 1; i++) {
            for (int[] edge : edges) {
                int a = edge[0];
                int b = edge[1];
                int c = edge[2];

                // 중요 >>>> 출발이 1번이 아닌 노선은 첫 루프에서 전부 막힘 <<<
                // dist는 초기값으로 1번이 0으로 초기화 / 나머지는 무한 대 값 초기화
                if (dist[a] != Long.MAX_VALUE) {
                    dist[b] = Math.min(dist[b], dist[a] + c);
                }
            }
        }

        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            int c = edge[2];

            if(dist[a] != Long.MAX_VALUE) {
                if(dist[a] + c < dist[b]) {
                    // 계속 갱신됨 -> 음수 사이클 존재
                    System.out.println(-1);
                    return;
                }
            }
        }


        StringBuilder sb = new StringBuilder();
        for (int i = 2; i <= N; i++) {
            if (dist[i] == Long.MAX_VALUE) {
                sb.append(-1);  // 도달 불가
            } else {
                sb.append(dist[i]);
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }


}
