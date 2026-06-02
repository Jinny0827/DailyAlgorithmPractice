package org.example.Y_2026.June;

import java.util.Scanner;

/**
 * Day01 테트로미노
 *
 * 폴리오미노란 크기가 1×1인 정사각형을 여러 개 이어 붙인 도형이다.
 * 테트로미노는 정사각형 4개를 이어 붙인 도형으로, 회전·대칭 포함 5가지 종류가 있다.
 * N×M 크기의 종이에 각 칸마다 숫자가 적혀 있을 때, 테트로미노 하나를 종이 위에 올려놓고 4개 칸의 숫자 합이 최대가 되도록 하라.
 *
 */
public class Day01Tetromino {

    // dfs 함수 재귀호출을 위해 N,M, map은 전역변수로 취급
    static int N, M;
    static int[][] map;
    static boolean[][] visited;
    static int answer = 0;
    
    // 동서남북 방위
    // 북 남 서 동 (행 변화)
    static int[] dr = {-1, 1, 0, 0};
    // 북 남 서 동 (열 변화)
    static int[] dc = {0, 0, -1, 1};
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 종이의 크기
        // 행
        N = scanner.nextInt();
        // 열
        M = scanner.nextInt();

        // 종이 지도
        map = new int [N][M];
        // 방문 여부
        visited = new boolean[N][M];
        
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = scanner.nextInt();
            }
        }

        // 모든칸을 시작점으로 DFS (깊이 우선 탐색) -> 초기화
        for(int i =0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                visited[i][j] = true;
                dfs(i, j, 1, map[i][j]);
                visited[i][j] = false;
                tShape(i, j);
            }
        }

        System.out.println(answer);
    }


    // 4 방향에 대한 탐색
    static void dfs(int r, int c, int depth, int sum) {
        
        // 4칸 다 골랐으면 최대값 갱신 후 종료
        if (depth == 4) {
            answer = Math.max(answer, sum);
            return;
        }

        // 상하좌우 탐색
        for(int i = 0; i < 4; i++) {
            // 행 변화 (북남동서 돌면서 값 확인)
            int nr = r + dr[i];
            // 열 변화 (북남동서 돌면서 값 확인)
            int nc = c + dc[i];

            // 범위 안에 있다 && 아직 방문 안했다면?
            if(nr >= 0 && nr < N && nc >=0 && nc < M && !visited[nr][nc]) {
                // 방문 완
                visited[nr][nc] = true;
                // 최대값 갱신
                dfs(nr, nc, depth + 1, sum + map[nr][nc]);
                // 방문 풀기
                visited[nr][nc] = false;
            }
        }
    }


    // T형 처리
    static void tShape(int r, int c) {
        // 상하좌우 방향중 3개 선택 가능
        // 4방향 중 하나를 제외한 3방향 합산
        for (int skip = 0; skip < 4; skip++) {
            int sum = map[r][c];
            boolean valid = true;

            for(int i = 0; i < 4; i++){
                if (i == skip) continue;

                int nr = r + dr[i];
                int nc = c + dc[i];

                if(nr < 0 || nr >= N || nc < 0 || nc >= M) {
                    valid = false;
                    break;
                }
                sum += map[nr][nc];

            }

            if (valid) answer = Math.max(answer, sum);
        }
    }
}
