package org.example.Y_2026.May;


import java.util.Scanner;

/**
 * Day 17 구간 합 구하기 4
 *
 * 수 N개가 주어졌을 때,
 * i번째 수부터 j번째 수까지의 합을 구하는 프로그램을 작성하시오.
 * 단, M개의 쿼리가 주어지며 각 쿼리마다 구간 합을 출력해야 한다.
 */
public class Day17PrefixSum {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        int N = scanner.nextInt();
        int M = scanner.nextInt();

        int[] a = new int[N + 1];
        int[] sum = new int[N + 1];

        // a 공간의 원소들을 미리 더해서 원소들의 덧셈을 구해서 배열에 저장해둔다.
        for (int i = 1; i <= N; i++) {
            a[i] = scanner.nextInt();
            // 1을 뺀 이유는 i가 1부터 시작중이므로 (배열 인덱스는 0부터 시작)
            sum[i] = sum[i-1] + a[i];
        }
        
        // i ~ j 구간의 덧셈에 대한 요청을 여러번 보낼 예정(M번(쿼리))
        for(int x = 0; x < M; x++) {
            int i = scanner.nextInt();
            int j = scanner.nextInt();

            sb.append(sum[j] - sum[i-1]).append("\n");
        }

        /**
         * a[]   = [_, 5, 4, 3, 2, 1]
         * sum[] = [_, 5, 9, 12, 14, 15]
         *
         * 쿼리 1→3 : sum[3] - sum[0] = 12 - 0 = 12
         * 쿼리 2→4 : sum[4] - sum[1] = 14 - 5  = 9
         * 쿼리 5→5 : sum[5] - sum[4] = 15 - 14 = 1
         */
        System.out.println(sb);
    }


}
