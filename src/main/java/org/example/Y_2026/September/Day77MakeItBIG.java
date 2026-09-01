package org.example.Y_2026.September;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Day77 크게 만들기 (그리디)
 *
 * N의 숫자가 주어졌을 때, 이 중에서 숫자 K개를 제거했을 때 얻을 수 있는 가장 큰 수를 구하는 프로그램을 작성하세요.
 *
 * 입력:
 * 7 3
 * 1924313
 *
 * 출력:
 * 9433
 */
public class Day77MakeItBIG {

    public static void main(String[] args) throws Exception {
        // N개의 숫자를 받아 K개의 숫자를 빼서 최대의 값을 만들어야한다.
        // 예) 123456 에서 2개를 빼면 12를 빼야 3456으로 제일 큰 수가 됨 (현재의 숫자가 앞의 숫자가 크면 앞의 작은 숫자를 지운다.)

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // N (숫자의 갯수)
        int N = Integer.parseInt(st.nextToken());

        // K (삭제해야하는 숫자 갯수)
        int K = Integer.parseInt(st.nextToken());

        // 들어온 숫자
        st = new StringTokenizer(br.readLine());
        String number = st.nextToken();

        String result = solve(number, K);

        System.out.println(result);
    }

    private static String solve(String number, int K) {
        // 1. 결과를 담을 자료구조(스택 또는 StringBuilder)를 하나 준비한다.
        StringBuilder sb = new StringBuilder();

        // 2. number의 첫 글자부터 마지막 글자까지 순서대로 하나씩 확인한다.
        for(int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);

            // 3. 현재 글자를 확인할 때, 아래 조건을 모두 만족하는 동안 반복해서 결과의 마지막 글자를 제거한다.
            // 3-1. 결과가 비어있지 않다
            // 3-2. 결과의 마지막 글자가 현재 글자보다 작다
            // 3-3 .아직 K(제거 가능 횟수)가 남아있다
            while(sb.length() > 0 && c > sb.charAt(sb.length() - 1) && K > 0) {
                // 뒤 숫자가 커서 앞 숫자가 삭제된다는 가정
                // 예 123이면 2가 1보다 크므로 1을 삭제
                sb.deleteCharAt(sb.length() - 1);
                K--;
            }

            // 4. 위 반복이 끝나면(더 이상 제거할 게 없으면), 현재 글자를 결과 끝에 추가한다.
            sb.append(c);
        }

        // 6. 모든 글자를 다 확인했는데도 K가 남아있다면, 결과의 뒤쪽에서부터 K개를 제거한다.
        if(K > 0) {
            // sb.delete(a, b)는 인덱스 a부터 b-1까지의 문자를 잘라내는 메서드
            // a는 갯수, b-1은 위치
            sb.delete(sb.length() - K, sb.length());
        }

        // 7. 남은 결과를 문자열로 합쳐서 반환한다.
        return sb.toString();
    }

}
