package org.example.Y_2026.July;

import java.util.Scanner;

/**
 * Day 47 평범한 배낭
 *
 * 준서는 여행에 필요한 물건을 최대한 많이 가져가려고 합니다. 준서가 가진 배낭은 최대 무게 K까지 넣을 수 있습니다.
 * 준서가 가지고 있는 물건은 N개이며, 각 물건은 무게 W와 가치 V를 가집니다. 어떤 물건을 배낭에 넣으면 그 물건의 가치만큼 배낭에 넣은 물건의 가치 합이 증가합니다.
 * 준서가 배낭에 넣을 수 있는 물건들의 가치의 합의 최댓값을 구하세요.
 * 
 */
public class Day47OrdinaryBackPack {

    // N K (물건 개수, 배낭 최대 무게) -> N개의 줄에 대한 W, V를 입력받음(각 물건의 무게, 가치)
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
            
        // W (물건 개수)
        int N = scanner.nextInt();
        
        // V (배낭 최대 무게)
        int K = scanner.nextInt();

        // DP(동적계획법)로 접근
        // 각 물건의 무게 배열
        int[] W = new int[N + 1];
        // 각 물건의 가치 배열
        int[] V = new int[N + 1];

        for(int i = 1; i <= N; i++) {
            W[i] = scanner.nextInt();
            V[i] = scanner.nextInt();
        }

        // 물건의 갯수와 배낭 최대 무게를 기준으로 2차원 배열 생성
        // [현재물건인덱스][현재무게인덱스] = 값은 가치
        int[][] dp = new int [N + 1][K + 1];

        // 물건의 개수만큼
        for (int i = 1; i <= N; i++) {
            // 배낭의 최대 무게만큼 (무게 0~K까지 전부)
            for (int w = 0; w <= K; w++) {
                if(w < W[i]) {
                    // 4 < 6 투입 X
                    // 현재 확인중인 무게보다 이 물건의 무게가 크면 이전 값 그대로 가져온다. -> 못 넣음
                    dp[i][w] = dp[i-1][w];
                } else {
                    // 4 < 3 투입
                    // 현재 확인중인 무게보다 이 물건의 무게가 낮으면 최대값 확인 후 -> 넣을 수 있다.
                    dp[i][w] = Math.max(dp[i-1][w], dp[i-1][w - W[i]] + V[i]);
                }
            }
        }

        System.out.println(dp[N][K]);
    }

}
