package org.example.Y_2026.August;

import java.util.Scanner;

/**
 * Day69 좋은 수열
 *
 * 1, 2, 3으로만 이루어진 길이 N인 수열이 있다.
 * 이 수열이 "나쁜 수열"이 되려면, 어떤 i ≤ j < k가 존재해서 xi, xi+1, ..., xj 와 xj+1, ..., xk 가 완전히 동일한 부분수열이어야 한다
 * (같은 패턴이 연속으로 반복됨).
 *
 * 나쁜 수열이 아닌 수열을 "좋은 수열"이라고 한다.
 * N이 주어졌을 때, 길이 N인 좋은 수열 중 사전순으로 가장 작은 것을 구하시오.
 *
 * 입력: 4
 * 출력: 1213
 */
public class Day69GoodSequence {

    static int N;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        // 수열의 갯수 입력
        N = scanner.nextInt();
        
        // N개의 수를 담을 배열 변수
        int[] sequence = new int[N];
        
        // 0번 자리부터 채우기 시작
        backTracking(sequence, 0);

        for(int i = 0; i < N; i++) {
            sb.append(sequence[i]);
        }

        System.out.println(sb);
    }

    private static boolean backTracking(int[] sequence, int index) {
        if(index == N) {
            return true;
        }

        for(int num = 1; num <= 3; num++) {
            sequence[index] = num;

            if(isGood(sequence, index) && backTracking(sequence, index + 1)) {
                return true;
            }
        }

        return false;
    }

    // 순간 배열 스냅샷 판단 함수
    private static boolean isGood(int[] sequence, int endIndex) {
        int length  = endIndex + 1;

        for (int half = 1; half <= length / 2; half++) {
            // 뒤에서부터 half 길이씩 두 블록 비교
            boolean same = true;
            for(int offset = 0; offset < half; offset++) {
                if(sequence[endIndex - offset] != sequence[endIndex - half - offset]) {
                    same = false;
                    break;
                }
            }

            if(same) {
                return false;
            }
        }

        return true;
    }

}
