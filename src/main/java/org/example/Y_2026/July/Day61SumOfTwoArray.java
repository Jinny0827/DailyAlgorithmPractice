package org.example.Y_2026.July;

import java.time.Period;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Day61 두 배열의 합
 *
 * 길이 n인 배열 A와 길이 m인 배열 B가 주어진다.
 * A의 부분배열 합과 B의 부분배열 합을 더한 값이 T가 되는 (A의 부분배열, B의 부분배열) 쌍의 개수를 구하시오.
 */
public class Day61SumOfTwoArray {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 목적 합계 값
        int T = scanner.nextInt();

        // 첫번째 배열
        int N = scanner.nextInt();
        int[] A = new int[N];
        for(int i = 0; i < N; i++) {
            A[i] = scanner.nextInt();
        }

        // 두번째 배열
        int M = scanner.nextInt();
        int[] B = new int[M];
        for(int i = 0; i < M; i++) {
            B[i] = scanner.nextInt();
        }

        // 부분 배열 합을 전부 구한 다음 이분 탐색으로 짝을 찾는 방식
        //1. sumA = A의 모든 부분배열 합을 담을 리스트
        // A의 부분 배열 사이즈는 총 갯수의 N - i개의 합 => 1부터 N까지의 합은 N(N+1)/2
        // 예시) N=4면 4+3+2+1=10, 공식으로도 4*5/2=10
        int sizeA = N * (N + 1) / 2;
        int[] sumA = new int[sizeA];
        int idxA = 0;
        // 앞 숫자를
        for (int i = 0; i < N; i++) {
            int sum = 0;
            for(int j = i; j < N; j++) {
                // A의 배열끼리 더해주는 형태 (1번, 2번 덧셈)
                // 더해주면서 배열에 하나씩 넣어줌
                sum += A[j];
                sumA[idxA++] = sum;
            }
        }

        //2. sumB = B의 모든 부분배열 합을 담을 리스트 (같은 방식)
        int sizeB = M * (M + 1) / 2;
        int[] sumB = new int[sizeB];
        int idxB = 0;
        for(int i = 0; i < M; i++) {
            int sum = 0;
            for(int j = i; j < M; j++) {
                sum += B[j];
                sumB[idxB++] = sum;
            }
        }

        // 3. sumB를 정렬한다 + 이분 탐색
        Arrays.sort(sumB);
        // T - A의 합계값을 빼서 sumB에 존재하는지 확인하는 방식
        int count = 0;
        for(int i = 0; i < sumA.length; i++) {
           int target = T - sumA[i];
           count += countOccurrences(sumB, target);
        }

        //5. count 출력
        System.out.println(count);
    }

    private static int countOccurrences(int[] sumB, int target) {
        // 정렬된 배열에서 절반씩 탐색 범위를 줄여가며 원하는 값을 찾는 방법 
        // target 값이 sumB를 반으로 쪼갰을때 앞이냐 뒤냐에 따라 계산 횟수가 절반 줄어듬
        int lower = lowerBound(sumB, target);
        int upper = upperBound(sumB, target);

        return upper - lower;
    }

    private static int lowerBound(int[] sumB, int target) {
        // target이 처음 등장할 수 있는 위치
        int left = 0;
        int right = sumB.length;
        while(left < right) {
            int mid = 0;
            mid = (left + right) / 2;
            if(sumB[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    private static int upperBound(int[] sumB, int target) {
        // target보다 큰 값이 처음 등장하는 위치
        int left = 0;
        int right = sumB.length;

        while(left < right) {
            int mid = 0;
            mid = (left + right) / 2;
            if(sumB[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

}
