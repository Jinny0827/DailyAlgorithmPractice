package org.example.Y_2026.June;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Day32 MST (최소 스패닝 트리)
 *
 * 최소 스패닝 트리(MST)는 주어진 그래프의 모든 정점을 연결하는 부분 그래프 중, 간선 가중치의 합이 최소인 트리
 *
 * 그래프의 정점 수 V와 간선 수 E,
 * 각 간선의 정보 (A B C: A와 B를 연결하며 가중치는 C)가 주어질 때,
 * MST의 간선 가중치 합을 구하시오.
 *
 *
 */
public class Day32MSTGraph {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 정점 수 V
        int V = Integer.parseInt(st.nextToken());

        // 간선 수 E
        int E = Integer.parseInt(st.nextToken());

        // 크루스칼 = 간선 리스트 ([간선 당][출발, 도착, 가중치])
        int[][] edges = new int[E][3];

        for(int i = 0; i < E; i++) {
            // 간선 별 크루스칼 리스트 작성
            st = new StringTokenizer(br.readLine());
            edges[i][0] = Integer.parseInt(st.nextToken());
            edges[i][1] = Integer.parseInt(st.nextToken());
            edges[i][2] = Integer.parseInt(st.nextToken());
        }

        // 가중치 오름차순
        Arrays.sort(edges, (a, b) -> a[2] - b[2]);


        // 유니온-파운드
        // 크루스칼 알고리즘(최소 신장 트리) 등에서 사이클 발생 여부를 판단할 때 사용
        // 간선 별 중복되는 간선 연결 찾기 (예) 1->2, 2->3, 1->3 (사이클))
        
        // parent 초기화 (점선 자체를 루트화)
        int[] parent = new int[V + 1];
        for (int i = 1; i <= V; i++) {
            parent[i] = i;
        }

        int sum = 0;
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            int w = edge[2];

            if(find(parent, a) != find(parent, b)) {
                // 루트가 다르면 사이클이 아니므로 연결 처리
                union(parent, a, b);
                sum += w;
            }

            // 루트가 같으면 사이클이므로 넘어간다.
            // 예) 1,1,3

        }

        System.out.println(sum);
    }

    // x의 루트(대표) 찾기, 같은 루트면 이미 연결된 것 = 사이클
    static int find(int[] parent, int x) {
        // x번째 값이 x == x 의 경우
        if(parent[x] == x) {
            return x;
        }

        // 재귀로 루트까지
        return find(parent, parent[x]);
    }

    // 두 정점을 같은 집합으로 합치기
    static void union(int[] parent, int a, int b) {
        a = find(parent, a);
        b = find(parent, b);

        if(a != b) {
            parent[b] = a;
        }
    }




}
