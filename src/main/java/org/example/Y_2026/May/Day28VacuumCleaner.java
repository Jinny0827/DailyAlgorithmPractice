package org.example.Y_2026.May;


import java.util.Scanner;

/**
 * Day28 로봇 청소기
 *
 * N×M 크기의 방에서 로봇 청소기가 아래 알고리즘으로 움직인다. 청소한 칸의 수를 구하라.
 * 동작 알고리즘
 *
 * 현재 칸이 청소 안 됐으면 → 청소
 * 현재 방향 기준 왼쪽부터 회전하며 청소 안 된 칸 탐색
 *
 * 있으면 → 그 방향으로 전진 후 1번으로
 * 네 방향 모두 청소됐거나 벽이면 → 뒤로 후진 (방향 유지)
 *
 *
 * 후진할 위치도 벽이면 → 종료
 */

public class Day28VacuumCleaner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 행
        int N = scanner.nextInt();
        
        // 열
        int M = scanner.nextInt();

        // 시작 행
        int r = scanner.nextInt();
        // 시작 열
        int c = scanner.nextInt();
        // 시작 방향
        int d = scanner.nextInt();

        // 빈 칸과 벽에 대한 입력 값 받기
        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = scanner.nextInt();
            }
        }

        // 방위에 따른 이동 계획
        // 북(0) 동(1) 남(2) 서(3)
        // 행 방향 전환
        int[] dr = {-1, 0, 1, 0};
        // 열 방향 전환
        int[] dc = {0, 1, 0, -1};

        int count = 0;

        while(true) {
            // 현재 칸 청소
            if (map[r][c] == 0) {
                // 현재 칸 청소 완료 후 count 증가 처리
                map[r][c] = 2;
                count++;
            }
            
            // 왼쪽으로 4번 회전하며 빈칸 탐색
            boolean moved = false;
            for (int i = 0; i < 4; i++) {
                // 왼쪽 회전 공식
                d = (d + 3) % 4;
            
                // 회전 후 앞 칸 좌표
                // 현재 시작행 + 바뀐 방향 을 계산해서 앞칸에 해당하는 행,열을 구한다.
                int nr = r + dr[d];
                int nc = c + dc[d];

                // 왼쪽해서 바라보는 칸의 값이 0이라면 (빈칸/이동했었던칸이아니라면)
                if(map[nr][nc] == 0) {
                    // 현재 행/열 수정
                    // 이동 가능 처리 / 반복문 멈춤처리
                    r = nr;
                    c = nc;
                    moved = true;
                    break;
                }
            }
            
            // 못 움직였으면 후진
            if (!moved) {
                // 뒤 방향 계산
                int back = (d + 2) % 4;
                
                // 후진 좌표
                int br = r + dr[back];
                int bc = c + dc[back];
                
                // 벽이면 종료
                if (map[br][bc] == 1) {
                    break;
                }

                // 아니면 후진
                r = br;
                c = bc;
            }
        }

        System.out.println(count);

    }

}
