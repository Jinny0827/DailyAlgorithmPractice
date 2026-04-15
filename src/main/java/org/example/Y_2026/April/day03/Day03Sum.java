package org.example.Y_2026.April.day03;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 문제: 두 수의 합
 *
 *
 * n개의 서로 다른 양의 정수로 이루어진 수열이 있다.
 * 이 수열에서 크기가 양수인 두 원소 a[i]와 a[j] (i < j)의 합이 x가 되는 쌍의 개수를 구하라.
 *
 * 1 ≤ n ≤ 100,000
 * 1 ≤ 수열의 원소 ≤ 1,000,000 (모두 다름)
 * 1 ≤ x ≤ 2,000,000
 */
public class Day03Sum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 갯수
        int n = scanner.nextInt();
        
        // 수열
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // 합계 값
        int x = scanner.nextInt();

        // 투포인터 방식으로 처리 (수열 소트)
        Arrays.sort(a);

        int L = 0;
        int R = n - 1;
        int count = 0;

        while (L < R) {
            int sum = a[L] + a[R];
            if (sum == x) {
                count++;
                L++;
                R--;
            } else if (sum < x) {
                L++;
            } else {
                R--;
            }
        }

        System.out.println(count);

    }
}
