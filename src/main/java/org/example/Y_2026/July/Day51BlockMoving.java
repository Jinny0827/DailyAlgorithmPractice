package org.example.Y_2026.July;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Day 51 블록 이동하기
 *
 * 2 x 1 크기 로봇이 0(빈칸)과 1(벽)로 이루어진 N x N 지도 위에서 움직입니다.
 * 좌표는 왼쪽 상단이 (1,1). 로봇은 처음 (1,1)에 가로 방향으로 놓여 있고,
 * 목표는 두 칸 중 한 칸이라도 (N,N)에 도달하는 것입니다.
 *
 * 이동: 현재 방향을 유지한 채 상하좌우로 한 칸씩 이동 (벽이나 지도 밖 불가)
 * 회전: 로봇이 차지한 두 칸 중 한 칸을 축으로 90도 회전 가능. 단, 축의 대각선 방향 두 칸 모두 벽이 없어야 함
 * 이동 1칸, 회전 1회 모두 1초 소요
 *
 * 지도 board가 주어질 때 (N,N)까지 걸리는 최소 시간을 반환하는 solution 함수를 작성하세요.
 */
public class Day51BlockMoving {

    // N x N 보드판은 board로 들어옴
    // 최소 시간 정수를 반환하기 위해 반환 타입 int
    public static int solution(int[][] board) {
        // N 값
        int n = board.length;

        // 평행 이동용 방향 (상,하,좌,우) -> x축, y축 준비
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        // visited[row][col][orientation]
        // orientation 0 = 가로(오른쪽 칸과 짝), 1 = 세로(아래 칸과 짝)
        boolean[][][] visited = new boolean[n][n][2];

        // BFS 큐에 넣은 상태 표현
        // {r, c, orientation, time} 순서로 저장
        //Linked List는 노드(객체) 끼리의 주소 포인터를 서로 가리키며 링크(참조)
        Queue<int[]> queue = new LinkedList<>();
        // 초기값 (0,0), 가로, 0초 설정
        queue.offer(new int[]{0, 0, 0, 0});
        visited[0][0][0] = true;

        // 기준 칸(r,c)과 나머지 한칸의 좌표는 방향에 따라 정해진다. (기준 칸과 방향에 따른 +1칸)
        // 4방향으로 이동 시 두칸이 같은 delta만큼 같이 움직인다.
        while(!queue.isEmpty()) {
            // 현재 위치에 대한 정보(예) 0, 0, 0, 0)를 꺼낸다
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];
            int orientation = cur[2];
            int time = cur[3];

            // 두번째 칸 계산 (가로 기준)
            int r2 = (orientation == 0) ? r : r + 1;
            int c2 = (orientation == 0) ? c + 1 : c;
            
            // 이동 로직 출발 전 도착 체크
            // 두칸 중 하나라도 n-1 도착 시 끝칸 계산
            if ((r == n - 1 && c == n - 1) || (r2 == n-1 && c2 == n - 1)) {
                return time;
            }

            // 평행방향 이동 로직
            // 4방향 이동(상,하,좌,우)
            for(int d = 0; d < 4; d++) {
                // nr1, nc1은 로봇 전체가 특정 방향(상/하/좌/우 중 하나, d값에 따라(4방향 반복 순환))으로
                // 한 칸 이동했을 때 기준 칸이 도착하는 좌표
                int nr1 = r + dx[d];
                int nc1 = c + dy[d];
                // 방향이 0이면 가로 1이면 세로
                // 현 방향이 바라보고 있는 방향과 맞는지 확인
                // nr2, nc2는 별도로 이동시키는 게 아니라,
                // 새로 옮겨진 기준 칸(nr1,nc1)을 기준으로, 방향(orientation)에 따라 자동으로 정해지는 짝 칸의 좌표
                int nr2 = (orientation == 0) ? nr1 : nr1 + 1;
                int nc2 = (orientation == 0) ? nc1 + 1 : nc1;

                if(isValid(nr1, nc1, n, board) && isValid(nr2, nc2, n, board)
                    && !visited[nr1][nc1][orientation]) {
                    
                    // 방문 처리
                    visited[nr1][nc1][orientation] = true;
                    // queue에 저장
                    queue.offer(new int[] {nr1, nc1, orientation, time + 1});
                }
            }

            // 회전방향 이동 로직
            if(orientation == 0) {
                // 가로 상태: (r,c), (r,c+1)
                // 위로 회전 - 위쪽 두칸이 다 비어있어야함 (이동 반경이 두칸)
                // x x (이 두곳을 확인)
                // o o
                if(isValid(r - 1, c, n, board) && isValid(r - 1, c + 1, n, board)) {
                    if(!visited[r - 1][c][1]) {
                        // 왼쪽 칸 축
                        visited[r - 1][c][1] = true;
                        queue.offer(new int[] {r - 1, c, 1, time + 1});
                    }
                    if (!visited[r - 1][c + 1][1]) {
                        // 오른쪽 칸 축
                        visited[r - 1][c + 1][1] = true;
                        queue.offer(new int[]{r - 1, c + 1, 1, time + 1});
                    }
                }
                
                // 아래로 회전 - 아래쪽 두칸이 모두 비어있어야함
                // o o
                // x x (이 두곳을 확인)
                if(isValid(r + 1, c, n, board) && isValid(r + 1,c + 1, n, board)) {
                    if (!visited[r][c][1]) {
                        visited[r][c][1] = true;
                        queue.offer(new int[]{r, c, 1, time + 1});
                    }
                    if (!visited[r][c + 1][1]) {
                        visited[r][c + 1][1] = true;
                        queue.offer(new int[]{r, c + 1, 1, time + 1});
                    }
                }
            } else {
                // 세로 상태: (r,c), (r+1,c)
                // 왼쪽으로 회전
                // 왼쪽으로 회전
                // x o o
                // x
                if (isValid(r, c - 1, n, board) && isValid(r + 1, c - 1, n, board)) {
                    if (!visited[r][c - 1][0]) {
                        visited[r][c - 1][0] = true;
                        queue.offer(new int[]{r, c - 1, 0, time + 1});
                    }
                    if (!visited[r + 1][c - 1][0]) {
                        visited[r + 1][c - 1][0] = true;
                        queue.offer(new int[]{r + 1, c - 1, 0, time + 1});
                    }
                }
                // 오른쪽으로 회전
                // o o x
                //     x
                if (isValid(r, c + 1, n, board) && isValid(r + 1, c + 1, n, board)) {
                    if (!visited[r][c][0]) {
                        visited[r][c][0] = true;
                        queue.offer(new int[]{r, c, 0, time + 1});
                    }
                    if (!visited[r + 1][c][0]) {
                        visited[r + 1][c][0] = true;
                        queue.offer(new int[]{r + 1, c, 0, time + 1});
                    }
                }
            }
        }

        // 문제 조건상 항상 도달 가능하므로 실제로는 여기 안 옴, 컴파일용 안전장치
        return -1;
    }
    
    // 범위/벽 체크용 헬퍼 메서드
    private static boolean isValid(int r, int c, int n, int[][] board) {
        /** 아래 조건 모두 만족
         * 1. 이동할 방향이 벽이 아니고 (not 0)
         * 2. N x N 의 숫자 이내 (r/c < n)
         * 3. 이동하는 지도 내 칸이 벽이 아니어야 한다(board[좌우방향][위아래방향] == 0)
        */
        return r >= 0 && r < n && c >= 0 && c < n && board[r][c] == 0;
    }

}
