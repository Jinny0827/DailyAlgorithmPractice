package org.example.Y_2026.May;


import java.util.Scanner;

/**
 * Day27 계단 수 (비트마스킹 DP)
 *
 * 인접한 모든 자리의 숫자 차이가 1인 수를 계단 수라고 한다.
 * 길이가 N이고, 0~9까지 모든 숫자가 최소 한 번씩 등장하는 계단 수의 개수를 구하라.
 * 답은 1,000,000,000으로 나눈 나머지를 출력한다.
 */
public class Day27BitMaskingDP {
    /**
     * dp[i][j][k]
     *  i = 지금 몇 자리까지 만들었나 (1~N)
     *  j = 마지막에 붙인 숫자 (0~9)
     *  k = 지금까지 쓴 숫자 집합 (비트마스크 0~1023)
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 숫자의 길이 (예) 123 -> 3)
        int N = scanner.nextInt();

        // i = 자릿수 → 최대 N자리까지 → N+1
        // j = 마지막 숫자 → 0~9 → 10
        // k = 사용한 숫자 집합 → 0~1023 → 1024
        long[][][] dp = new long[N+1][10][1024];

        // 초기 값 설정
        // << 는 비트마스크에서 j번 숫자를 사용했다고 표시하는 방법
        for (int j = 1; j <= 9; j++) {
            dp[1][j][1 << j] = 1;
        }


        // 1 << next 는 이진수의 1자리를 앞자리로 바꿔준다. 0001 -> 0010 = 2의 지수가 늘어나는 격
        // 본 계산 설정
        for (int i = 1; i < N; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k < 1024; k++) {
                    if(dp[i][j][k] == 0) {
                        continue;
                    }
                    
                    // (숫자 자리수만큼 반복하는) i에 대해서 +-1 하여 계단 처리
                    // 여기 if는 0보다 클때 - 처리
                    if (j > 0) {
                        int next = j - 1;
                        int nk = k | (1 << next);
                        dp[i+1][next][nk] += dp[i][j][k];
                    }
                    
                    // 여기 if는 9보다 작을때 +처리
                    if (j < 9) {
                        int next = j + 1;
                        int nk = k | (1 << next);
                        dp[i+1][next][nk] += dp[i][j][k];
                    }


                }
            }
        }

        long answer = 0;
        for (int j = 0; j <= 9; j++) {
            // N 자리이고 0~9 전부 사용한 경우를 책정
            answer += dp[N][j][1023];
        }

        System.out.println(answer % 1_000_000_000);

    }

}
