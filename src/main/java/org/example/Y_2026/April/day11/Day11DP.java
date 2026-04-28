package org.example.Y_2026.April.day11;

/**
 * DP (동적 프로그래밍)
 *
 * 집에서 학교까지 가장 짧은 경로로 등교하려 합니다.
 * 이동은 오른쪽 또는 아래쪽으로만 가능하고, 중간에 웅덩이가 있으면 해당 칸은 지나갈 수 없습니다.
 * 최단 경로의 경우의 수를 구하세요. (단, 경우의 수는 1,000,000,007로 나눈 나머지 반환)
 */
public class Day11DP {
    // m = 열 수 (가로), n = 행 수 (세로), puddles = 웅덩이 좌표 (가로, 세로 쌍의 배열)
    // puddles가 2차원배열인 이유는 웅덩이가 여러개 [[1번웅덩이], [2번웅덩이], ...]
    public int solution(int m, int n, int[][] puddles) {
        // dp[n][m] 2차원 배열 생성
        int dp[][] = new int[n][m];

        // 이중 for 문(행 r: 0~n,열 c: 0~m -> r,c는 목적 칸의 배열)
        for (int r = 0; r < n; r++) {
            for(int c = 0; c < m; c++) {

                // 웅덩이 체크
                boolean isPuddle = false;
                for(int[] p : puddles) {
                    if(p[0] - 1 == c && p[1] - 1 == r) {
                        // 사용자의 입력 값은 인덱스를 고려하지 않은 값 (인덱스는 - 1 처리)
                        // puddles 배열은 인덱스가 1번부터 시작하고, dp 배열은 인덱스가 0번부터 시작
                        // puddles[][] = [[2,2]]
                        // puddles[0][0](열 위치) = 2, puddles[0][1](행 위치) = 2
                        // 1을 빼주면
                        isPuddle = true;
                        break;
                    }
                }
                // 웅덩이 칸은 전부 0으로 처리(continue 시 경로 계산 x)
                if (isPuddle) continue;

                // dp 채우기
                // 첫 행 또는 첫 열
                // 첫 행 또는 첫 열 중간에 웅덩이가 있는 경우? = 그 오른쪽칸/아래칸으로 바로 이동할 수 없음
                // 숫자를 더해주는게 아니고 1 아님 0으로 갈 수 있는지 없는지로
                if(r == 0 && c == 0) {
                    // 시작점
                    dp[r][c] = 1;
                } else if(r == 0) {
                    dp[r][c] = dp[r][c-1];
                } else if(c == 0) {
                    dp[r][c] = dp[r-1][c];
                } else {
                    // 위에서 오는 경우의 수 + 왼쪽에서 오는 경우의 수 = 현재 칸의 총 경우의 수
                    // % 1000000007는 int 범위 초과 시 예외 처리
                    dp[r][c] = (dp[r-1][c] + dp[r][c-1]) % 1000000007;
                }
            }
        }

        // 배열은 전부 0부터 시작
        // 웅덩이를 만나면 1로 채우거나 경우의수를 생각하지 않고 continue 처리
        return dp[n-1][m-1];
    }
}
