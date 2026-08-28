package org.example.Y_2026.August;

import java.util.Scanner;

/**
 * Day75 알파벳
 *
 * 문제 설명
 * 세로 R칸, 가로 C칸으로 된 보드의 각 칸에는 대문자 알파벳이 하나씩 적혀 있습니다.
 * 말은 (0,0)에서 시작하여 상하좌우로 인접한 칸으로 이동할 수 있는데, 지금까지 지나온 경로에 이미 나온 알파벳이 적힌 칸으로는 이동할 수 없다.
 * 말이 최대한 몇 개의 칸을 지날 수 있는지(시작 칸 포함) 구하세요.
 *
 * 입력:
 *
 * 2 4
 * CAAB
 * ADCB
 *
 * 출력:
 *
 * 6
 *
 */
public class Day75Alphabet {

    static int R;
    static int C;
    static char[][] board;
    static boolean[] visited;
    
    // 상하 / 좌우에 대한 배열
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 세로 방향 R (row)
        R = scanner.nextInt();
        // 가로 방향 C (column)
        C = scanner.nextInt();

        // char 2차원 배열로 직관적인 알파벳 나열 및 접근
        board = new char[R][C];
        visited = new boolean[26];
        for (int i = 0; i < R; i++) {
            // scanner.next는 공백전까지의 토큰을 통째로 읽음 (예) CAAB)
            // .toCharArray는 그 토큰 String을 문자 단위로 쪼개서 배열로 전환시킴
            board[i] = scanner.next().toCharArray();
        }

        // 가장 긴 경로에 대한 칸 결과값
        int result = DFS(0, 0);

        System.out.println(result);
    }

    // 0,0 부터 시작하는 재귀 알파벳 순회
    private static int DFS(int x, int y) {
        
        // 현재 칸 board[x][y]의 알파벳을 visited에 표시 (방문 처리)
        // Java에서 char는 내부적으로 정수(아스키 코드)라서, 알파벳 대문자끼리는 뺄셈
        // A(65)를 빼주는 방식으로 알파벳에 대한 정수 계산
        visited[board[x][y] - 'A'] = true;

        // max 변수를 만들어 0으로 초기화 (자식 방향들 중 최댓값 저장용)
        int max = 0;

        // dx, dy를 이용해 4방향 순회
        for(int i = 0; i < 4; i++) {
            // 상하/좌우의 순회되었을 때 다음 칸 값들 표현
            int nx = x + dx[i];
            int ny = y + dy[i];

            // 다음 값의 상하좌우가 0이 아니고 R과 C(최대 범위)를 벗어나지 않으며 방문하지 않았을때(알파벳 26개 범위에서)
            // 새 좌표가 보드 범위를 벗어나지 않는지 체크
            // 그 칸의 알파벳이 아직 visited가 아닌지 체크
            if(nx >= 0 && nx < R &&  ny >= 0 && ny < C) {
                // 다음 칸의 값을 알파벳에서 뺏을 때 값 -> 뒤 조건문에서 visited 배열 사용
                int letter = board[nx][ny] - 'A';
                // 콜스택에 쌓인 채로 자식 호출이 끝나기를 기다린다. 자식이 return해야 그 다음 줄로 진행
                if(!visited[letter]) {
                    // 조건 만족하면 재귀 호출해서 결과를 max와 비교 갱신
                    visited[letter] = true;
                    int next = DFS(nx, ny);
                    max = Math.max(max, next);
                    // 현재 칸의 방문 표시를 해제 (백트래킹)
                    visited[letter] = false;
                }
            }
            
            // 한방향을 쭉 파는 DFS를 통해 한방향이 반환되고 max가 묶여있는 상태에서 다른 방향(다른 경로)를 실행시키며 max 치를 비교
        }
        // 1 + max 반환 (0,0의 칸이 1이므로 -> 0,0칸 갯수 + 총 갯수)
        return 1 + max;
    }
}
