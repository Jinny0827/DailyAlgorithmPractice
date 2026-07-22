package org.example.Y_2026.July;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Day 54 스도쿠
 *
 * 스도쿠는 9×9 표에 1부터 9까지 숫자를 채우는 퍼즐입니다.
 *
 * 각 가로줄, 세로줄에는 1~9가 한 번씩만 나와야 합니다.
 * 표를 9개의 3×3 박스로 나눴을 때, 각 박스 안에도 1~9가 한 번씩만 나와야 합니다.
 *
 * 일부 칸이 채워진 스도쿠 표가 주어지면, 나머지 빈 칸(0으로 표시)을 채워 완성된 표를 출력하세요.
 */
public class Day54Sudoku {

    static int[][] board = new int[9][9];
    static boolean[][] row = new boolean[9][10];
    static boolean[][] col = new boolean[9][10];
    static boolean[][] box = new boolean[9][10];
    static List<int[]> blanks = new ArrayList<>();
    static boolean found = false; // 정답을 찾았는지 여부

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                board[i][j] = scanner.nextInt();

                if (board[i][j] == 0) {
                    // 입력값이 0이 들어오면 차후 입력 가능
                    blanks.add(new int[] {i, j});
                } else {
                    // 입력값에 0이 아닌 숫자가 들어온 경우
                    int num = board[i][j];
                    // *3을 하는 이유는 줄 건너띄기를 위해
                    int b = (i / 3) * 3 + (j / 3);
                    row[i][num] = true;
                    col[j][num] = true;
                    box[b][num] = true;
                }
            }
        }

        // blanks 리스트에 저장해둔 빈 칸들을 순서대로 하나씩 채워나가기
        // 재귀(백트래킹)로 blanks에 담아놓은 채워야하는 칸의 인덱스를 구한다.
        solve(0);
        print();
    }

    // blanks의 사이즈만큼 재귀하며 진행
    private static void solve(int idx) {
        // 정답을 찾았으면 반환
        if(found) {
            return;
        }

        // 0부터 시작한 idx가 빈칸을 모아둔 blanks의 갯수만큼 도달하면 완성했다는 뜻 (빈칸 다채웠다는 뜻)
        // found를 true로 변경
        // 사이즈만큼은 와서 true는 치는데 하단에서 더 넣을 숫자가 없는 경우가 있을 수 있음
        if (idx == blanks.size()) {
            found = true;
            return;
        }

        // 빈칸의 좌표를 i,j 값 얻어오고 box권 계산
        int[] idxBlanks = blanks.get(idx);
        int i = idxBlanks[0];
        int j = idxBlanks[1];
        int b = (i / 3) * 3 + (j / 3);

        // 빈칸에 대해서 위,아래,박스권에서 중복 조건 확인
        for(int num = 1; num <= 9; num++) {
            // 전부 false인 경우에만 진행 (칸 채우기)
            if(!row[i][num] && !col[j][num] && !box[b][num]) {
                board[i][j] = num;
                row[i][num] = col[j][num] = box[b][num] = true;

                // 빈칸의 갯수만큼 재귀
                solve(idx + 1);

                // 재귀된 solve에서 답을 찾았는지 확인(found == true)
                if(found) {
                    return;
                } else {
                    // 빈칸에 num을 넣으며 진행하던 중 어딘가에서 더 넣을 숫자가 없는 경우
                    // 실패 처리
                    board[i][j] = 0;
                    row[i][num] = col[j][num] = box[b][num] = false;
                }
            }
        }
    }

    private static void print() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                // 한줄씩 쌓으면서
                sb.append(board[i][j]);
                // 9개의 칸마다 띄어쓰기 처리
                if(j < 8) {
                    sb.append(" ");
                }
            }
            // 9개의 칸이 한줄 쌓이면 띄어쓰기
            sb.append("\n");
        }

        System.out.println(sb);
    }

}
