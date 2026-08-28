package org.example.Y_2026.August;

import java.util.Scanner;

/**
 * Day75 2048
 *
 * N×N 크기의 보드에 2048 게임처럼 숫자 블록들이 놓여 있습니다.
 * 상/하/좌/우 중 한 방향으로 이동시키면 같은 방향으로 블록들이 밀리고,
 * 이동 경로상 같은 값의 블록 두 개가 만나면 하나로 합쳐지며 값이 2배가 됩니다
 * (단, 한 번의 이동에서 이미 합쳐진 블록은 다시 합쳐지지 않음).
 *
 * 이 보드에서 최대 5번까지 이동시킬 수 있을 때, 이동 후 보드에 존재할 수 있는 가장 큰 블록의 값을 구하세요.
 *
 * 입력
 *
 * 3
 * 2 2 2
 * 4 4 4
 * 8 8 8
 *
 * 출력
 *
 * 32
 *
 * 제한 조건
 * 3 ≤ N ≤ 20
 * 보드의 각 값은 0(빈 칸) 또는 2의 거듭제곱 (2 ~ 1024)
 * 이동 횟수는 최대 5번
 */
public class Day752048 {

    static int N;
    static int[][] board;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // N, board 입력받기
        N = scanner.nextInt();
        board = new int[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                board[i][j] = scanner.nextInt();
            }
        }

        // dfs(board, 0) 호출 → 결과를 answer에 저장
        int answer = dfs(board, 0);

        // answer 출력
        System.out.println(answer);
    }
    
    // 5번 이동을 재귀로 시뮬레이션, 매 depth마다 4방향 분기
    private static int dfs(int[][] board, int depth) {

        // 1. max = 0 로 초기화
        int max = 0;

        // 2. 만약 depth == 5: board 안의 최댓값을 찾아서 반환 (재귀 종료)
        if(depth == 5) {
            //board 전체를 2중 for문으로 순회 (i: 0~N-1, j: 0~N-1):
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                   max = Math.max(max, board[i][j]);
                }
            }

            // 현재 값이 max치보다 크면 현재값 반환
            return max;
        }

        // 3. 4방향(상,하,좌,우) 각각에 대해 반복:
        for (int dir = 0; dir < 4; dir++) {

            // 3-1. board를 복사해서 copy 생성
            // 2차원 배열 복사는  clone하는 방식을 사용하면 안된다. -> 겉의 배열(행들의 묶음)만 복사
            int[][] copy = new int[N][N];
            for (int i = 0; i < N; i++) {
                // 각 행마다 clone() -> 완전한 복사 처리
                copy[i] = board[i].clone();
            }

            // 3-2. move(copy, 방향) 호출 → copy가 그 방향으로 밀린 상태로 변함
            move(copy, dir);

            //       3-3. dfs(copy, depth + 1) 재귀 호출 → 반환값을 max와 비교해서 더 큰 값으로 갱신
           int nextDfs = dfs(copy, depth + 1);
           max = Math.max(max, nextDfs);
        }

        //4. max 반환
        return max;
    }

    private static void move(int[][] board, int direction) {
        // direction에 따라 열/행을 각각 추출해서 line이라는 복사 배열에 넣어 mergedLine을 호출하고 새로 생성된 값을 board에 초기화
        switch (direction) {
            // 상/하 (열 선택),좌/우 (행 선택) 순서로
            case 0 :
                // 위로 밀기 (미는 방향이 위)
                // 열 선택
               for (int j = 0; j < N; j++) {
                   int[] line = new int[N];
                   // 열을 기준으로 측정 (위로 밀기)
                   for (int i = 0; i < N; i++) {
                        line[i] = board[i][j];
                   }

                   int[] merged = mergeLine(line);
                   for(int i = 0; i < N; i++) {
                       board[i][j] = merged[i];
                   }
               }
                break;
            case 1 :
                // 아래로 밀기 (미는 방향이 아래)
                // 열 선택 - 순서 상관없음
                for (int j = 0; j < N; j++) {
                    int[] line = new int[N];
                    for (int i = 0; i < N; i++) {
                        // 아래부터 위로 역순 추출
                        line[i] = board[N - 1 - i][j];
                    }

                    int[] merged = mergeLine(line);
                    for (int i = 0; i < N; i++) {
                        // 다시 아래부터 채워넣기
                        board[N - 1 - i][j] = merged[i];
                    }
                }
                break;
            case 2 :
                // 오른쪽으로 밀기
                for (int i = 0; i < N; i++) {
                    // 돌면서 행에 대한 배열을 N개만큼 생성
                    int[] line = new int[N];
                    for (int j = 0; j < N; j++) {
                        // i번째 행을 왼쪽 -> 오른쪽 순서로 추출
                        line[j] = board[i][j];
                    }

                    // 압축 병합
                    int[] merged = mergeLine(line);
                    for (int j = 0; j < N; j++) {
                        // 결과 다시 채워넣기
                        board[i][j] = merged[j];
                    }
                }
                break;
            case 3 :
                // 왼쪽으로 밀기
                for(int i = 0; i < N; i++) {
                    int[] line = new int[N];
                    for(int j = 0; j < N; j++) {
                        line[j] = board[i][N - 1 - j];
                    }

                    int[] merged = mergeLine(line);
                    for(int j = 0; j < N; j++) {
                        board[i][N - 1 - j] = merged[j];
                    }
                }
                break;
        }
    }

    private static int[] mergeLine(int[] line) {
        // 가져온 행/열을 한줄 단위로 숫자확인하여 압축 + 병합한 결과를 반환하는 함수
        int[] result = new int[N];
        boolean[] merged = new boolean[N];
        int idx = 0;


        for(int i = 0; i < N; i++) {
            // 1. 0이 아닌 값만 순서대로 리스트에 담기 (압축)
            if (line[i] == 0) {
                continue;
            }

            // 2. 리스트를 앞에서부터 훑으며:
            //       현재 값 == 다음 값이면 → 합쳐서(2배) 결과에 담고 인덱스 2칸 이동
            //       다르면 → 현재 값 그대로 결과에 담고 인덱스 1칸 이동
            // idx > 0 이고 result[idx-1] == line[i]이고 아직 안합쳐져 있으면 합치기
            if(idx > 0 && line[i] == result[idx - 1] && !merged[idx - 1]) {
                // 합치기 (같은 값이니 2배와 동일)
                result[idx - 1] += line[i];
                // 이 자리는 이미 합쳐졌다고 표시
                merged[idx - 1] = true;
            } else {
                // 이미 합쳐졌거나 값이 다르면 그대로
                result[idx] = line[i];
                // 인덱스 증가
                idx++;
            }
        }

        // 3. 결과 반환
        return result;
    }
}
