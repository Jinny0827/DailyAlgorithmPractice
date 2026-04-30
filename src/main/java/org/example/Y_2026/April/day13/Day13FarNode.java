package org.example.Y_2026.April.day13;

import java.util.*;

/**
 * Day 12 : 가장 먼 노드
 *
 * n개의 노드가 있는 그래프가 있습니다.
 * 각 노드는 1부터 n까지 번호가 붙어 있습니다.
 *
 * 1번 노드에서 가장 멀리 떨어진 노드의 개수를 구하세요.
 * 가장 멀리 떨어진 노드란 최단경로로 이동했을 때 간선의 개수가 가장 많은 노드를 의미합니다.
 */
public class Day13FarNode {

    public static void main(String[] args) {
        Day13FarNode day13FarNode = new Day13FarNode();

        int[][] testEdge = {{3,6},{4,3},{3,2},{1,3},{1,2},{2,4},{5,2}};

        System.out.println(day13FarNode.solution(6, testEdge));
    }


    public int solution(int n, int[][] edge) {
        
        // 인접 리스트 생성 (가상 그래프 그리기)
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            // 노드의 갯수만큼 리스트 생성
            graph.add(new ArrayList<>());
        }

        for (int[] e : edge) {
            // graph[숫자] = 노드, 들어있는 값은 그 노드에 해당하는 연결된 노드 위치
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        // 각 노드까지의 거리 dist[노드번호] = 1번에서 그 노드까지 간선 몇 개?
        // 1을 더해준 이유 = 노드는 1부터 시작이므로 (0+1)
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);

        // Queue를 사용하는 이유는 BFS는 가까운 노드부터 탐색 시작(반대는 깊이 우선 탐색)
        // 먼저 들어온걸 먼저 뺴는 (FIFO) 성질을 이용해서 먼 노드의 순서는 자동으로 지켜진다.

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        // 시작점의 거리
        dist[1] = 0;

        while(!queue.isEmpty()) {
            // 큐에서 하나 꺼냄 (제일 위의 값)
            int node = queue.poll();
            
            // 꺼낸 노드의 이웃들 순회 (해당 층을 순회)
            for (int next : graph.get(node)) {

                // 아직 방문 안한 노드만 (값이 없는 노드)
                if (dist[next] == -1) {
                    // 거리 = 현재 노드 거리 + 1 (다음 이동 노드이기 때문)
                    dist[next] = dist[node] + 1;
                    queue.add(next);
                }
            }
        }

        // dist 배열에서 가장 큰 값 (제일 뒷 노드)
        int max = 0;
        for(int i = 0; i <= n; i++) {
            max = Math.max(max, dist[i]);
        }

        // 최대 값과 같은 노드 카운트
        //i=1: max=0, dist[1]=0  → 카운트 (answer=1)
        //i=2: max=1, dist[2]=1  → 카운트 (answer=2)
        //i=3: max=1, dist[3]=1  → 카운트 (answer=3)
        //i=4: max=2, dist[4]=2  → 카운트 (answer=4)
        //i=5: max=2, dist[5]=2  → 카운트 (answer=5)
        //i=6: max=2, dist[6]=2  → 카운트 (answer=6)
        // answer = 3
        int answer = 0;
        for(int i = 0; i <= n; i++) {
            if (dist[i] == max) {
                answer++;
            }
        }

        return answer;
    }
}
