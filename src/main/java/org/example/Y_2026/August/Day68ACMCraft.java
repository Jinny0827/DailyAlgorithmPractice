package org.example.Y_2026.August;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Day68 Daily Craft
 *
 * 게임에서 건물을 짓는 순서에는 규칙이 있다.
 * 건물 X를 지어야 건물 Y를 지을 수 있는 관계가 여러 개 주어질 때, 특정 건물 W를 짓기 위해 필요한 최소 시간을 구하라.
 * (선행 건물들은 동시에 지을 수 있으며, 각 건물은 자신의 선행 건물이 모두 완성된 후에만 짓기 시작할 수 있다.)
 *
 * 입력 >>
 * 첫 줄: 테스트케이스 개수 T
 * 각 테스트케이스: 건물 개수 N, 규칙 개수 K
 * 다음 줄: 각 건물을 짓는데 걸리는 시간 D[1..N]
 * 다음 K줄: X Y (건물 X 완성 후 건물 Y 착공 가능)
 * 마지막 줄: 승리에 필요한 건물 번호 W
 * 
 * 입력 >
 * 2 (테스트케이스 개수)
 * 4 4 (건물 개수, 규칙 개수)
 * 10 1 100 10 (건물별 건축 시간)
 * 1 2 (X 완성 후 Y 착공 규칙)
 * 1 3
 * 2 4
 * 3 4
 * 4 (W)
 */
public class Day68ACMCraft {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 테스트 케이스 갯수
        int T = Integer.parseInt(st.nextToken());

        for(int i = 0; i < T; i++) {
            // 첫번째 케이스 건물 개수(N)와 규칙개수(K)
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            // 각 건물을 짓는동안 필요한 시간
            st = new StringTokenizer(br.readLine());
            int[] D = new int[N];
            for(int j = 0; j < N; j++) {
                D[j] = Integer.parseInt(st.nextToken());
            }

            // 건물 X 완성 후 Y 착공가능한 K개
            // 인접리스트(List<Integer>[] graph)와 진입차수 배열(int[] indegree)
            List<Integer>[] graph = new List[N];
            for(int k = 0; k < N; k++) {
                graph[k] = new ArrayList<>();
            }

            // 진입 차수 배열 (Y건물이 착공되기 전에 끝나야 하는 선행 건물(X)의 개수)
            int[] indegree = new int[N];
            // 건물별 건축 시간 계산 결과를 담는 배열
            int[] dp = new int[N];

            for(int z = 0; z < K; z++) {
                st = new StringTokenizer(br.readLine());
                // -1 붙이는 이유 = 0-based로 변환
                int x = Integer.parseInt(st.nextToken()) - 1;
                int y = Integer.parseInt(st.nextToken()) - 1;
                graph[x].add(y);
                indegree[y]++;
            }

            Queue<Integer> queue = new LinkedList<>();
            
            // dp 초기화 + 진입차수 0인 노드 큐에 삽입
            for (int j = 0; j < N; j++) {
                // i번 건물 완성되는 시간 
                dp[j] = D[j];
                if (indegree[j] == 0) {
                    queue.offer(j);
                }
            }

            while(!queue.isEmpty()) {
                int cur = queue.poll();
                for (int next : graph[cur]) {
                    dp[next] = Math.max(dp[next], dp[cur] + D[next]);
                    indegree[next]--;
                    if(indegree[next] == 0) {
                        queue.offer(next);
                    }
                }
            }

            st = new StringTokenizer(br.readLine());
            int W = Integer.parseInt(st.nextToken()) - 1;
            System.out.println(dp[W]);
        }
    }

}
