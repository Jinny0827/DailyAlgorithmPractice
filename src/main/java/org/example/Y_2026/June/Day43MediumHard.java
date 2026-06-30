package org.example.Y_2026.June;


import java.util.Scanner;

/**
 * Day43 Medium-Hard
 *
 * K개의 랜선을 잘라 N개를 만들려 한다.
 * 랜선을 자를 때 길이 L로 설정하면 각 랜선에서 랜선길이 / L 개를 얻는다.
 * 남은 부분은 버린다.
 * N개 이상 을 만들 수 있는 L의 최댓값을 구하라.
 *
 */
public class Day43MediumHard {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 현재 랜선의 갯수
        int K = scanner.nextInt();
        // 자르고 난 뒤 목표 갯수
        int N = scanner.nextInt();

        // 랜선 최대 길이가 2³¹-1 → int 범위 초과
        // 시작 값인 1 ~ 동적 최대 값 right 로 비교
        long right = 0;

        long[] arrayK = new long[K];
        for(int i = 0; i < K; i++) {
            arrayK[i] = scanner.nextLong();
            // 이진 탐색 최대 값 확인 (가장 긴 것이 기준)
            right = Math.max(right, arrayK[i]);
        }

        long left = 1;
        long answer = 0;

        while(left <= right) {
            // 최대 길이에서 반을 쪼개 기준 길이를 만들어준다.
            long mid = (left + right) / 2;

            // mid 길이로 잘랐을 때 총 개수 계산 (자른 후 더하여 비교)
            long count = 0;
            for (int i = 0; i < K; i++) {
                count += arrayK[i] / mid;
            }

            if(count >= N) {
                // 잘린 갯수가 N개를 넘기거나 같으면 목표 도달
                // answer를 값 변경 및 더 큰 L도 될 수 있으니 계속 탐색
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        System.out.println(answer);
    }

}
