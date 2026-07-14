package org.example.Y_2026.July;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Day49 히스토그램에서 가장 큰 직사각형
 *
 * 히스토그램을 이루는 직사각형들의 높이가 왼쪽부터 순서대로 주어진다.
 * 각 직사각형의 폭은 1이다.
 * 이 히스토그램 안에 완전히 포함되는 가장 넓은 직사각형의 넓이를 구하라.
 *
 * 입력
 * -> 여러 테스트 케이스가 주어지며, 각 줄은 n h1 h2 ... hn 형태 (n: 직사각형 개수, hi: 높이)
 * -> n = 0이 입력되면 종료
 *
 * 출력
 * -> 각 테스트 케이스마다 최대 직사각형 넓이를 한 줄에 출력
 */
public class Day50HistogramOfRectangle {

    // 예) [2,1,4,5,1,3,3]에서
    //
    // 인덱스 2~3 구간 [4,5] → 최저높이 4, 폭 2 → 넓이 8 ← 이게 정답
    // 인덱스 5~6 구간 [3,3] → 최저높이 3, 폭 2 → 넓이 6
    // 전체 [2,1,4,5,1,3,3] → 최저높이 1, 폭 7 → 넓이 7
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        // 막대 갯수, (갯수만큼) 2,1,3, ... 형식으로 수열이 여러개 들어옴 (1개일 수도 있고)
        String line;
        while((line = br.readLine()) != null) {
            // 들어온 수열에서 첫 값은 막대 갯수 -> 갯수 확인하여 종료 조건 통과
            StringTokenizer st = new StringTokenizer(line);
            int n = Integer.parseInt(st.nextToken());
            // 종료 조건
            if (n == 0) {
                break;
            }

            // 두번째 수열 값부터 heights에 넣어 막대 그래프 구성
            // Step 1: 높이 배열 끝에 0을 추가 (sentinel)
            // → 이렇게 하면 반복이 끝날 때 스택에 남은 막대들을 강제로 다 꺼내서 계산할 수 있음
            long[] heights = new long[n + 1];
            for (int i = 0; i < n; i++) {
                heights[i] = Long.parseLong(st.nextToken());
            }
            heights[n] = 0;
            
            // Step 2: 인덱스를 저장하는 스택, 스택 안 높이는 항상 오름차순
            Deque<Integer> stack = new ArrayDeque<>();
            long maxArea = 0;

            // 예) heights = [2, 1, 4, 5, 1, 3, 3, 0]
            for(int i = 0; i <= n; i++) {
                // 스택이 비어있으면 stack에 push = 첫 인덱스 push

                // Step 3: 현재 막대가 스택 top 막대보다 낮거나 같으면
                // top 막대는 더이상 오른쪽으로 확장 불가 -> pop해서 넓이 확정
                // peek = 가장 위 element를 반환
                // stack의 가장 최근 값부터 꺼내서 heights의 i번째 값과 비교하면서 반복
                while(!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                    // 가장 위에 있는 element를 제거 = 값 꺼내오기
                    long h = heights[stack.pop()];

                    // Step 4: 폭 계산
                    // 첫 값은 팝되고 나서 stack이 empty 상태
                    // 왼쪽 경계 = 스택에 남은 다음 인덱스 (없으면 맨 처음부터)
                    // 오른쪽 경계 = 현재 i (직전까지)
                    // -1을 하는 이유는 우측 경계 i와 좌측 경계 stack.peek이 유효구간에 포함되지 않기 때문
                    long width = stack.isEmpty() ? i : i - stack.peek() - 1;

                    // Step 5: 넓이 계산 후 최댓값 갱신
                    maxArea = Math.max(maxArea, h * width);
                }

                // Step 6: 현재 인덱스를 스택에 push
                stack.push(i);
            }

            sb.append(maxArea).append('\n');
        }

        System.out.println(sb);
    }


}
