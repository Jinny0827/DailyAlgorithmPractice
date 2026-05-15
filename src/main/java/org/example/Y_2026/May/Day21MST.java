package org.example.Y_2026.May;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Day21 최소 스패닝 트리
 *
 * 그래프가 주어졌을 때, 최소 스패닝 트리(MST) 의 간선 가중치 합을 구하시오.
 * 최소 스패닝 트리란, 주어진 그래프의 모든 정점을 연결하는 부분 그래프 중 간선의 가중치 합이 최소인 트리이다.
 */
public class Day21MST {

    // MST는 2가지 알고리즘으로 풀 수 있음
    // 크루스칼 / 프림 알고리즘
    // 가중치가 적은 간선부터 골라서 연결하되 사이클 생성 시 버린다.
    // 이 문제의 핵심은 두 정점이 이미 연결되어 있는지(사이클 여부)를 어떻게 빠르게 판별할까?
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 정점 수
        int V = Integer.parseInt(st.nextToken());
        
        // 간선 수 (간선 정보의 갯수)
        int E = Integer.parseInt(st.nextToken());

        // 간선 정보
        int[][] edges = new int[E][3];
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            edges[i][0] = Integer.parseInt(st.nextToken()); // 시작 정점
            edges[i][1] = Integer.parseInt(st.nextToken()); // 끝 정점
            edges[i][2] = Integer.parseInt(st.nextToken()); // 가중치
        }

        // edges[i][2]가 가중치이므로 가중치를 기준으로 오름차순 처리 = 가중치가 적은 간선부터 연결의 준비작업
        Arrays.sort(edges, (a, b) -> a[2] - b[2]);

        // 사이클 판별을 위해 유니온-파인드 구조 필요
        // 각 정점의 대표(부모 정점)를 관리한다. 두 정점의 대표(부모)가 같으면 이미 연결되있으므로 사이클임
        
        // 자기 자신이 부모
        // 정점의 값을 배열에 담아준다.
        int[] parent = new int[V + 1];
        for (int i = 1; i <= V; i++) {
            parent[i] = i;
        }

        long sum = 0;
        for (int[] edge : edges) {
            int a = edge[0]; // 시작 정점
            int b = edge[1]; // 끝 정점
            int w = edge[2]; // 가중치

            if(find(parent, a) != find(parent, b)) {
                // 대표가 다름 -> 사이클이 아님 -> 연결 처리
                union(parent, a, b);
                sum += w;
            }
            // 대표가 같으면 -> 사이클 -> 아무것도 안함
        }


        System.out.println(sum);
    }

    // 대표 찾기 (루트)
    static int find(int[] parent, int x) {
        if(parent[x] == x) return x;
        // 재귀로 루트까지 올라간다.
        return find(parent, parent[x]);
    }
    
    // 두 정점 합치기
    static void union(int[] parent, int a, int b) {
        a = find(parent, a);
        b = find(parent, b);
        if (a != b) {
            parent[b] = a;
        }
    }

}
