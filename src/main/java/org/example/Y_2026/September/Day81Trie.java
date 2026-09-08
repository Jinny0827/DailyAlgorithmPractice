package org.example.Y_2026.September;


import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Day 81 Trie
 *
 * 새 주제: Trie
 * - 언제 사용? 
 * 여러 문자열의 공통 접두사를 빠르게 찾거나 자동완성, 사전 검색을 해야할 때 사용
 *
 * - 동작 원리
 * 각 노드가 문자 하나를 의미하는 트리로 문자열들을 삽입하면 공통 접두사가 자연스럽게 하나의 경로로 합쳐짐
 *
 * - 시간복잡도
 * 삽입/검색 O(L)(L = 문자열 길이), 전체 구성은 O(전체 문자 수)
 *
 *
 * 문제 설명
 * 스마트 자판 모듈이 있다. 사용자가 단어를 입력할 때 규칙은 다음과 같다.
 *
 * 1. 첫 글자는 무조건 사용자가 직접 눌러야 한다.
 * 2. 이후, 지금까지 입력한 접두사로 시작하는 사전 속 단어들이 전부 "같은 다음 글자" 하나로만 이어진다면, 모듈이 그 글자를 자동으로 입력해준다.
 * 3. 그렇지 않고 다음 글자가 여러 가지로 갈릴 수 있다면(또는 그 단어가 여기서 끝난다면), 사용자가 직접 버튼을 눌러야 한다.
 *
 * 주어진 사전(단어 목록)에 대해, 사전의 모든 단어를 입력하는 데 필요한 평균 버튼 입력 횟수를 구하라.
 *
 * 입출력 예시
 *
 * 입력:
 * 4
 * hello
 * hell
 * heaven
 * goodbye
 *
 * 출력:
 * 2.00
 *
 * (설명: hello=3번, hell=2번, heaven=2번, goodbye=1번 → 평균 (3+2+2+1)/4 = 2.00)
 *
 * 제한 조건
 *
 * 1. 여러 개의 테스트 케이스가 주어지며 입력 끝(EOF)까지 반복
 * 2. 각 테스트 케이스: 단어 개수 N (1 ≤ N ≤ 100,000)
 * 3. 각 단어는 소문자 알파벳으로만 구성, 길이 1~80
 * 4. 한 사전 내에 같은 단어는 중복되지 않음
 * 5. 결과는 소수점 둘째 자리까지 출력
 */
public class Day81Trie {
    // 단어의 위치에 따라 노드의 차수에 집어넣음
    // hello, goodbye면 1차수는 h,g 그리고 2차수는 e,o
    

    // 입력받을 단어 갯수
    static int N;

    // trie[노드번호][문자인덱스] = 다음 노드 번호 (0번 없음)
    static int[][] trie;

    // 각 노드의 자식 갯수
    static int[] childCnt;

    // 이 노드에서 끝나는 단어가 있는지
    static boolean[] isEnd;

    // 지금까지 만든 노드의 개수 (0번은 루트, 새 노드는 1번부터)
    static int nodeCnt;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //  들어오는 테스트 케이스에 대한 반복처리
        String line;
        while((line = br.readLine()) != null ){
            N = Integer.parseInt(line.trim());

            // 전역 배열 초기화 위해 각 단어별 알파벳 갯수(길이) 초기화
            String[] words = new String[N];
            int totalLen = 0;

            for(int i = 0; i < N; i++) {
                words[i] = br.readLine().trim();
                totalLen += words[i].length();
            }

            // 전역 배열 초기화
            trie = new int[totalLen + 1][26];
            childCnt = new int[totalLen + 1];
            isEnd = new boolean[totalLen + 1];
            nodeCnt = 0;

            for (int i = 0; i < N; i++) {
                insert(words[i]);
            }

            long totalPress = 0;
            for (int i = 0; i < N; i++) {
                totalPress += count(words[i]);
            }

            double avg = (double) totalPress / N;
            System.out.printf("%.2f%n", avg);
        }
    }

    private static void insert(String word) {
        //1. cur = 0으로 시작 (루트)
        int cur = 0;

        //2. 단어의 글자를 하나씩 순회 (for (char c : word.toCharArray()))
        for(char c : word.toCharArray()) {
            //3. 현재 글자의 인덱스 계산 (c - 'a')
            int idx = c - 'a';

            //4. 자식이 없으면 (trie[cur][idx] == 0) → 새 노드 번호를 배정하고(nodeCnt 증가) trie[cur][idx]에 저장, childCnt[cur]도 1 증가
            if(trie[cur][idx] == 0) {
                trie[cur][idx] = ++nodeCnt;
                childCnt[cur]++;
            }

            //5. cur을 방금 확인한 자식 노드로 이동
            cur = trie[cur][idx];
        }

        //6. 단어를 다 돌고 나면, 마지막에 서 있는 노드에 isEnd = true 표시
        isEnd[cur] = true;
    }

    private static int count(String word) {
        //1. cur = 0으로 시작 (루트)
        int cur = 0;

        //2. press = 1로 초기화 (첫 글자는 무조건 수동)
        int press = 1;

        //3. word를 인덱스 i로 순회 (for (int i = 0; i < word.length(); i++))
        for(int i = 0; i < word.length(); i++) {
            //4. 현재 글자의 인덱스 계산 (word.charAt(i) - 'a')
            int idx = word.charAt(i) - 'a';

            //5. next = trie[cur][idx] → 방금 글자를 타고 내려간 노드
            int next = trie[cur][idx];

            //6. 만약 i가 마지막 글자가 아니라면 (i != word.length() - 1):
            if (i != word.length() - 1) {
                //   childCnt[next] > 1 이거나 isEnd[next] == true 이면 press += 1
               if(childCnt[next] > 1 || isEnd[next]) {
                   press += 1;
               }
            }

            //7. cur = next (다음 반복을 위해 이동)
            cur = next;
        }

        //8. 반복 끝나면 press 리턴
        return press;
    }

}
