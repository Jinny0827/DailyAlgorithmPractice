package org.example.Y_2026.April.day08;

import java.util.Scanner;

/**
 * 나무 자르기
 *
 * 1. 상근이는 나무를 필요한 만큼만 가져가려 한다.
 * 2. 절단기 높이를 H로 설정하면, H보다 높은 나무는 **(나무 높이 - H)**만큼 잘린다.
 * 3. 상근이가 M미터 이상의 나무를 가져가려 할 때, 절단기 높이의 최댓값을 구하라.
 */
public class Day08Cutter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 나무 갯수 (배열 길이)
        int N = scanner.nextInt();
        // 내가 필요한 나무 길이 (목표 값)
        int M = scanner.nextInt();

        // 나무 배열 공간
        // 나무 배열 내 최대 값 확인
        int[] trees = new int[N];
        int right = 0;
        for (int i = 0; i < N; i++) {
            trees[i] = scanner.nextInt();
            right = Math.max(right, trees[i]);
        }
        
        // 이진 탐색 (이진탐색 = 정렬된 범위에서 절반씩 줄여가며 값 찾기)
        int left = 0;
        int answer = 0;

        while(left <= right) {
            // H 후보 값
            int mid = (left + right) / 2;

            long sum = 0;
            for(int i = 0;i < N; i++) {
                if (trees[i] > mid) {
                    // 절단기보다 나무의 높이가 크면 자른 값(차액)을 더해준다.
                    sum += trees[i] - mid;
                }
            }

            // 내가 필요한 나무의 길이가 합계값보다 크면 후보 값을 answer에 배당
            if(sum >= M) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }

        }

        System.out.println(answer);
    }

}
