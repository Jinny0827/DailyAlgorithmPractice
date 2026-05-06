package org.example.Y_2026.May;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 트리의 부모 찾기 (백준)
 *
 * 루트 없는 트리가 주어진다. 이 트리의 루트를 1이라고 정했을 때, 각 노드의 부모를 구하시오.
 *
 * 7 -> 1 6 / 6 3 / 3 5 / 4 1 / 2 4 / 4 7
 */
public class Day15TreesParents {

    public static void main(String[] args) throws IOException {

        // 백준의 경우 Scanner를 많이 사용하나 노드의 갯수가 100,000건 등 일반적인 수식의 범위가 아닌경우가 있기때문에
        // BufferedReader를 사용한다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 노드의 갯수 N
        int N = Integer.parseInt(br.readLine());

        // 인접 리스트 초기화 (노드번호가 1~N이므로 N + 1 크기)
        List<List<Integer>> graph = new ArrayList<>();
        for(int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        // N-1개의 간선 입력
        for(int i = 0; i < N - 1; i++) {
            // 1 3 이런 식으로 들어오는 문자를 쪼개서 정수화
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // 노드 -> 목표 노드, 이웃 노드 -> 목표 노드 저장 (양방향)
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        // 부모 저장 배열 생성
        int[] parents = new int[N + 1];
        // 방문 여부 배열 생성
        boolean[] visited = new boolean[N + 1];

        // 큐 생성, 루트 1번 생성
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        visited[1] = true;

        // BFS
        /**
         * queue: [1]
         * → 1 꺼냄, 이웃 6,4 추가  → parent[6]=1, parent[4]=1
         * queue: [6, 4]
         * → 6 꺼냄, 이웃 3 추가    → parent[3]=6
         * queue: [4, 3]
         * → 4 꺼냄, 이웃 2,7 추가  → parent[2]=4, parent[7]=4
         */
        while(!queue.isEmpty()) {
            
            // 현재 노드 꺼내기
            int current = queue.poll();
            
            // 현재 노드의 이웃 노드들 확인
            for(int neighbor : graph.get(current)) {
                if(!visited[neighbor]) {
                    visited[neighbor] = true;
                    parents[neighbor] = current;
                    queue.add(neighbor);
                }
            }
        }

        // 부모 배열 출력
        // 0은 비워뒀고 1번은 루트
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i <= N; i++) {
            sb.append(parents[i]).append("\n");
        }
        System.out.print(sb);
    }

}
