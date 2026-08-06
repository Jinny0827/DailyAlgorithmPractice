package org.example.Y_2026.August;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Day64 최솟값 구하기
 *
 * N개의 수 A1, A2, ..., AN과 정수 L이 주어진다.
 * Di를 Ai-L+1 ~ Ai 중 최솟값이라 정의한다. 단, i-L+1 < 1이면 A1부터 Ai까지의 최솟값으로 한다. D1, D2, ..., DN을 구하라.
 *
 * 입력 >>
 * 첫째 줄: N, L (1 ≤ L ≤ N ≤ 5,000,000)
 * 둘째 줄: Ai (-10^9 ≤ Ai ≤ 10^9), N개
 *
 * 출력 >>
 * D1부터 DN까지 공백으로 구분하여 출력
 * 
 */
public class Day64SlidingWindow {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());

        int[] A = new int[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        int[] result = slidingWindow(A, N, L);
        for(int i = 0; i < N; i++) {
            sb.append(result[i]).append(" ");
        }

        System.out.println(sb);

    }

    private static int[] slidingWindow(int[] A, int N, int L) {
        // 단조덱 사용
        // 덱(인덱스 저장용)과 결과 배열 D를 준비한다.
        int[] deck  = new int[N];
        // 실제 원소가 있는 범위 (head,tail)
        int head = 0;
        int tail = -1;
        int[] D = new int[N];

        // i를 0부터 N-1까지 순회하며 아래를 반복한다.
        for(int i = 0; i < N; i++) {
            
            // 덱이 비어있지 않고, 덱 맨 뒤 인덱스의 값이 A[i]보다 크거나 같으면 → 맨 뒤를 계속 제거한다.
            while(head <= tail && A[deck[tail]] >= A[i]) {
                // 맨뒤인덱스가 앞인덱스보다 크고 맨뒤인덱스값이이 현재인덱스값보다 크면 맨뒤인덱스의 범위를 줄여준다. -> 맨뒤인덱스를 작은값으로 이동
                tail--;
            }

            // 현재 인덱스 i를 덱 맨 뒤에 추가한다.
            // 현재 인덱스가 L의 범위를 넘지 않을 때 맨뒤덱 인덱스 위치를 한칸뒤로 이동 -> 맨뒤인덱스 큰값으로 이동
            deck[++tail] = i;

            // 덱 맨 앞 인덱스가 윈도우 범위(i-L)보다 작거나 같으면(범위 밖이면) → 맨 앞을 제거한다.
            // 혹은 L의 범위에 도달 못했다면 범위 도달까지 head값 추가
            if(deck[head] <= i - L) {
                // 맨앞인덱스가 현인덱스 - 인덱스범위값보다 작으면 맨앞 인덱스를 한칸 앞으로 이동 -> 맨앞인덱스 큰값으로 이동
                head++;
            }

            // D[i] = A[맨앞인덱스] 로 저장한다.
            D[i] = A[deck[head]];
        }

        // 순회가 끝나면 D 배열이 정답이다.
        return D;
    }

}
