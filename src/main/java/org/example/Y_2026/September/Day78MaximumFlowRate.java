package org.example.Y_2026.September;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Day78 최대 유량 (Network Flow)
 *
 * 개념 브리핑
 * - 언제써요? -> 그래프의 각 간선에 용량 제한이 있을 때, 소스에서 싱크까지 보낼 수 있는 최대 유량 구하는 문제에 사용
 * - 핵심 원리 -> 소스->싱크로 가는 증가 경로를 BFS/DFS로 찾아 그 경로의 최소 용량만큼 유량을 흘리고,
 * 역방향 간선을 만들어 두는 과정을 더 이상 경로가 없을 때까지 반복
 * - 시간 복잡도 -> Edmonds-Karp 기준 O(VE2)
 *
 * 문제 설명 >
 * 도현이는 파이프를 통해 물을 보내려 한다. 각 파이프는 시작점, 끝점, 최대로 보낼 수 있는 유량이 정해져 있다. 
 * 시작점 A에서 끝점 Z로 보낼 수 있는 최대 유량을 구하시오
 *
 * 입출력 예시
     입력:
     5
     A B 3
     B C 3
     C D 5
     D Z 4
     B Z 6

     출력:
     3
 *
 */
public class Day78MaximumFlowRate {

    static int N;
    // 간선별(a 시작점 -> b 도착점 / 대문자,소문자 포함) 유량
    static int[][] capacity;
    // 현재 유량
    static int[][] flow;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // N개의 파이프 연결 경우의 수
        N = Integer.parseInt(st.nextToken());
        
        // 52개인 이유는 대/소문자 26개 씩
        // 용량
        capacity = new int[52][52];
        // 현재 흐르는 유량
        flow = new int[52][52];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            char start = st.nextToken().charAt(0);
            char end = st.nextToken().charAt(0);
            int weight = Integer.parseInt(st.nextToken());

            int from = idx(start);
            int to = idx(end);

            // 두 지점 사이의 여러 파이프가 존재할 수 있으므로
            // A B 조합이 여러개 나올 수 있음
            capacity[from][to] += weight;
        }

        int total = 0;
        int amount = BFS();
        while((amount = BFS()) > 0) {
            total += amount;
        }

        System.out.println(total);
    }
    
    // 알파벳(대,소문자 조건)을 정수형 char 변환 함수
    private static int idx(char c) {
        // 만약 c가 'A' 이상 'Z' 이하라면:
        //        return c - 'A'          // A→0, B→1, ..., Z→25
        //    아니라면 (즉, 'a' ~ 'z'):
        //        return (c - 'a') + 26   // a→26, b→27, ..., z→51

        if(c >= 'A' && c <= 'Z') {
            // 대문자 알파벳 범위
            return c - 'A';
        } else {
            // 소문자 알파벳 범위 (26을 더해서 대문자화)
            return (c - 'a') + 26;
        }
    }

    private static int BFS() {
        //1. parent[52] 배열을 만들고 전부 -1로 초기화 (각 정점이 어디서 왔는지 기록용)
        int[] parent = new int[52];
        Arrays.fill(parent, -1);

        //2. visited[52] 배열을 만들고 전부 false로 초기화
        boolean[] visited = new boolean[52];
        Arrays.fill(visited, false);


        //3. 큐를 만들고 시작점 A(idx('A') = 0)를 큐에 넣고 visited[0] = true 처리
        // 초기값
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(idx('A'));
        visited[0] = true;

        //4. 큐가 빌 때까지 반복:
        while(!queue.isEmpty()) {
            //4-1. 큐에서 하나 꺼내서 now에 저장
            int now = queue.poll();

            //4-2. now가 Z(idx('Z') = 25)면 반복 종료
            if (now == idx('Z')) {
                break;
            }

            //4-3. now에서 갈 수 있는 모든 정점(next, 0~51)에 대해:
            for(int next = 0; next < 52; next++) {

                // 4-3-1. 잔여용량 = capacity[now][next] - flow[now][next]
                // 현재 위치에서 다음 위치까지 가는 유량 - 현재 저장된 유량 (음수 나오면 현재 유량보다 크단 얘기 = 비정상)
                int residual = capacity[now][next] - flow[now][next];

                //4-3-2. 잔여용량이 0보다 크고 아직 방문 안 했다면 → visited[next] = true, parent[next] = now, 큐에 삽입
                if(residual > 0 && !visited[next]) {
                    // 방문 처리
                    visited[next] = true;
                    // 다음 부모 방문 값이 현재 값
                    parent[next] = now;
                    // 큐에 넣는다. 다시 poll해서 사용하고 다음 차례로 이동해야 하므로
                    queue.offer(next);
                }
            }
        }

        //5. 만약 visited[Z]가 false라면 → 경로 없음, 0 리턴하고 종료 (Z까지 못갔으니깐)
        if(!visited[idx('Z')]) {
            return 0;
        }

        //6. Z부터 parent를 따라 A까지 역추적하면서, 경로상 잔여용량 중 최솟값(병목) 찾기 == -1이 아닌 알파벳 확인
        int bottleNeck = Integer.MAX_VALUE;
        int node = idx('Z');

        while(node != idx('A')) {
            int prev = parent[node];
            // 최소 유량 계산
            int residual = capacity[prev][node] - flow[prev][node];
            bottleNeck = Math.min(bottleNeck, residual);
            node = prev;
        }

        //7. 같은 경로를 다시 역추적하면서, 각 간선에 병목만큼 유량 반영:
        node = idx('Z');
        while(node != idx('A')) {
            int prev = parent[node];

            // 7-1. flow[prev][node] += bottleneck
            // 정방향 삽입
            flow[prev][node] += bottleNeck;

            //7-2. flow[node][prev] -= bottleneck (역방향 간선)
            // 역방향 삽입
            flow[node][prev] -= bottleNeck;

            // 역방향이므로 이전방향으로 노드 이동
            node = prev;
        }

        //8. 병목값 리턴
        return bottleNeck;
    }

}
