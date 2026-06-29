package org.example.Y_2026.June;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Day42 신입 사원
 *
 * 신입사원 채용 시 서류와 면접 점수 두 가지 기준으로 평가한다.
 *
 * 어느 한 지원자보다 두 점수 모두 낮은 지원자는 탈락한다.
 * N명의 지원자가 있을 때, 합격자 수의 최대값을 구하라.
 *
 * 나보다 두 점수 모두 높은 사람이 단 한 명이라도 있으면 탈락
 */
public class Day42NewRecruit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 지원자 수
        int N = scanner.nextInt();

        // 순위를 입력값으로 받는다.
        int[][] ranking = new int[N][2];
        for(int i = 0; i < N; i++) {
            ranking[i][0] = scanner.nextInt();
            ranking[i][1] = scanner.nextInt();
        }

        // 서류 점수를 sort 하여 순위를 메기고
        // 그 뒤에 면접 점수를 비교한다.
        Arrays.sort(ranking, (a, b) -> a[0] - b[0]);
        
        int minInterview = ranking[0][1];
        int count = 1;

        // 서류 1등의 면접점수를 최저점으로 잡고 다른 지원자들의 면접점수를 확인
        // 서류 1등의 면접점수보다 높으면 통과
        for(int i = 1; i < N; i++) {
            if(ranking[i][1] < minInterview) {
                count++;
                minInterview = ranking[i][1];
            }
        }

        System.out.println(count);
    }
}
