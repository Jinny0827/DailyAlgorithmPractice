package org.example.Y_2026.August;


import java.util.Arrays;
import java.util.Scanner;

/**
 *  Day76 외판원 순회 (TSP)
 *
 * N개의 도시가 있다. 한 도시에서 출발해 나머지 도시를 모두 한 번씩 방문한 뒤 다시 출발 도시로 돌아오는 최소 비용 경로를 구하라.
 *
 * 도시 간 도로가 없으면 비용은 0으로 주어진다 (=갈 수 없음)
 * 비용은 대칭이 아닐 수 있다 (Wij ≠ Wji)
 * 한 번 방문한 도시는 다시 방문하지 않는다 (출발 도시로 복귀하는 것만 예외)
 *
 * 입력
 *
 * 4
 * 0 10 15 20
 * 5 0 9 10
 * 6 13 0 12
 * 8 8 9 0
 *
 * 출력
 *
 * 35
 */

public class Day76SalespersonTour {

    static int N;

    // [현재도시][목표도시]
    static int[][] cost;

    // dp[방문도시][방문마스크]
    static int[][] dp;

    // 0인 경우 반환할 값
    static final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        // 핵심 주제: 모든 도시를 한 번씩 방문하는 순서(경로)들 중에서, 표를 조회해 합산했을 때 가장 작은 합을 만드는 순서를 찾는 것
        Scanner scanner = new Scanner(System.in);

        // 도시의 갯수 객체 입력받기
        //1. N 입력받기
        N = scanner.nextInt();

        // 2. cost 배열 크기 N×N으로 초기화
        cost = new int[N][N];

        // 3. cost 배열에 값 채우기 (이중 for문으로 입력받기)
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                cost[i][j] = scanner.nextInt();
            }
        }

        // 4. dp 배열 크기 N × (1<<N) 으로 초기화 -> 마을별로 비트마스크 표현 위해
        // 예) 마을이 4개면 0000 ~ 1111까지 총 16가지(=2^4) 상태가 나온다.
        // 비트시프트 연산인 << 는 2^N과 같다.
        dp = new int[N][1 << N];


        // 5. dp 배열을 전부 -1(미계산 표시)로 채우기
        for(int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }

        // 6. dfs(0번 도시, 방문상태 = 1(0번만 방문한 상태)) 호출
        int result = dfs(0, 1);

        // 7. 결과값 출력
        System.out.println(result);
    }

    private static int dfs(int here, int visited) {
        //1. 만약 visited가 모든 도시를 방문한 상태라면 (visited == (1<<N) - 1):
        if(visited == (1 << N) - 1) {
            // 현재도시 -> 0번 도시로 가는 길이 있는지 확인
            if(cost[here][0] != 0) {
                // 있으면 그 비용 반환,
                return cost[here][0];
            } else {
                // 없으면 INF 반환
                return INF;
            }
        }

        //2. 만약 dp[현재도시][visited]가 이미 계산되어 있다면 (-1이 아니라면):
        //       그 값을 그대로 반환 (재계산 방지)
        if(dp[here][visited] != -1) {
            return dp[here][visited];
        }

        // 3. 최소값 변수를 INF로 초기화
        int minResult = INF;


        //4. 다음 도시 후보를 0부터 N 전까지 반복:
        for(int i = 0; i < N; i++) {
            //       4-1. 다음도시가 아직 방문하지 않았는지 확인 (visited의 해당 비트가 0인지)
            if((visited & (1 << i)) == 0) {
                //       4-2. 현재도시 -> 다음도시로 가는 길이 있는지 확인 (cost 값이 0이 아닌지)
                if(cost[here][i] != 0) {
                    //       4-3. 둘 다 만족하면:
                    //              다음 상태로 재귀호출: dfs(다음도시, visited에 다음도시 비트 추가)
                    //              그 결과 + (현재도시->다음도시 비용) 을 후보값으로 계산
                    //              최소값을 후보값과 비교해서 더 작은 값으로 갱신
                    int result = dfs(i, visited | 1 << i) + cost[here][i];
                    minResult = Math.min(minResult, result);
                }
            }
        }

        //5. dp[현재도시][visited]에 최소값 저장
        dp[here][visited] = minResult;


        //6. 최소값 반환
        return minResult;
    }

}
