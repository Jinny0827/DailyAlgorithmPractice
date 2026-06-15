package org.example.Y_2026.June;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 *  Day33 LCA
 *
 *  N개의 정점으로 이루어진 트리가 주어진다.
 *  두 노드의 쌍 M개가 주어질 때,
 *  각 쌍의 **최소 공통 조상(LCA)**을 구하라.
 *  -> 가장 가까운 공통 조상
 */
public class Day33LCA {

    // 입력 값에 대한 기준
    // 간선: N-1 = 최대 49,999줄
    // 쿼리: M = 최대 10,000줄
    // 총 약 60,000줄
    // Scanner 보단 BufferedReader + StringTokenizer
    // 1. 줄 수가 많음 (6만 줄) → Scanner는 한 줄씩 읽는 속도가 느려서 시간 초과 위험
    // 2. LCA를 단순(naive) 방식으로 풀면 쿼리마다 깊이 차이만큼 부모를 타고 올라가야 함 → 최악의 경우 M × N = 10,000 × 50,000 = 5억 연산

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        // 인접 리스트 작성 및 공간 할당
        // 공간 할당의 이유 = 인덱스로 바로 접근하기 위해서
        List<List<Integer>> graph = new ArrayList<>();
        for(int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 1; i < N; i++ ) {
            // N개의 점선에 대한 간선 받아서 저장
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph.get(a).add(b);
            graph.get(b).add(a);
        }


        // N개 점선에 대한 부모 배열 만들기
        // depth = 깊이 차이만큼 끌어올리기
        int[] parent = new int[N + 1];
        int[] depth = new int[N + 1];
        boolean[] visited = new boolean[N + 1];

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        visited[1] = true;

        // 1부터 시작하여
        // graph에서 점선의 연결정보 꺼내어 (예) 1을 get하면 2)
        // 다음 연결점을 next로 지정하여 부모, 방문 여부, 뎁스, queue에 저장
        while(!queue.isEmpty()) {
            int current = queue.poll();

            for (int next : graph.get(current)) {
                if(!visited[next]) {
                    // 다음 방문점을 위한 조절
                    /**
                     * visited[next] = true → 방문 처리 (재방문 방지)
                     * parent[next] = current → next의 부모는 current
                     * depth[next] = depth[current] + 1 → current보다 1단계 깊음
                     * queue.add(next) → 다음 탐색 대상으로 등록
                     */
                    visited[next] = true;
                    parent[next] = current;
                    depth[next] = depth[current] + 1;
                    queue.add(next);
                }
            }
        }
        
        
        // 점선에 대한 쿼리/질문(M) 해결
        int M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int q = 0; q < M; q++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // LCA 계산
            // a,b 중 깊이 비교 -> b가 더 깊으면 a와 b를 swap 한다
            // a가 더 깊도록 보장해준다.
            if (depth[a] < depth[b]) {
                int temp = a;
                a = b;
                b = temp;
            }

            // a의 뎁스를 b의 뎁스와 같아질때까지 parent를 끌어올려야 한다.
            while (depth[a] > depth[b]) {
                a = parent[a];
            }

            while (a != b) {
                a = parent[a];
                b = parent[b];
            }

            sb.append(a).append("\n");
        }


        System.out.println(sb);
    }



}
