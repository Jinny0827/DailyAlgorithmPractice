package org.example.Y_2026.April.day07;

import java.util.HashSet;
import java.util.Scanner;

/**
 * 정수 N개로 이루어진 수열 A가 있을 때, M개의 수 각각이 A에 존재하는지 확인하라.
 *
 * M개의 수에 대해, 수열 A에 존재하면 1, 없으면 0을 한 줄에 하나씩 출력
 */
public class Day07NumberArray {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashSet<Integer> set = new HashSet<>();

        StringBuilder sb = new StringBuilder();

        int N = scanner.nextInt();
        for (int i = 0; i < N; i++) {
            set.add(scanner.nextInt());
        }

        int M = scanner.nextInt();
        for (int i = 0; i < M; i++) {
            int num = scanner.nextInt();
            sb.append(set.contains(num) ? 1 : 0).append("\n");
        }
    }
}
