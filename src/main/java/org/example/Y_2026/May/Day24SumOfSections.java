package org.example.Y_2026.May;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Day24 구간 합 구하기
 *
 * N개의 수가 주어졌을 때,
 * 중간에 수의 변경이 여러 번 일어나고,
 * 그 중간에 특정 구간의 합을 구하는 프로그램을 작성하라.
 *
 * 수열의 1은 변경 2는 합
 */
public class Day24SumOfSections {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 결과값 담기 위한 객체
        StringBuilder sb = new StringBuilder();
        // 첫 줄 쪼개기 수의 갯수 / 변경 횟수 / 구간 합 요청 횟수
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // 원본 (값 변경 추적용)
        // 0 부터 시작 이므로 + 1
        // long인 이유는 제한 조건이 -2^63 ~ 2^63-1 (int 범위 초과)
        long[] arr = new long[N + 1];
        
        // 입력된 숫자 배열 받아 원본에 저장
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        // 트리 (구간 합 저장용)
        // 입력된 숫자 배열을 트리에 저장
        long[] tree = new long[N + 1];
        for (int i= 1; i <= N; i++) {
            update(tree, i, arr[i], N);
        }

        // M + K번 쿼리 반복 저장
        // 요청의 횟수(구간 합 요청 + 변경 요청)
        int total = M + K;
        for (int i = 0; i < total; i++) {
            // 횟수만큼 리밋 주고 요청을 반복해서 받아낸다.
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // N이 백만개가 될수도 있다. 일반적인 덧셈 방식으로는 연산 속도가 느림
            // 펜윅 트리 형태 (구간 합을 트리 형태로 미리 저장 / 마지막 비트 값을 기준)
            // 홀수 배열은 값을 그대로 가지고 있다. (마지막 비트가 항상 1)
            // 짝수 배열은 마지막 비트가 0이다. -> 마지막 1이 몇 번째 자리냐에 따라 담당구역의 크기가 달라진다.
            //1 = 0001 → 마지막 1이 1번째 자리 → 1개 담당
            //2 = 0010 → 마지막 1이 2번째 자리 → 2개 담당
            //4 = 0100 → 마지막 1이 3번째 자리 → 4개 담당
            //8 = 1000 → 마지막 1이 4번째 자리 → 8개 담당
            
            // 순서 변경
            if (a == 1) {
                // 1 3 6 이면 1은 변경 플래그, 3번째 값을 6으로 바꿔라
                
                // 변경 전/후 차이
                long diff = c - arr[b];
                // 원본 갱신
                arr[b] = c;
                // 트리 갱신
                update(tree, b, diff, N);
            } else {
                // 합계값 갱신
                sb.append(query(tree, c) - query(tree, b - 1)).append("\n");
            }
        }

        System.out.println(sb);
    }

    // i & -i 가 마지막 1비트 추출
    // update => 값 변경 시 트리 갱신
    static void update(long[] tree, int i, long diff, int N) {
        while(i <= N) {
            tree[i] += diff;
            // 담당 구역 위로 올라가며 갱신
            i += (i & -i);
        }
    }

    // i & -i 가 마지막 1비트 추출
    // query => 1~i 구간 합
    // i에서 내려가면서 합산 처리
    static long query(long[] tree, int i) {
        long sum = 0;
        while(i > 0) {
            sum += tree[i];

            // 담당 구역 아래로 내려가며 합산 (이전 구간으로 이동)
            i -= (i & -i);
        }

        return sum;
    }


}
