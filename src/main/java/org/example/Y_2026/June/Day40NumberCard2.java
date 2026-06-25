package org.example.Y_2026.June;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Day 40 숫자 카드 2
 *
 * 숫자 카드 N개가 있다.
 * 정수 M개가 주어졌을 때, 이 수가 적힌 숫자 카드를 몇 개 가지고 있는지 구하라.
 */
public class Day40NumberCard2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // N개의 카드 갯수들
        int N = scanner.nextInt();

        Map<Integer, Integer> countN = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int card = scanner.nextInt();
            countN.put(card, countN.getOrDefault(card, 0) + 1);
        }

        // M개의 질문들
        int M = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < M; i++) {
            int query = scanner.nextInt();
            sb.append(countN.getOrDefault(query, 0));
            if (i < M - 1) {
                sb.append(" ");
            }
        }

        System.out.println(sb);

    }

}
