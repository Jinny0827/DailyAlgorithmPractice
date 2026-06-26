package org.example.Y_2026.June;

import java.util.Scanner;

/**
 * Day41 파이프 옮기기
 *
 * N×N 집에서 파이프를 (1,1)~(1,2) 에서 (N,N) 까지 옮기려 한다.
 * 출력은 파이프를 옮길 수 있는 경우의 수
 *
 * 파이프는 가로 / 세로 / 대각선 3가지 상태를 가지며 이동 규칙은 아래와 같다.
 *
 * 현재 상태   가능한 다음 상태
 * 가로        가로, 대각선
 * 세로        세로, 대각선
 * 대각선      가로, 세로, 대각선
 */
public class Day41PipeMoving {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();

        // N x N의 지도에 대한 노드 입력받기
        int[][] map = new int[N + 1][N + 1];
        for(int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                map[i][j] = scanner.nextInt();
            }
        }

        // 경우의 수를 담기 위한 dp 배열 [가로][세로][가로,세로,대각선의 경우의 수 배열]
        // 0 : 가로, 1 : 세로, 2 : 대각선
        int[][][] dp = new int[N + 1][N + 1][3];

        // 시작점 초기화
        // (1,2)에 가로 상태로 1가지
        dp[1][2][0] = 1;

        for(int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {

                if (map[i][j] == 1) {
                    continue;
                }
                // 가로 상태에 대한 경우의 수 값 저장
                dp[i][j][0] += dp[i][j-1][0] + dp[i][j-1][2];
                // 세로 상태에 대한 경우의 수 값 저장
                dp[i][j][1] += dp[i-1][j][1] + dp[i-1][j][2];

                // 대각선의 경우 위 아래 상태 값이 전부 0(빈 칸)이어야 가능
                if(map[i-1][j] == 0 && map[i][j-1] == 0) {
                    // 대각선 상태에 대한 경우의 수 값 저장
                    dp[i][j][2] += dp[i-1][j-1][0] + dp[i-1][j-1][1] + dp[i-1][j-1][2];
                }
            }
        }

        System.out.println(dp[N][N][0] + dp[N][N][1] + dp[N][N][2]);
    }

}
