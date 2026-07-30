package org.example.Y_2026.July;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Period;
import java.util.*;

/**
 * Day60 트리의 독립집합
 *
 * 트리의 각 정점에 양의 정수 가중치가 주어진다.
 * 두 정점이 서로 인접(간선으로 연결)하지 않도록 정점들을 고른 집합을 독립집합이라 하며, 그 크기는 선택한 정점들의 가중치 합이다.
 *
 * 가중치 합이 최대가 되는 독립집합을 구하고, 그 집합에 속하는 정점 번호를 오름차순으로 출력하라.
 * (여러 정답이 가능하면 그중 하나만 출력)
 *
 */
public class Day60TreeDP {

    static int[] weight;
    static List<Integer>[] adj;
    static int[][] dp;
    static List<Integer>[][] path;
    static int N;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 정점 갯수
        N = Integer.parseInt(st.nextToken());
        // 가중치를 담을 배열
        weight = new int[N + 1];
        // 정점에 대한 가중치 값 받기
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= N; i++) {
            weight[i] = Integer.parseInt(st.nextToken());
        }

        // 간선에 맞는 인접된 정점 관계
        // 리스트를 원소로 갖는 배열 -> 정점과 연결된 이웃 정점들의 리스트를 하나씩 갖는다.
        adj = new ArrayList[N + 1];
        
        // 정점당 연결된 정점의 리스트를 보관하는 정점연결 배열
        for (int i = 1; i <= N; i++) {
           adj[i] = new ArrayList<>();
        }
        
        // 정점에 대한 간선을 받는다. -> 간선은 N - 1개 (점선 총 갯수에서 한개를 뺀 값)
        for(int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            // 쌍으로 앞/뒤 원소 받아준다.
            adj[u].add(v);
            adj[v].add(u);
        }

        // 트리 DP 구현
        // 1. dp, path 배열 선언 및 초기화
        //    - dp[N+1][2] : dp[node][0] = node 제외 가중치 최대합, dp[node][1] = node 포함 가중치 최대합
        //    - path[N+1][2] : 각 상태에서 실제로 어떤 정점들을 선택했는지 저장할 리스트
        dp = new int[N+1][2];
        path = new ArrayList[N+1][2];
        for(int i = 1; i <= N; i++) {
            path[i][0] = new ArrayList<>();
            path[i][1] = new ArrayList<>();
        }

        // 3. 메인에서 dfs(1, 0) 호출 (1번을 루트로, 부모는 0으로 표시)
        dfs(1, 0);

        StringBuilder sb = new StringBuilder();
        List<Integer> answer;

        if (dp[1][0] > dp[1][1]) {
            sb.append(dp[1][0]).append('\n');
            answer = path[1][0];
        } else {
            sb.append(dp[1][1]).append('\n');
            answer = path[1][1];
        }

        Collections.sort(answer);
        for(int v : answer) {
            sb.append(v).append(' ');
        }

        System.out.println(sb.toString().trim());
    }

    // 2. dfs(node, parent) 함수 구현
    private static void dfs(int node, int parent) {
        // dp는 가중치의 합, path는 그 가중치를 합친 정점의 목록
        // 자식 병합 전 초기화 -> 해당 노드의 2차원 배열은 본인을 제외한 가중치 / 본인 포함 가중치로 삽입해준다.
        //    2-1. dp[node][0] = 0, dp[node][1] = weight[node] 로 초기화
        //    2-2. path[node][1]에 자기 자신(node) 추가
        dp[node][0] = 0;
        dp[node][1] = weight[node];
        path[node][1].add(node);

        //    2-3. adj[node]를 순회하면서
        //         - neighbor == parent 면 스킵 (부모 방향으로 안 감)
        //         - dfs(neighbor, node) 재귀 호출로 자식 먼저 계산 (후위순회)
        for (int neighbor : adj[node]) {
            // 부모 방향으로는 안감
            if(neighbor == parent) continue;

            // 자신 먼저 재귀로 계산
            dfs(neighbor, node);

            // 여기서 dp[node][0], dp[node][1] 갱신 (다음 단계)
            // 자식의 계산이 끝나면 부모의 dp에 더해준다.
            
            // node(본인) 제외 => 자식은 뽑든 안뽑든 자유 -> 더 큰 쪽을 선택한다.
            //    2-4. dp[node][0] += max(dp[neighbor][0], dp[neighbor][1])
            //         - 이때 선택된 쪽(0 또는 1)의 path[neighbor]를 path[node][0]에 합쳐줌
            if(dp[neighbor][0] > dp[neighbor][1]) {
                // 순회하는 이웃 노드의 본인 제외 값이 본인 포함 값보다 클때는 본인 제외 값과 경로를 넣어주고
                dp[node][0] += dp[neighbor][0];
                path[node][0].addAll(path[neighbor][0]);
            } else {
                // 순회하는 이웃 노드의 본인 제외 값이 본인 포함 값보다 적으면 본인 포함 값과 경로를 넣어준다.
                dp[node][0] += dp[neighbor][1];
                path[node][0].addAll(path[neighbor][1]);
            }
            
            // node 포함 => 자식은 무조건 제외
            //    2-5. dp[node][1] += dp[neighbor][0]
            //         - path[neighbor][0]를 path[node][1]에 합쳐줌
            dp[node][1] += dp[neighbor][0];
            path[node][1].addAll(path[neighbor][0]);
        }
    }

}
