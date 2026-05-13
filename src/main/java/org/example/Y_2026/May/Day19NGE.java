package org.example.Y_2026.May;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Day19 오큰차
 *
 * 크기가 N인 수열 A가 주어졌을 때, 각 원소 A[i]의 오큰수(NGE)를 구하시오.
 * 오큰수란 A[i]보다 오른쪽에 있으면서 A[i]보다 큰 수 중 가장 왼쪽에 있는 수이다.
 * 없으면 -1을 출력한다.
 *
 * 순서를 중요시해야하므로 (정렬에 대한 기준을 만들면 안됨) Queue보단 Stack
 *
 *  N은 최대 100만 크기 -> scanner 보단 BufferedReader
 */
public class Day19NGE {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 첫 줄
        int N = Integer.parseInt(br.readLine());
        // 두 번째줄
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] A = new int[N];

        // 두 번째 줄의 배열을 하나씩 떼준다. 4 5 6 7 -> [4,5,6,7]
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        // 기준 값보다 큰 값을 인덱스를 스택에 담는다.
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[N];
        // 초기 값으로 -1을 담는다. -> 전부 없음 처리
        Arrays.fill(result, -1);

        for(int i = 0; i < N; i++) {
            // 스택 top의 값보다 현재 값이 크면 -> 오큰수 찾음
            // peek은 top값 확인 (인덱스)
            while (!stack.isEmpty() && A[stack.peek()] < A[i]) {
                // 오큰수 발견시 result 대기열 스택에서 왼쪽 값 제거 (오큰수 = 오른쪽 큰수)
                result[stack.pop()] = A[i];
            }

            // 입력된 배열값에 대한 오큰수를 못찾았을 경우 인덱스를 넣어준다.
            stack.push(i);
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < N; i++) {
            sb.append(result[i]);
            if (i < N - 1) sb.append(" ");
        }

        System.out.println(sb);
    }
}
