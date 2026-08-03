package org.example.Y_2026.August;

import java.util.Scanner;

/**
 * Day62 행렬 곱셈 순서
 *
 * N개의 행렬이 주어질 때, 모든 행렬을 순서대로 곱하는 데 필요한 곱셈 연산 횟수를 최소화하는 계산 순서를 찾아 그 최소 곱셈 횟수를 구하세요.
 * (행렬 곱셈은 결합법칙이 성립하므로 계산 순서를 바꿔도 결과는 같지만 연산 횟수는 달라집니다.)
 * 항상 순서대로 곱셈이 가능한 크기만 입력으로 주어진다
 */

public class Day62SequenceMatrixMultiplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 이어져 있는 선형 행렬이므로 3,4 -> 4,5 -> 5,6 이런식으로 행렬 입력값이 들어온다.
        // 선형 행렬에 대한 곱셈 후 덧셈 값이 작은 값을 구해야한다.
        // 작은 구간부터 답을 구해서 저장해두고, 큰 구간을 구할 때 그 저장된 값을 재활용하는 방식(DP)
        // 행렬의 갯수
        int N = scanner.nextInt();
        
        // 입력을 p 배열로 변환
        int[] p = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            if(i == 1) {
                p[0] = r;
            }
            // 열값만 넣는다. -> 행 값은 이전 열 값( [N - 1] )
            p[i] = c;
        }

        // dp[][] 테이블 준비 (한 번 계산한 부분 답을 저장해뒀다가, 나중에 다시 써먹기 위한 저장소)
        // dp[i][i]는 자동 0
        long dp[][] = new long[N + 1][N + 1];

        // 구간 길이(len)를 2부터 N까지 늘려가며 채우기 (1은 행렬이 한개 그 자체이므로 곱해줄 필요 X => 2개 이상이어야 곱셈)
        // 한마디로 행렬간 곱셈을 순서대로 처리할 반복문
        
        // len = 지금 몇개짜리 구간 계산할 차례(작은거 먼저 -> 큰거 순서로 진행)
        // i = 그 구간이 몇번 행렬에서 시작하는지
        // j = i와 len으로 자동 계산되는 끝점 (몇번행렬에서 끝나는지)
        // k = 그 구간[i,j] 안에서 어디를 잘라서 두 덩어리로 나눠볼까 하나씩 다 시도해보는 후보 지점
        for(int len = 2; len <= N; len++) {
            // 시작점
            for(int i = 1; i <= N - len + 1; i++) {
                // 끝점 = 구간의 끝점이 N을 넘으면 안되서 + 1
                int j = i + len - 1;
                dp[i][j] = Long.MAX_VALUE;
                for(int k = i; k < j; k++) {
                    // 어디서 나눌지..
                    long cost = dp[i][k] + dp[k+1][j] + (long) p[i-1] * p[k] * p[j];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        // len2에서 베이스로 행렬 한개씩의 곱셈을 만들어둔다?

        // 정답 출력
        System.out.println(dp[1][N]);
    }

}
