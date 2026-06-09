package org.example.Y_2026.June;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Day30 구간 합 구하기 (펜윅 트리)
 *
 * N개의 수가 주어졌을 때,
 * 중간에 수의 변경이 여러 번 일어나고,
 * 그 중간에 특정 구간의 합을 구하는 프로그램을 작성하시오.
 *
 * 1 ≤ N ≤ 1,000,000
 * 1 ≤ M, K ≤ 10,000
 * 수의 범위: -2⁶³ ~ 2⁶³-1
 *
 * N의 값이 백만까지 들어오는데 변경과 구간 합 조회가 섞여서 들어올 때
 * 매번 처음부터 더하는건 비효율적이다. O(N)
 *
 */
public class Day30FenwickTree {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 결과 값 담을 객체
        StringBuilder sb = new StringBuilder();


        // N, M, K
        // 수의 개수
        int N = Integer.parseInt(st.nextToken());
        // 변경 횟수
        int M = Integer.parseInt(st.nextToken());
        // 구간 합 조회 횟수
        int K = Integer.parseInt(st.nextToken());


        // N개의 수 받기
        // 원본
        long[] arr = new long[N + 1];
        // 펜윅 트리 (덧셈 보관 트리)
        long[] tree = new long[N + 1];

        // N개의 수 받기
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Long.parseLong(st.nextToken());
            
            // 트리에도 초기화 (수열 입력 받으며 트리에도 초기화처리)
            update(tree, i, arr[i], N);
        }

        // M + K번 쿼리
        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            // a = 변경(1)/조회(합 조회)(2) 플래그
            int a = Integer.parseInt(st.nextToken());
            // b = 변경/조회(합 조회)의 첫 값
            int b = Integer.parseInt(st.nextToken());
            // c = 변경/조회(합 조회)의 마지막 값 (범위의 수)
            int c = Integer.parseInt(st.nextToken());

            if(a == 1) {
                // 변경
                long diff = c - arr[b];
                arr[b] = c;
                update(tree, b, diff, N);

            } else {
                // 조회 로직 (b - c 구간 합)
                sb.append(query(tree, c) - query(tree, b - 1)).append("\n");
            }

        }

        System.out.println(sb);
    }

    /* i & -i => 마지막 1비트 추출 연산 (각 인덱스의 담당 구간 크기를 결정) 01 0100 0100 1000 -> 칸마다 2의 지수 2n */

    // 값 변경 시 트리 갱신
    static void update(long[] tree, int i, long diff, int N) {
        while (i <= N) {
            // 트리 값 결정
            tree[i] += diff;
            // 다음 담당 구간으로 이동
            i += (i & -i);
        }
    }

    // 조회 
    static long query(long[] tree, int i) {
        long sum = 0;
        while (i > 0) {
            // 트리안에 해당 값을 sum에 더해준다.
            sum += tree[i];
            // 이전 구간으로 이동
            i -= (i & -i);
        }

        return sum;
    }


}
