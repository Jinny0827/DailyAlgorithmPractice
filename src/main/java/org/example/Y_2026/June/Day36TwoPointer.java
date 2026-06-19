package org.example.Y_2026.June;

import java.time.Period;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Day36 용액 (Two Pointer)
 *
 * N개의 정수로 이루어진 용액이 있다.
 * 각 용액은 산성(양수) 또는 알칼리성(음수) 수치를 가진다.
 * 두 용액을 혼합했을 때 혼합 용액의 특성값이 0에 가장 가까운 두 용액을 찾아라.
 * 혼합 특성값 = 두 용액의 특성값 합
 */
public class Day36TwoPointer {

    // N개 용액 중 2개를 골라서 합산
    // 그 합이 0에 가장 가까운 쌍을 출력
    // 단, 같은 용액을 두 번 쓰면 안 됨

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();

        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = scanner.nextInt();
        }

        Arrays.sort(arr);
        int L = 0;
        int R = N - 1;

        // 합산했을 경우 가장 0에 근사한 값
        int minAbs = Integer.MAX_VALUE;
        // 정답 쌍의 왼쪽 값
        int ansL = 0;
        // 정답 쌍의 오른쪽 값
        int ansR = 0;

        while(L < R) {
            // 합산 값 int 범위 초과 대비하여 long 처리
            long sum = (long) arr[L] + arr[R];

            // 여기서 minAbs랑 비교해서 갱신
            if(Math.abs(sum) < minAbs) {
                minAbs = (int) Math.abs(sum);
                ansL = arr[L];
                ansR = arr[R];
            }

            // 반복을 위한 L / R 값에 대한 증감
            if (sum < 0) {
                L++;
            } else if (sum > 0) {
                R--;
            } else {
                break;
            }
        }

        System.out.println(ansL + " " + ansR);
    }



}
