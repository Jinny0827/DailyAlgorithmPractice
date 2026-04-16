package org.example.Y_2026.April.day04;

import java.util.Scanner;

/**
 * 수열
 *
 * 매일 측정한 온도가 N일치 주어진다.
 * 연속된 K일의 온도 합이
 * 최대가 되는 값을 구하라. (연속 3일의 조건)
 *
 * 2 ≤ N ≤ 100,000
 * 1 ≤ K ≤ N
 * 각 날의 온도: -100 ≤ t ≤ 100
 */
public class Day04NumericArray {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 총 측정일
        int N = scanner.nextInt();

        // 타겟 (해당일까지의 합계의 최대 값)
        int K = scanner.nextInt();

        // 온도 배열
        int[] temp = new int[N];
        for (int i = 0; i < N; i++) {
            temp[i] = scanner.nextInt();
        }

        // K일치 만큼의 온도 합과 최대 값 계산
        // [3, -2, -4, -9, 0, 3, 7]
        // 슬라이딩 윈도우 방식
        // 1. 3 -2 -4 = -3
        // 2. -2 -4 -9 = -15
        // -3 이라는 첫번째 덧셈값에서 첫 값인 3을 빼주고 -9를 더해준다.
        // -3 -3 -9 = -15
        int sum = 0;
        
        // 첫 K의 합
        for (int i = 0; i < K; i++) {
            // 1, 2, 3 합계값
            sum += temp[i];
        }

        // 첫 합을 max 초기값으로
        int max = sum;

        for (int i = K; i < N; i++) {
            sum += temp[i];
            sum -= temp[i - K];
            max = Math.max(max, sum);
        }

        System.out.println(max);
    }

}
