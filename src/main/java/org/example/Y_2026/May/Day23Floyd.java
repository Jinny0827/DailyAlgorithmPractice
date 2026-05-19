package org.example.Y_2026.May;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *  Day23 플로이드
 * 
 * n개의 도시와 m개의 버스 노선이 있다.
 * 각 버스는 출발 도시 → 도착 도시, 비용이 주어진다.
 * 모든 도시 쌍 (i, j)에 대해 도시 i에서 도시 j로 가는 최솟값 비용을 구하라.
 * 갈 수 없으면 0을 출력한다.
 */
public class Day23Floyd {

    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        // 도시의 개수
        int n = Integer.parseInt(bf.readLine().trim());

        // 버스 노선의 개수
        int m = Integer.parseInt(bf.readLine().trim());


        StringTokenizer st;

        //도시 i에서 도시 j까지의 최소 비용 담을 배열 [도시][도시] = 거리 비용
        int[][] dist = new int[n + 1][n + 1];
        // int 오버플로우 대비
        int INF = 100_000_000;

        // dist를 INF로 초기화 및 본인은 0 처리
        for (int i = 1; i <= n; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());
            // 도시 -> 도시
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            
            // 비용
            int c = Integer.parseInt(st.nextToken());

            // 여러 비용에 대한 최솟값 적용
            dist[a][b] = Math.min(dist[a][b], c);
        }


        //3중 for문
        //for k (경유 도시)
        //  for i (출발 도시)
        //    for j (도착 도시)
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                // 갈 수 없으면 0 , 갈 수 있으면 dist 값
                if (dist[i][j] == INF) {
                    sb.append(0);
                } else {
                    sb.append(dist[i][j]);
                }

                sb.append(" "); // 공백 처리
            }
            sb.append("\n"); // 한칸 띄우기
        }

        System.out.println(sb);

    }

}
