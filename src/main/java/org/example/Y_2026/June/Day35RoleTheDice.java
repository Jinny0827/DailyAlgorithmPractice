package org.example.Y_2026.June;

import java.util.Scanner;

/**
 * Day35 주사위 굴리기 (구현/시뮬레이션)
 *
 * N×M 크기의 지도 위에 주사위를 놓고 명령에 따라 굴린다.
 *
 * 주사위는 칸에 놓일 때, 지도의 해당 칸 값이 0이면 주사위 바닥면 값을 칸에 복사하고, 0이 아니면 지도의 값을 주사위 바닥면에 복사한 후 칸을 0으로 만든다.
 *
 * 각 이동 후 주사위의 윗면 숫자를 출력하라.
 *
 */
public class Day35RoleTheDice {

    // 입력 값 N M x y K(명령 횟수 = 주사위 굴리는 횟수)
    // 그 다음부턴 지도 값 나열
    // 마지막 줄엔 명령 K개에 대한 나열


    // 동(1), 서(2), 북(3), 남(4) 순서에 따른 좌표 변화량
    // 인덱스 0은 dummy로 비워두고 1~4번을 사용합니다.
    // 문제 기준: 북쪽은 행 감소(-1), 남쪽은 행 증가(+1), 동쪽은 열 증가(+1), 서쪽은 열 감소(-1)
    // x(행) 이동
    static int[] dx = {0, 0, 0, -1, 1};
    // y(열) 이동
    static int [] dy = {0, 1, -1, 0, 0};


    public static void main(String[] args) {
        // 입력 줄 수가 적고 연산에 대한 제한 시간도 넉넉해서 BufferedReader가 아닌 Scanner 사용
        Scanner scanner = new Scanner(System.in);

        // 차례로 지도 행,열 / 시작 행,열 / 주사위 굴리는 명령 횟수
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int K = scanner.nextInt();

        // 지도 배열 초기화
        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = scanner.nextInt();
            }
        }

        // 명령횟수에 대한 배열 초기화
        int[] commands = new int[K];
        for(int i = 0; i < K; i++) {
            commands[i] = scanner.nextInt();
        }

        // 주사위 상태(표현) 배열
        // 인덱스: 0=위, 1=아래, 2=북(앞), 3=남(뒤), 4=서(왼), 5=동(오른)
        int[] dice = new int[6];

        // commands 순회하며 시뮬레이션 진행
        for (int i = 0; i < K; i++) {
            int command = commands[i];

            // 다음 이동할 좌표 계산
            // 현 행/열(좌표) + 이동할 방향을 행/열로 지정해놓은 배열안에 값 가져오기
            int nx = x + dx[command];
            int ny = y + dy[command];

            // 지도 밖으로 나가는 명령은 무시한다. (이동 가능 여부 파악)
            if(nx < 0 || nx >= N || ny < 0 || ny >= M) {
                continue;
            }
            
            // 이동가능하다면 현재 좌표 업데이트
            x = nx;
            y = ny;

            // 주사위 굴리기
            rollDice(command, dice);

            // 지도와 주사위 바닥면(dice[1]) 상호 작용
            if(map[x][y] == 0) {
                // 지도의 칸이 0이면 주사위 바닥면의 값을 칸에 복사
                map[x][y] = dice[1];
            } else {
                // 0이 아니면 지도의 값이 주사위 바닥 값으로 복사되고, 칸은 0이 됨
                dice[1] = map[x][y];
                map[x][y] = 0;
            }

            System.out.println(dice[0]);
        }
    }

    static void rollDice(int command, int[] dice) {
        // 기본 값 위
        int temp = dice[0];

        switch (command) {
            case 1: // 동 (서->위, 아래->서, 동->아래, 위->동)
                dice[0] = dice[4];
                dice[4] = dice[1];
                dice[1] = dice[5];
                dice[5] = temp;
                break;
            case 2: // 서 (동->위, 아래->동, 서->아래, 위->서)
                dice[0] = dice[5];
                dice[5] = dice[1];
                dice[1] = dice[4];
                dice[4] = temp;
                break;
            case 3: // 북 (남->위, 아래->남, 북->아래, 위->북)
                dice[0] = dice[3];
                dice[3] = dice[1];
                dice[1] = dice[2];
                dice[2] = temp;
                break;
            case 4: // 남 (북->위, 아래->북, 남->아래, 위->남)
                dice[0] = dice[2];
                dice[2] = dice[1];
                dice[1] = dice[3];
                dice[3] = temp;
                break;
        }

    }


}
