package org.example.Y_2026.May;

import java.util.Scanner;

/**
 * N×M 크기의 방에서 로봇 청소기가 아래 알고리즘으로 움직인다.
 * 청소한 칸의 수를 구하라.
 *
 * 동작 알고리즘
 * 1. 현재 칸이 청소 안 됐으면 → 청소
 * 2. 현재 방향 기준 왼쪽부터 회전하며 청소 안 된 칸 탐색
 *    2-1. 있으면 → 그 방향으로 전진
 *    2-2. 네방향 모두 청소됐거나 벽이면 → 뒤로 후진 (방향 유지)
 * 3. 후진할 위치도 벽이면 → 종료
 *
 */
public class Day16RobotCleaner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt(); // 행
        int M = scanner.nextInt(); // 열

        int r = scanner.nextInt(); // 시작 행
        int c = scanner.nextInt(); // 시작 열
        int d = scanner.nextInt(); // 시작 방향

        // 지도
        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = scanner.nextInt();
            }
        }
    }


}
