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

        // 북->동->남->서 순서로 행/열 변화량
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        int count = 0;

        while(true) {
            // 1. 현재 칸 청소 후 마킹 (2 처리)
            if (map[r][c] == 0) {
                map[r][c] = 2; // 청소 완료 마킹처리
                count++; // 청소 후 갯수 증가
            }

            // 2. 왼쪽 회전
            boolean moved = false;
            for (int i = 0; i < 4; i++) {
                d = (d + 3) % 4;

                // 회전의 방향에 따라 달라짐
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (map[nr][nc] == 0) {
                    // 왼쪽 회전 후 해당 칸 청소 여부 확인
                    r = nr;
                    c = nc;
                    moved = true;
                    break;
                }
            }


            // 3. 뒤 방향 (후진) -> 못 움직였다면
            if(!moved) {
                int back = (d + 2) % 4;

                int br = r + dr[back];
                int bc = c + dc[back];

                if(map[br][bc] == 1) {
                    // 후진시에도 벽이면
                    break;
                }

                r = br;
                c = bc;
            }
        }

        System.out.println(count);
    }
}
