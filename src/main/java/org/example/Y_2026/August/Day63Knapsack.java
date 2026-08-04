package org.example.Y_2026.August;

import java.time.Period;
import java.util.*;

/**
 * Day63 냅색문제(투포인터)
 *
 * N개의 물건이 있고 각각 무게가 있다.
 * 이 중에서 무게의 합이 C 이하가 되도록 물건을 고르는 부분집합(공집합 포함)의 개수를 구하여라.
 *
 * 1 ≤ N ≤ 30
 * 1 ≤ C ≤ 1,000,000,000
 * 각 물건 무게 ≤ 1,000,000,000
 * 시간제한 통과하려면 O(2^N) 완전탐색 불가 → N=30이라 2^15 씩 나눠서 처리하는 접근 필요
 */
public class Day63Knapsack {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int C = scanner.nextInt();

        int[] weight = new int[N];
        for(int i = 0; i < N; i++) {
            weight[i] = scanner.nextInt();
        }

        // 비트마스크로 부분집합 합을 구하는 방법 사용
        // weight를 반으로 나눠서 포인터 그룹 두 개 만든다.
        //half = N을 2로 나눈 값 (정수 나눗셈)
        int half = N / 2;
        //a배열 = weight의 0번부터 half번 전까지
        int[] a = Arrays.copyOfRange(weight, 0, half);
        //b배열 = weight의 half번부터 끝까지
        int[] b = Arrays.copyOfRange(weight, half, N);

        //aSums = getSums(a배열) 호출
        List<Long> aSums = getSums(a);
        //bSums = getSums(b배열) 호출
        List<Long> bSums = getSums(b);

        //aSums 정렬
        Collections.sort(aSums);

        //bSums 정렬
        Collections.sort(bSums);

        // 각 그룹에서 만들 수 있는 모든 부분집합의 합을 구해서 리스트에 저장
        int count = 0;
        int left = 0;
        int right = bSums.size() - 1;

        //left가 aSums 끝에 도달할 때까지, 그리고 right가 0 이상인 동안 반복:
        // aSums가 정렬되어 있으므로 a값 하나를 고정해서 b값이 얼마 이하여야 C를 안넘기는지 정해진다.
        // b값 상한이 커질수록 쓸 수 있는 b후보도 늘어나니까, a를 작은 값부터 오름차순으로 보면서 b는 반대로 큰값부터 좁혀가는 방식 (투포인터)
        while (left < aSums.size() && right >= 0) {
            //    만약 aSums[left] + bSums[right] <= C:
            if(aSums.get(left) + bSums.get(right) <= C) {
                //        (aSums[left]는 bSums[0]부터 bSums[right]까지 전부와 짝지어도 C 이하)
                //        count에 (right + 1)을 더한다
                //        left를 1 증가
                count += right + 1;
                left++;
            } else {
                //    아니면:
                //        right를 1 감소
                right--;
            }
        }
        // count 반환
        System.out.println(count);
    }

    // mask는 0부터 2^n-1을 도는 정수 -> 이 mask를 이진수로 보면 각 자리가 이 원소를 포함하냐 안하냐로 나타냄
    // mask 하나가 부분집한 하나에 1:1 대응된다. -> mask는 부분집합의 합 하나 하나
    // 함수 getSums(정수배열 arr) -> Long 리스트 반환:
    private static List<Long> getSums(int[] arr) {
        // n = arr의 길이
        int n = arr.length;
        // 결과를 담을 리스트 sums 생성 (비어있음)
        List<Long> sums = new ArrayList<>();

        // mask를 0부터 (2의 n제곱 / 자바에서는 1 << n으로 표현 (비트 이동))까지 반복
        // 예시( arr로 6,4,7 이 들어온다는 가정) -> n = 3, 1 << n은 3의 이진수인 001을 n칸(3칸) 이동 001000 = 8
        for(int mask = 0; mask < (1 << n); mask++) {
            // sum = 0  (현재 mask가 나타내는 부분집합의 합)
            long sum = 0;

            // i를 0부터 n-1까지 반복:
            // 예시( mask = 5 ) -> i가 n개(3개 -> 0,1,2)로 바뀔때마다 비트하나만 켠 숫자를 만든다 001 -> 010 -> 100
            for(int i = 0; i < n; i++) {
                // 만약 mask의 i번째 비트가 1이면
                // mask = 5 는 101 -> 001,010,100을 넣어가며 같은 자리 숫자가 있는지 확인 예) 101,001 있음, 101,010 없음
                if((mask & ( 1 << i )) != 0) {
                    // sum에 arr[i]를 더한다
                    sum += arr[i];
                }
            }

            // sum을 sums 리스트에 추가
            sums.add(sum);
        }
        // sums 리스트 반환

        return sums;
    }

}
