package org.example.Y_2026.August;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Day65 트리의 지름
 *
 * 트리의 지름이란 트리에서 임의의 두 정점 사이의 거리 중 가장 긴 것을 말한다.
 * 트리의 지름을 구하는 프로그램을 작성하라.
 *
 * 첫 줄: 정점 개수 V (2 ≤ V ≤ 100,000)
 * 다음 V개 줄: 정점 번호 → (연결된 정점, 거리) 쌍이 반복 → 줄 끝은 -1
 *
 *
 * 예시 입력:
 * 5
 * 1 3 2 -1
 * 2 4 4 -1
 * 3 1 2 4 3 -1
 * 4 2 4 3 3 5 6 -1
 * 5 4 6 -1
 */
public class Day65TreeDiameter {

    static boolean[] visited;
    static int[] dist;
    static List<int[]>[] graph;

    public static void main(String[] args) {
        // V가 최대 100,000인데 재귀 DFS를 사용하면 재귀 깊이가 10만이 넘을 가능성이 있다 -> StackOverFlow
        // Thread의 스택 크기를 키워서 실행하는 방법과 스택을 소스적으로 건드리는 방법이 있다.
        new Thread(null, () -> {
            try {
                solve();
            } catch (Exception e) {

            }
        }, "main", 1 << 26).start();
    }

    private static void solve() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        // 정점 번호 1번부터 시작이므로 + 1 처리
        graph = new List[V + 1];
        for(int i = 0; i <= V; i++) {
            graph[i] = new ArrayList<>();
        }

        // 기준 정점 번호 -> {연결 정점 번호 ,가중치}, ...
        for(int i = 0; i < V; i++) {
            st = new StringTokenizer(br.readLine());
            // 기준 정점 번호
            int from = Integer.parseInt(st.nextToken());
            while(st.hasMoreTokens()) {
                // 연결된 정점 번호
                int to = Integer.parseInt(st.nextToken());
                if(to == -1) {
                    break;
                }
                // 가중치
                int weight = Integer.parseInt(st.nextToken());
                graph[from].add(new int[] {to, weight});
                graph[to].add(new int[] {from, weight});
            }
        }

        dist = new int[V + 1];
        visited = new boolean[V + 1];
        // 출발 1, 시작 0부터 재귀
        DFS(1, 0);

        // 가장 먼 정점 거리 값
        int maxDist = 0;
        // 가장 먼 정점 번호
        int farthest = 1;

        // 정점으로 V가 삽입된 순간부터 0이 아닌 1부터 시작하며 반복 순회
        // DFS를 통해 탐색된 가장 먼 거리의 dist 원소를 찾는다.
        for(int i = 1; i <= V; i++) {
            if(dist[i] > maxDist) {
                maxDist = dist[i];
                farthest = i;
            }
        }

        // 가장 먼 정점 거리를 기준으로 재귀 호출
        // 방문 여부 배열과 기준 정점별 가중치를 담는 배열을 초기화 -> 이미 쓰인 값들을 날려야함
        visited = new boolean[V + 1];
        dist = new int[V + 1];
        DFS(farthest, 0);

        // 가장 먼 거리 기준으로 진행했기 때문에 이중에서 최대값이 진짜 값
        int diameter = 0;
        for(int i = 1; i <= V; i++) {
            diameter = Math.max(diameter, dist[i]);
        }

        System.out.println(diameter);
    }

    private static void DFS(int now, int distance) {
        // 현재 정점, 현재정점까지의 가중치(거리) -> 초기값 1, 0
        // 방문 처리
        visited[now] = true;
        dist[now]= distance;

        // 현재 값에 대비한 다음 값 찾기
        for(int[] next : graph[now]) {
            // 다음 노드 순회하며 재귀 DFS (다음 정점, 다음정점까지의 가중치)
            // 다음 노드를 방문했었는지 확인
            if(!visited[next[0]]) {
                DFS(next[0], distance + next[1]);
            }
        }
    }

}
