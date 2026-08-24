package org.example.Y_2026.August;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Day74 내리막 길 (DFS + 메모제이션)
 *
 * DFS + 메모제이션 (Top-down DP)
 * 1. 언제 쓰여요?
 * 재귀적으로 부분 문제를 나눠 풀 수 있고, 같은 상태를 여러 경로에서 반복 방문하게 될때 사용
 *
 * 2. 핵심 동작 원리
 * 현재 칸에서 이동 가능한 다음 칸들을 DFS로 재귀 탐색, 각 칸에서 도착점까지 가는 경로 수를 배열(dp)에 저장
 * 이미 계산된 칸이면 재귀 호출 없이 저장된 값을 바로 반환해서 중복 계산 막는다.
 *
 * 3. 시간 복잡도
 * O(M X N), 각 칸은 한번만 계산, 칸마다 4방향만 확인하기 때문(공간복잡도도 O(M X N))
 *
 * 문제 설명
 * 지도가 M×N 격자로 주어집니다. 각 칸에는 높이가 적혀 있습니다.
 * (1,1)에서 출발해서 (M,N)까지 이동하는데, 상하좌우로만 이동할 수 있고 반드시 현재 칸보다 높이가 낮은 칸으로만 이동할 수 있습니다.
 * (1,1)에서 (M,N)까지 갈 수 있는 서로 다른 경로의 개수를 구하세요.
 *
 * 입력:
 *
 * 4 5
 * 50 45 37 32 30
 * 35 50 40 20 25
 * 30 30 25 17 28
 * 27 24 22 15 10
 *
 * 출력:
 *
 * 3
 */
public class Day74DownhillPath {

    static long[][] map;
    static long[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // M * N = 행, 열 갯수 입력값
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        // M * N 크기 값 입력받기
        map = new long[M + 1][N + 1];
        dp = new long[M + 1][N + 1];

        // 실제 지도에 대한 각 칸 크기값 입력받기
        for(int i = 1; i <= M; i++) {
            // 행 단위로 새로 입력받음
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());

            }
        }

        // 캐시용 배열 -1 초기화 -> Arrays.fill은 1차원 배열에만 사용 가능 행 단위 반복(자동으로 열값들에 대해서 채워짐)
        for (int i = 0; i <= M; i++) {
            Arrays.fill(dp[i], -1);
        }

        long result = DFS(1, 1);

        System.out.println(result);
    }

    private static long DFS(int x, int y) {

        // 실제 M,N은 + 1 되어있는 상황
        int M = map.length - 1;
        int N = map[0].length - 1;
        long result = 0;

        // 1. x == M && y == N 이면 → return 1 (도착점 도달, 기저 조건. 그냥 return이 아니라 값 1을 반환해야 해요)
        if(x == M  && y == N) {
            return 1;
        }

        // 2. dp[x][y] != -1 이면 → return dp[x][y] (이미 계산됨, 캐시 재사용)
        if(dp[x][y] != -1) {
            return dp[x][y];
        }

        // 3. 메인 로직: 4방향(상하좌우) 중 map의 범위 안에 있고, 현재 칸보다 높이가 낮은 칸으로 재귀 호출 → 결과들을 다 더해서 dp[x][y]에 저장 후 반환
        // 상, 하
        int[] dx = {-1, 1, 0, 0};
        // 좌, 우
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            // 상,하 방향으로 다음 칸 정립
            int nx = x + dx[i];
            // 좌, 우 방향으로 다음 칸 정립
            int ny = y + dy[i];

            // nx, ny가 범위 안에 있고, map[nx][ny] < map[x][y] 이면
            // -> map[nx][ny] < map[x][y]의 재귀가 끝나면 dp[x][y]에 값 저장
            if(nx >= 1 && nx <= M && ny >= 1 && ny <= N && map[nx][ny] < map[x][y]) {
                // DFS(nx, ny) 재귀 호출해서 dp[x][y]에 누적
                // 위 기저 조건에서 return 된 값을 누적시킴
                // 1,1은 여기서 멈춰있고 2,1이 실행되면서 타고 내려간다고 생각 (싱글스레드)
                result += DFS(nx, ny);
            }
        }

        // 합산된 result를 dp의 해당 칸에 저장
        dp[x][y] = result;

        // 누적된 result를 반환 (재귀하며 저장시킨 값들을 )
        return result;
    }

}
