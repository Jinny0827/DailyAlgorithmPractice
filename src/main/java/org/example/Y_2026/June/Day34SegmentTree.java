package org.example.Y_2026.June;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Day34 구간 합 구하기 (Segment Tree)
 *
 * N개의 수가 주어졌을 때, 중간에 수의 변경이 여러 번 일어나고,
 * 그 중간에 특정 구간의 합을 구하는 프로그램을 작성하시오.
 *
 * 배열 값들을 쉽게 더하기 위한 트리 형태로 표현
 * */
public class Day34SegmentTree {

    static int N;
    static long[] arr;
    static long[] tree;


    public static void main(String[] args) throws IOException {
        /**
         * 첫 배열은 수  갯수, 변경 횟수, 합 조회 횟수
         * 두번째 배열은 수의 갯수만큼 수열 받기
         * 세번째 배열부터는 첫 값이 위치변경/합조회 플래그, 두번째 값/세번째 값이 변경/조회의 기준 값들
         */


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 첫 배열은 수의 갯수, 변경 횟수, 구간 합 조회 횟수를 입력받는다.
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // 원본 배열 만들기
        arr = new long[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            // 수 갯수만큼 수열 입력받기
            arr[i] = Long.parseLong(st.nextToken());
        }

        // 트리 배열 만들기
        // 세그먼트 트리를 배열로 표현할 때 최악의 경우 노드 수가 4*N개 필요
        tree = new long[N * 4];
        build(1, 1, N);

        // 출력용 구간 합 구하기
        StringBuilder sb = new StringBuilder();
        
        // 쿼리 횟수만큼 반복하여 출력
        for(int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            // 플래그 값
            int a = Integer.parseInt(st.nextToken());
            // 시작 값
            long b = Long.parseLong(st.nextToken());
            // 끝 값
            long c = Long.parseLong(st.nextToken());

            if(a == 1) {
                // 값 변경 로직
                long diff = c - arr[(int) b];
                arr[(int) b] = c;
                update(1, 1, N, (int) b, diff);
            } else {
                // 조회 : b~c 구간 합
                sb.append(query(1, 1, N, (int)b, (int)c)).append("\n");
            }
        }

        System.out.println(sb);

    }
    // 함수 들어가기 전 요소들 설명
    // mid = 구간을 절반으로 쪼개는 기준점
    // node * 2, node * 2 + 1은 트리 인덱스(값 X) 왼/오른쪽 자식(위에서 아래로)
    // 함수의 매개변수들은 전부 인덱스입니다.
    // node는 간선이라고 생각하면 됩니다.


    // 트리 배열 만드는 함수
    static void build(int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
            return;
        }

        int mid = (start + end) / 2;
        // 값 접근은 위에서 아래로
        // 값 변경을 위한 접근은 아래에서 위로
        build(node * 2, start, mid);
        build(node * 2 + 1, mid + 1, end);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    // 값 변경에 대한 함수
    static void update(int node, int start, int end, int idx, long diff) {
        if(idx < start || idx > end) {
            // 인덱스 범위(시작값보다 작거나 끝값보다 크면 -> 오류 상황) 조건
            return;
        }

        tree[node] += diff;

        if(start == end) {
            // 재귀 하다가 시작값과 끝값이 같아지면 return
            return;
        }

        int mid = (start + end) / 2;
        // 값 접근은 위에서 아래로
        // 현 노드에서 위 노드의 좌측/우측에 대한 재귀
        update(node * 2, start, mid, idx, diff);
        update(node * 2 + 1, mid + 1, end, idx, diff);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    // 구간 합 조회 함수
    static long query(int node, int start, int end, int left, int right) {
        if (right < start || end < left) {
            // 현재 구간이 조회 구간 밖
            return 0;
        }

        if(left <= start && end <= right) {
            // 현재 구간이 조회 구간 안에 완전히 포함
            return tree[node];
        }

        // 애매하게 걸치면 → 왼쪽/오른쪽 재귀 후 합산
        int mid = (start + end) / 2;
        return query(node * 2, start, mid, left, right) + query(node * 2 + 1, mid + 1, end, left, right);

        // 왜 tree[node * 2], tree[tree * 2 + 1] 처럼 상위 좌우노드를 바로 접근하지않는가?
        // 걸쳐있는 구간을 잘게 쪼개야 정확한 범위 합산
        // [2~4] 조회 시 [1~3] 노드는 완전 포함이 아니라 걸쳐있음 → 더 내려가서 [2~3]만 가져와야 함

    }

}
