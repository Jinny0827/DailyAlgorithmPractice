package org.example.Y_2026.May;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Day 20 가장 긴 증가하는 부분 수열 (LIS)
 *
 * 수열 A가 주어졌을 때, 가장 긴 증가하는 부분 수열을 구하는 프로그램을 작성하시오.
 * 예를 들어, 수열 A = {10, 20, 10, 30, 20, 50} 인 경우,
 * 가장 긴 증가하는 부분 수열은 {10, 20, 30, 50} 이고, 길이는 4이다.
 * 부분 수열 = 순서를 유지하면서 일부 원소만 뽑은 것
 */
public class Day20LIS {

    // 원본 순서 유지 — 앞에 있던 게 앞에 와야 함
    // 값이 계속 증가 — 뒤로 갈수록 커져야 함

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 수열의 갯수
        int N = Integer.parseInt(br.readLine());

        // 두 번째 줄 (수열)은 숫자가 공백
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] A = new int[N];
        for(int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        // dp 배열을 선언 후 1로 채워놓는다.
        int[] dp = new int[N];
        Arrays.fill(dp, 1);

        for (int i = 1; i < N; i++) {
            for(int j = 0; j < i; j++ ){
                if(A[j] < A[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

    }



}
