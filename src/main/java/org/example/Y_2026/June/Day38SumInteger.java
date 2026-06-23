package org.example.Y_2026.June;

import java.util.Scanner;

/**
 *  Day38 수들의 합
 *
 * 서로 다른 N개의 자연수의 합이 S가 될 때, N의 최댓값을 구하라.
 * -> N중 최고 값이 아닌 몇 개의 N개가 필요한지 출력
 * 
 */
public class Day38SumInteger {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // 최대 몇 개의 서로 다른 자연수로 S값을 만들수 있냐는 것
        long S = scanner.nextLong();

        long sum = 0;
        long count = 0;

        for(long i = 1; sum + i <= S; i++) {
            sum += i;
            count++;
        }

        System.out.println(count);

    }
}
