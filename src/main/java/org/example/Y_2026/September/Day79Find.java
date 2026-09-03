package org.example.Y_2026.September;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Da79 찾기
 *
 * 문자열 매칭 (KMP) 개념
 * 1. 언제 쓰나? 큰 텍스트 안에서 특정 패턴 문자열이 나타나는 모든 위치를 빠르게 찾을 때
 * 2. 핵심 원리? 패턴 자체의 접두사/접미사가 겹치는 구간(실패함수, faiure function)을 미리 계산해 두고,
 * 이미 비교한 정보를 버리지 않고 실패 함수를 이용해 다음 비교 위치로 점프
 * 3. 시간 복잡도? O(N+M) (N: 텍스트 길이, M: 패턴 길이) -> 단순 완전탐색의 O(NxM)보다 훨씬 빠름
 *
 *
 * 문제 설명
 * 텍스트 T와 패턴 P가 주어질 때, T 안에서 P가 나타나는 모든 위치를 찾아라. (KMP 알고리즘 사용을 의도한 문제)
 *
 * 입력
 *
 * 첫째 줄: 텍스트 T (알파벳 대소문자, 공백 포함, 길이 1 ≤ |T| ≤ 1,000,000)
 * 둘째 줄: 패턴 P (알파벳 대소문자, 공백 포함, 길이 1 ≤ |P| ≤ 100,000, |P| ≤ |T|)
 *
 * 출력
 *
 * 첫째 줄: P가 T 안에 나타나는 횟수
 * 둘째 줄: P가 나타나는 위치들 (T의 첫 글자를 1로 시작, 오름차순, 공백 구분)
 */
public class Day79Find {

   static int[] fail;
   static String T;
   static String P;

   static List<Integer> positions = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 첫번째 줄 T 전체 단어(문장, 최대 백만개)
        T = br.readLine();

        // 두번째 줄 찾을 단어 P
        P = br.readLine();

        // 실패 함수 만들기 (P 자기 자신 분석)
        fail = failure();
        
        // P와 T 본격 비교함수
        search();

        System.out.println(positions.size());
        StringBuilder sb = new StringBuilder();
        for(int pos : positions) {
            sb.append(pos).append(' ');
        }

        System.out.println(sb.toString().trim());
    }

    private static int[] failure() {
        // P 단어의 접두사 접미사 조건 조사를 통해 얼마나 겹치는지 확인
        
        // 매칭시킬 단어의 길이로 칸수 지정
        fail = new int[P.length()];
        // "지금까지 접두사=접미사로 매칭된 길이"를 가리키는 포인터
        int j = 0;

        for(int i = 1; i < P.length(); i++) {
            // P[i]와 P[j]가 비교하면서 j를 조정
            // 다르면 -> j가 0보다 큰 동안 j = fail[j-1]로 돌아가기
            // 반복하며 재비교한다 -> 계속 값을 줄여나감
            while(j > 0 && P.charAt(i) != P.charAt(j)) {
                // 0에서 1을 빼도 0이된다. 그 아래공간이 없기 때문
                j = fail[j - 1];
            }

            // 같으면 -> j++
            if(P.charAt(i) == P.charAt(j)) {
                j++;
            }

            // fail[i] = j
            fail[i] = j;
        }

        return fail;
    }

    private static void search() {
        int j = 0;

        for(int i = 0; i < T.length(); i++) {
            // T[i]와 P[j]를 비교하면서 j 조정 (1단계와 동일한 while문)

            // T의 인덱스 문자와 P의 인덱스 문자가 같지 않으면 j를 줄인다. -> 기초값 j는 처음에 못탐
            // 반복하며 재비교한다 -> 계속 값을 줄여나감
            while(j > 0 && T.charAt(i) != P.charAt(j)) {
                j = fail[j - 1];
            }

            // i번째 T와 j번쨰 P가 같으면 j 위치 늘린다.
            if(T.charAt(i) == P.charAt(j)) {
                j++;
            }

            // 완전히 매칭되었을 때
            // P의 모든 글자가 다 매칭됐다 = P 전체가 T 안에서 통째로 발견됐다
            if(j == P.length()) {
                // 위치 기록
                positions.add(i - P.length() + 2);

                // 그 다음 이어서 탐색하기 위해 j 조정
                j = fail[j - 1];
            }
        }
    }

}
