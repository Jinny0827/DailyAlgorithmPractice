package org.example.Y_2026.May;

import java.util.Scanner;

/**
 * Day25 분할정복 (미출제)
 *
 * 크기가 2ᴺ × 2ᴺ인 2차원 배열을 Z 모양으로 순서대로 방문한다.
 * N > 1일 때, 배열을 4등분한 후 각 부분을 Z 순서로 재귀적으로 방문한다.
 *
 * 1. 어느 사분면인지 판단 → 시작 번호 더하기
 * 2. 해당 사분면 안에서 같은 방식으로 재귀
 */
public class Day25SubjugationOfDivision {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 배열의 크기 결정 int[N + 1];
        int N = scanner.nextInt();

        // 행/열
        int r = scanner.nextInt();
        int c = scanner.nextInt();

        System.out.println(dfs((int)Math.pow(2, N), r, c));

    }

    static long dfs(int N, int r, int c) {
        // n : 배열의 한변 길이 (정사각배열), (r, c) : 찾는 위치
        // 4사분면의 한면 크기  = n/2 -> 한 사분면의 크기는 n/2 * n/2
        // 예) N = 6 이면 3 * 3 크기의 한 개의 4사분면 예상
        // r, c의 위치 파악은 4사분면은 한 면의 2등분이기때문에 그 숫자에 대해서 N/2으로 계산해서 위치 파악

        if (N == 1) {
            return 0;
        }

        // 한 사분면의 한 면
        int half = N / 2;
        // 한 사분면의 크기
        long size = (long) half * half;

        if (r < half && c < half) {
            // 1사분면
            return 0 * size + dfs(half, r, c);
        } else if (r < half && c >= half) {
            // 2사분면 (c(열)이 하프 지점을 지난 2사분면)
            return 1 * size + dfs(half, r, c - half);
        }else if (r >= half && c < half) {
            // 3사분면 (r(행)이 하프 지점을 지났을때의 3사분면)
            return 2 * size + dfs(half, r - half, c);
        } else {
            // 4사분면 (r(행)이 하프 지점을 지나고 c(열)이 하프 지점을 지난 4사분면)
            return 3 * size + dfs(half, r - half, c - half);
        }

    }

}
