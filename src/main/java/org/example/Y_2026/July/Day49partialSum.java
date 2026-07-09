package org.example.Y_2026.July;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Day49 부분합
 *
 * 길이 N인 수열에서,
 * 부분합이 S 이상이 되는 연속 부분수열 중 길이가 최소인 것을 구하라.
 *
 */
public class Day49partialSum {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 수의 갯수
        int N = Integer.parseInt(st.nextToken());
        
        // 기준 부분 합
        int S = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int start = 0;
        int end = 0;
        int sum = 0;
        // 최소 길이 담을 변수 -> 무한 값으로 담고 변화시킨다?
        int minLength = Integer.MAX_VALUE;

        // 끝값을 하나씩 늘려가며
        // 수열 내 1번 값 -> S가 맞춰지는 값까지 더해주고 비교
        for (end = 0; end < N; end++) {
            // 한번 시작 -> 끝 값 더하고
            sum += arr[end];

            // S보다 크거나 같은지 비교 (멈춰야 하므로)
            while(sum >= S) {
                // 최소길이가 무한 / 끝값 - 시작값 + 1의 연산 값
                minLength = Math.min(minLength, end - start + 1);

                // 다음 시작값 기준으로 sum 값을 계산하기 위해 start 값을 빼준다.
                sum -= arr[start];
                // 시작 값을 늘리고 S보다 모자라면 더해주는 방식으로 진행
                start++;
            }
        }
        
        // 최소 길이가 못구해서 여전히 무한 값이면 0으로 처리 아니면 최소 길이
        System.out.println(minLength == Integer.MAX_VALUE ? 0 : minLength);
    }

}
