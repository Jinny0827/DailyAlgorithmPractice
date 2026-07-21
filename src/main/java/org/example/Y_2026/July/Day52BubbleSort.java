package org.example.Y_2026.July;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Day52 버블 소트
 *
 * N개의 수로 이루어진 수열 A(1~N이 중복 없이 존재)가 있다.
 * 이 수열을 버블 소트로 오름차순 정렬할 때, Swap이 총 몇 번 일어나는지 구하는 프로그램을 작성하시오.
 *
 * (버블 소트: 인접한 두 수를 비교해 앞이 뒤보다 크면 교환.
 * 첫 번째~두 번째, 두 번째~세 번째 … 순으로 끝까지 비교하는 것이 한 사이클. 정렬 완료까지 반복.)
 */
public class Day52BubbleSort {

    static long countSwap = 0;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // N개의 수열 받기
        int N = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[N];
        for(int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 직접 버블 소트(이중반복문 + swap)를 구현하면 로직은 맞지만 처리 시간때문에 타임아웃 가능성있음
        // 이론 상 swap 횟수는 배열의 역순쌍 개수와 같다.-> 버블소트를 돌리지 않고 병합 정렬 과정에서 역순쌍 갯수를 세는 방식으로 풀자
        // 역순쌍 = 배열에서 앞에 있는 수가 뒤에 있는 수보다 큰 쌍 예) 4,3 (4 > 3)

        mergeSort(arr, 0, N - 1);
        System.out.println(countSwap);
    }

    /**
     * 예시) 1,3,4,5,6,7,8
     * 처음 0, 6 들어가고 mid = 3
     * left >= right 되기 전까지
     * 1-1-1번 mergeSort가 0, 3 들어가서 mid = 1
     * 1-1-2번 mergeSort가 0, 1 들어가서 mid = 0
     * 1-1-3번 mergeSort가 0, 0 끝
     * 1-2-1번 mergeSort가 2, 3 들어가서 mid = 2
     * 1-2-2번 mergeSort가 3, 3 끝
     *
     *
     * 1번 끝나면 2번 시작
     * 2-1번 mergeSort가 4, 6 들어가서 mid = 5
     * 2-2번 mergeSort가 6, 6 끝
     */

    private static void mergeSort(int[] arr, int left, int right) {
        // 전체 쪼개고, 왼쪽/오른쪽 쪼개고 merge처리하는 순환 과정
        
        // 최소 값 ~ 최대 값 비교 및 배열 반으로 쪼개는 메서드
        if (left >= right) {
            return;
        }
        
        // 첫 값 + 마지막 값을 더해서 2분의 1로 중간 값 설정
        int mid = (left + right) / 2;

        // 첫 값 ~ 중간 값
        mergeSort(arr, left, mid);
        // 중간 다음 값 ~ 마지막 값
        mergeSort(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        // 최대 값 - 최소 값 + 1
        int[] temp = new int[right - left + 1];
        
        // 왼쪽 파트 시작 인덱스 0
        int i = left;

        // 오른쪽 파트 시작 인덱스 4
        int j = mid + 1;

        // temp 채울 위치
        int k = 0;

        // i번째 값이 j번째 값보다 작거나 같으면
        // 1,3,4,5,6,7,8 >>  0 <= 1 && 4 <= 6
        while(i <= mid && j <= right) {
            // 1 <= 8
            // 배열 내 앞, 뒤 숫자 비교
            if(arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                // 앞의 값이 커서 뒤로 넘어가는 스왑 과정 -> 카운트 추가
                countSwap += (mid - i + 1);
                temp[k++] = arr[j++];
            }
        }

        while(i <= mid) {
            temp[k++] = arr[i++];
        }

        while(j <= right) {
            temp[k++] = arr[j++];
        }

        for(int idx = 0; idx < k; idx++) {
            arr[left+idx] = temp[idx];
        }

    }

}
