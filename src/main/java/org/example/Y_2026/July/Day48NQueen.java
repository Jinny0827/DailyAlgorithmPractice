package org.example.Y_2026.July;

import java.util.Scanner;

/**
 * Day48 N-Queen
 *
 * N×N 크기의 체스판에 퀸 N개를 서로 공격할 수 없게 배치하는 방법의 수를 구하세요.
 * (퀸은 같은 행, 같은 열, 대각선에 있는 말을 공격할 수 있습니다.)
 *
 * 입력
 * 1. 첫째 줄에 N이 주어진다. (1 ≤ N ≤ 14)
 *
 * 출력
 * 2. N-Queen 문제의 해의 개수를 출력한다.
 *
 * 예시
 * 1. 입력: 8 → 출력: 92
 * 2. 입력: 4 → 출력: 2
 *
 * 제한 조건
 *
 * 1. N ≤ 14 (N=14일 때도 시간 내 통과해야 함)
 * 2. 시간 제한을 고려한 가지치기(pruning) 필요
 */
public class Day48NQueen {

    static int N;
    static int[] col;
    static int count = 0;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 체스판의 가로/세로/퀸의 갯수
        N = scanner.nextInt();
        // row번째 행에 배치된 퀸의 열 번호
        col = new int[N];
        place(0);

        System.out.println(count);

    }

    static void place(int row) {
        // 퀸을 다놓았다는 조건문 5x5면 5행을 다돌았다는 얘기
        if (row == N) {
            // 5행을 돌때까지 겹치는 이슈가 발생하지 않으면 방법으로 카운트
            count++;
            return;
        }

        // 첫 행의 열마다 퀸을 돌아가며 놓고 행을 내려가며 열/대각선에 퀸이 있는지 조건 비교
        for(int c = 0; c < N; c++) {
            // place 함수가 컬럼의 갯수에 맞게 계속 동작
            // c에 퀸을 두고 시작
            if(isValid(row, c)) {
                // 같은 열/대각선에 퀸이 없다면 퀸을 배치
                col[row] = c;
                // 다음 행 퀸 배치 위해 재귀
                place(row + 1);
            }
        }
    }

    static boolean isValid(int row, int c) {
        for (int r = 0; r < row; r++) {
            if(col[r] == c) {
                // 같은 열
                return false;
            }
            if(Math.abs(row - r) == Math.abs(col[r] - c)) {
                // 같은 대각선
                return false;
            }
        }

        return true;
    }
}
