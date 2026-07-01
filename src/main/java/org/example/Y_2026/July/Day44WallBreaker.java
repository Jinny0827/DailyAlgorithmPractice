package org.example.Y_2026.July;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Day44 벽 부수고 이동하기
 *
 * N×M 크기의 행렬로 표현되는 맵이 있다.
 * 맵 안에는 0과 1이 있는데, 0은 이동할 수 있는 칸을, 1은 이동할 수 없는 벽이 있는 칸을 나타낸다.
 * (1,1)에서 (N,M)까지 이동하려 하는데, 이동하는 거리는 지나는 칸의 개수다. 이때 벽을 한 개까지 부수고 이동할 수 있다면, 최단 거리를 구하라.
 */
public class Day44WallBreaker {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // N x M칸
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] map = new int[N][M];
        for(int i = 0; i < N; i++) {
            String line = br.readLine();
            for(int j = 0; j < M; j++) {
                // Java에서는 char끼리 뺄셈을 하면, 자동으로 두 문자의 아스키코드 값(정수)끼리 빼기
                // 결과는 int 타입이 된다. (char X)
                map[i][j] = line.charAt(j) - '0';
            }
        }

        // 해당 칸의 벽을 부쉈나 안부쉈나 여부
        int[][][] visited = new int[N][M][2];
        // 시작점으로부터의 해당 칸에 대한 거리 저장 배열
        int[][][] dist = new int[N][M][2];
        // 큐 {행,열, 부순여부} 3가지 정보를 담을 수 있는 구조
        Queue<int[]> queue = new LinkedList<>();
        
        // 초기값 설정
        // 시작 칸도 거리 1로 카운트
        visited[0][0][0] = 1;
        dist[0][0][0] = 1;
        queue.add(new int[] {0, 0, 0});

        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];
            int broken = cur[2];

            // 목표점에 도달 시 로그 전송 및 반환
            if(r == N - 1 && c == M - 1) {
                System.out.println(dist[r][c][broken]);
                return;
            }

            // 이동할 방위 지정(동,서,남,북)
            // 행과 열에 다르게 지정
            int[] dr = {-1, 1, 0, 0}; // 상,하,좌,우 순서 (행 기준)
            int[] dc = {0, 0, -1, 1}; // 상,하,좌,우 순서 (열 기준)

            for(int i = 0; i < 4; i++) {
                // next row, next column
                int nr = r + dr[i];
                int nc = c + dc[i];

                // 1. 범위 체크 - 맵 밖으로 나가면 skip
                if (nr < 0 || nr >= N || nc < 0 || nc >= M) {
                    continue;
                }

                // 2. 다음 칸이 빈칸이고, 같은 broken 상태로 아직 방문 안했다 -> 그 칸으로 이동
                if(map[nr][nc] == 0 && visited[nr][nc][broken] == 0) {
                    visited[nr][nc][broken] = 1;
                    dist[nr][nc][broken] = dist[r][c][broken] + 1;
                    queue.add(new int[] {nr, nc, broken});
                }

                // 3. 다음 칸이 벽이고 부술 수 있는지(broken = 0) 확인 후 부수고 이동 (broken을 1로 바꿔 queue에 넣는다.)
                if(map[nr][nc] == 1 && broken == 0 && visited[nr][nc][1] == 0) {
                    // broken이 된 상태 = 1 일때 방문 여부 변화
                    visited[nr][nc][1] = 1;
                    dist[nr][nc][1] = dist[r][c][broken] + 1;
                    // 부순 상태(1)로 큐에 추가
                    queue.add(new int[] {nr, nc, 1});
                }

            }
            
        }

        // 큐가 다 빌 때까지 도착 못 했으면 -1
        System.out.println(-1);

    }

}

