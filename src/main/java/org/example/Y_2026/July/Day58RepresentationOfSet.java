package org.example.Y_2026.July;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Day58 집합의 표현
 *
 * n개의 원소(1~n)에 대해 m개의 연산을 처리한다.
 *
 * 1. 0 a b: a가 속한 집합과 b가 속한 집합을 합친다
 * 2. 1 a b: a와 b가 같은 집합에 속해 있는지 확인해 YES/NO 출력
 */
public class Day58RepresentationOfSet {

    static int[] parent;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        // 원소 개수
        int N = Integer.parseInt(st.nextToken());
        // 연산 개수
        int M  = Integer.parseInt(st.nextToken());

        // 부모 저장 배열
        // 1 ~ N개이므로 N + 1
        parent = new int[N + 1];

        // 초기 부모는 자기 자신으로 저장
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int op = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (op == 0) {
                // a가 속한 집합과 b가 속한 집합을 합친다
                union(a, b);
            } else {
                // 같은 집합에 속해 있는지 확인해 YES/NO 출력
                int findA = find(a);
                int findB = find(b);

                if(findA == findB) {
                    sb.append("YES").append("\n");
                } else {
                    sb.append("NO").append("\n");
                }
            }

        }

        System.out.print(sb);
    }

    private static int find(int x) {
        if(parent[x] == x) {
            // 본인이 루트면 본인 반환
            return x;
        } else {
            // 본인이 루트가 아니면 부모의 부모까지 찾아서 반환
            parent[x] = find(parent[x]);
            return parent[x];
        }
    }

    private static void union(int a, int b) {
        // a와 b의 제일 위 루트 노드를 찾아 변수 선언
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {
            // a,b가 루트가 같다면 바로 반환
            return;
        } else {
            // a,b의 루트가 다르다면 a의 루트를 b의 루트로 변경 (반대도 가능)
            parent[rootA] = rootB;
        }
    }

}
