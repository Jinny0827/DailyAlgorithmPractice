package org.example.Y_2026.September;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Day85 단절선 (Tarjan's Algorithm)
 *
 * 그래프의 단절선 찾기
 *
 * 1. 무향 그래프에서 특정 간선을 지웠을 때 그래프가 분리되는지 판별 시 사용
 * 2. DFS로 각 정점의 방문 순서(discover)와 역방향 간선으로 되돌아갈 수 있는 가장 이른 정점(low-link)을 계산해, 
 * 자식 정점의 low 값이 부모의 discover값보다 크면 그 간선이 단절선이다.
 * 3. 시간 복잡도는 DFS 1회로 O(V+E) 버텍스 + 엣지 (점 + 간선)이다.
 *
 *
 * 문제 설명
 * 단절선이란 그 간선을 제거했을 때 그래프가 두 개 이상으로 나뉘게 되는 간선을 말한다. 그래프가 주어졌을 때,
 * 단절선을 모두 찾는 프로그램을 작성하시오.
 *
 * 입출력 예시
 *
 * 입력
 *
 * 7 8
 * 1 4
 * 4 5
 * 5 1
 * 1 6
 * 6 7
 * 2 7
 * 7 3
 * 2 3
 *
 * 출력
 *
 * 2
 * 1 6
 * 6 7
 *
 * 제한 조건
 *
 * 1. 정점 수 V (1 ≤ V ≤ 100,000), 간선 수 E (1 ≤ E ≤ 1,000,000)
 * 2. 정점 번호는 1부터 V까지
 * 3. 그래프는 항상 연결되어 있으며, 중복 간선과 자기 루프는 없음
 * 4. 출력은 단절선 개수 K, 이후 K줄에 "A B"(A < B) 형태로, 각 줄은 A 기준 오름차순(사전순) 정렬
 *
 */
public class Day85Bridge {

    // 인접 리스트 -> 크기는 V + 1 (1-indexed)
    static List<Integer>[] graph;

    // 방문 순서 저장, 초기값 0 (0이면 미방문)
    static int[] discover;
    
    // low-link 값 저장
    static int[] low;

    // discover 순서 부여용 카운터 (전역 int, 방문할때마다 ++)
    static int timer;

    // 단절선으로 판정된 간선 (a,b) 저장, 나중에 정렬해서 출력
    static List<int[]> bridges;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        // 1. graph, discover, low, bridges 초기화
        graph = new List[V + 1];
        for (int i = 1; i <= V; i++) {
            graph[i] = new ArrayList<>();
        }
        discover = new int[V + 1];
        low = new int[V + 1];
        bridges = new ArrayList<>();

        // 2. E번 반복하며 간선 입력받아 graph에 양방향으로 저장
        for(int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph[a].add(b);
            graph[b].add(a);
        }

        // 3. 모든 정점 순회하며 미방문 정점이면 dfs 호출
        for(int i = 1; i <= V; i++) {
            if(discover[i] == 0) {
                dfs(i, 0);
            }
        }

        // 4. bridges 정렬 (a 기준, 같으면 b 기준 오름차순)
        bridges.sort((x, y) -> x[0] != y[0] ? x[0] - y[0] : x[1] - y[1]);

        // 5. 개수와 간선들 출력
        StringBuilder sb = new StringBuilder();
        // 갯수
        sb.append(bridges.size()).append("\n");
        // 간선들
        for(int[] edge : bridges) {
            sb.append(edge[0]).append(" ").append(edge[1]).append("\n");
        }
        // 출력
        System.out.println(sb);
    }

    private static void dfs(int here, int parent) {
        // 1. 현재 정점(here) 방문 처리
        discover[here] = low[here] = ++timer;

        // 2. graph[here]의 이웃들을 하나씩 순회 (next)
        //       2-1. next == parent 면 → 부모로 되돌아가는 간선이니 스킵
        for(int next : graph[here]) {
            // 방금 사용한 간선을 다시 사용 X
            if (next == parent) {
                // 이 이웃만 건너뛰고 반복문 계속
                continue;
            }

            //       2-2. next가 미방문(discover[next] == 0) 이면 → 트리 간선
            if(discover[next] == 0) {
                // dfs(next, here) 재귀 호출  (여기가 "계단 한 칸 더 내려가는" 부분)
                dfs(next, here);

                // 재귀에서 돌아오면: low[here] = min(low[here], low[next])
                low[here] = Math.min(low[here], low[next]);

                // 판정: low[next] > discover[here] 이면 → 단절선! bridges에 (here, next) 추가
                if(low[next] > discover[here]) {
                    // 예로 7, 2 상황이면 작은값과 큰값을 구분하여 순서대로 넣음
                    int a = Math.min(here, next);
                    int b = Math.max(here, next);
                    bridges.add(new int[] {a, b});
                }
            } else {
                //       2-3. next가 이미 방문됨(discover[next] != 0) 이면 → 이게 바로 "사이클/역방향 간선"
                //            - low[here] = min(low[here], discover[next])
                low[here] = Math.min(low[here], discover[next]);
            }
        }
    }

}
