package org.example.Y_2026.September;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Day80 문자열 집합 판별
 *
 * 아호-코라식
 *
 * - 언제 사용?
 * 여러개의 문자 패턴을 미리 등록해놓고, 긴 텍스트 하나를 한 번 훑으면서 그 패턴들이 어디에 등장하는지 (또는 등장 여부) 빠르게 판별시 사용
 * 
 * - 핵심 동작 원리
 * 여러 패턴을 트라이(Trie)에 넣어 "하나의 자동자"로 만든 뒤,
 * KMP의 실패 함수처럼 매칭 실패 시 되돌아갈 위치를 알려주는 fail 링크를 BFS로 계산해둔다.
 * 텍스트를 한 글자씩 읽으며 트라이 간선 또는 fail링크를 타고 이동하면 어떤 패턴이든 다시 처음부터 비교할 필요가 없다.
 *
 * - 시간 복잡도
 * 전처리 O(패턴 길이 합), 검색 O(텍스트 길이) - 패턴 개수와 무관하게 텍스트를 단 한번만 훑습니다.
 *
 *
 * - 문제 설명
 * 집합 S는 크기가 N이고, 원소가 문자열인 집합이다.
 * Q개의 문자열이 주어졌을 때, 각 문자열이 집합 S에 속한 문자열을 부분 문자열로 포함하는지 판별하는 프로그램을 작성하시오.
 *
 * - 입력 형식
 * 1. 첫 줄: 집합 S의 크기 N
 * 2. 다음 N줄: 집합의 원소 (각 문자열, 길이 100 이하, 소문자 알파벳)
 * 3. 다음 줄: 질의 문자열 개수 Q
 * 4. 다음 Q줄: 판별할 문자열 (각 길이 10,000 이하, 소문자 알파벳)
 *
 * - 출력 형식
 * 각 질의 문자열에 대해, 집합 S의 원소를 부분 문자열로 하나라도 포함하면 YES, 아니면 NO를 한 줄씩 출력한다.
 *
 *예제 입력
 *
 * 3(집합 문자열의 수)
 * www
 * woo
 * jun
 * 
 * 3 (질의 문자열의 수)
 * myungwoo
 * hongjun
 * dooho
 *
 * 예제 출력
 *
 * YES
 * YES
 * NO
 * 
 * (myungwoo → "woo" 포함, hongjun → "jun" 포함, dooho → 셋 다 미포함)
 *
 * - 제한 조건
 *
 * 1 ≤ N ≤ 1,000
 * 1 ≤ Q ≤ 1,000
 * 모든 문자열은 소문자 알파벳으로만 구성
 * 시간 제한 2초, 메모리 제한 256MB
 */
public class Day80StringSetDiscrimination {

    // trie[노드번호][문자 0~25] = 다음 노드번호
    static int[][] trie;

    // 매칭 실패시 되돌아갈 노드번호
    static int[] fail;

    // 해당 노드가 S의 어떤 문자열의 끝인지
    static boolean[] isEnd;

    // 새 노드를 만들때마다 증가시킬 인덱스
    static int nodeCount;

    public static void main(String[] args) throws Exception {

        // Q개의 질의마다 S의 N개 원소를 매번 처음부터 비교하면 최악의 경우 시간 초과가 날 수 있어요.
        // 그래서 S의 N개 문자열을 하나의 트라이 + fail 링크로 미리 합쳐두고,
        // 질의 문자열을 딱 한 번만 훑으면서 판별하는 게 아호-코라식의 역할입니다.

        // BufferedReader 사용은 맞으나 StringTokenizer는 필요없음 -> 한줄에 문자열이 한개만 입력으로 들어오므로
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력된 문자열
        int N = Integer.parseInt(br.readLine());

        int MAX_NODES = N * 100 + 1;
        trie = new int[MAX_NODES][26];
        fail = new int[MAX_NODES];
        isEnd = new boolean[MAX_NODES];

        for(int i = 0; i < N; i++) {
            // 빈칸없이 입력받기
            String s = br.readLine().trim();
            insert(s);
        }

        // S의 모든 문자열이 트라이에 삽입되었을 경우 수행
        // BFS로 fail을 계산
        buildFail();

        // 검사할 문자열
        int q = Integer.parseInt(br.readLine().trim());
        for (int i = 0; i < q; i++) {
            String query = br.readLine().trim();

            System.out.println(query(query) ? "YES" : "NO");
        }
    }

    // insert(String s)
    // S의 집합 문자열들을 트라이(글자 트리)에 하나씩 심는 단계. 아직 검사는 안 함,
    // 그냥 "www", "woo", "jun"을 겹치는 글자는 공유하는 나무 구조로 만드는 것
    private static void insert(String s) {
        //1. 현재 위치(cur)를 루트 노드(0번)로 설정한다.
        int cur = 0;
        //2. 문자열 s의 각 글자를 처음부터 끝까지 하나씩 순회한다.
        for(int i = 0; i < s.length(); i++) {
            //3. 현재 글자를 인덱스(0~25)로 변환한다 ('a'는 0, 'b'는 1, ... 'z'는 25).
            int idx = s.charAt(i) - 'a';

            //4. 현재 노드(cur)에서 그 인덱스로 가는 자식 노드가 아직 없다면, 새 노드 번호를 하나 만들어서 연결한다.
            if(trie[cur][idx] == 0) {
                nodeCount += 1;
                trie[cur][idx] = nodeCount;
            }

            //5. 현재 위치(cur)를 방금 이동한 자식 노드로 갱신한다.
            cur = trie[cur][idx];

            //6. 2~5번을 문자열의 마지막 글자까지 반복한다.
        }

        //7. 반복이 끝난 후 마지막으로 도달한 노드를 "문자열이 끝나는 지점"으로 표시한다.
        isEnd[cur] = true;
    }


    // buildFail()
    // S로 만든 트라이를 얕은 노드부터(BFS) 훑으면서, 각 노드마다
    // "나중에 질의 문자열을 검색하다가 여기서 다음 글자로 갈 자식이 없으면,
    //  루트로 처음부터 다시 훑는 대신 어느 노드로 건너뛸지"를 미리 계산해서 fail[]에 저장해두는 단계.
    // (실제로 건너뛰는 동작은 여기서 일어나는 게 아니라, 나중에 query()에서 이 값을 참고할 때 일어남)
    private static void buildFail() {
        //1. 루트(0번)의 fail은 자기 자신(0)으로 둔다.
        fail[0] = 0;

        //2. 큐(Queue)를 하나 준비한다.
        Queue<Integer> queue = new ArrayDeque<>();

        //3. 루트의 26가지 문자(idx = 0~25)에 대해 반복한다.
        for(int i = 0; i < 26; i++) {
            //3-1. 그 방향에 자식이 있으면: 그 자식의 fail을 루트(0)로 설정하고 큐에 넣는다.
            if(trie[0][i] != 0) {
                fail[trie[0][i]] = 0;
                queue.offer(trie[0][i]);
            }
        }

        //4. 큐가 빌 때까지 다음을 반복한다.
        while(!queue.isEmpty()) {
            //4-1. 큐에서 노드 하나(cur)를 꺼낸다.
            int cur = queue.poll();

            //4-2. cur의 26가지 문자(idx = 0~25)에 대해 반복한다.
            for(int i = 0; i < 26; i++) {
                // (a) 그 방향에 자식(next)이 있으면:
                int next = trie[cur][i];

                if(next != 0) {
                    // next의 fail 값을 "trie[ cur의 fail ][ i ]" 로 설정한다.
                    fail[next] = trie[fail[cur]][i];

                    // next의 fail이 끝 노드(isEnd)라면, next도 끝 노드로 표시한다. (부분 매칭 정보 이어받기)
                    if(isEnd[fail[next]]) {
                        isEnd[next] = true;
                    }

                    // next를 큐에 넣는다.
                    queue.offer(next);
                } else {
                    // (b) 그 방향에 자식이 없으면:
                    // trie[cur][i]를 "trie[ cur의 fail ][ i ]" 값으로 채워 넣는다.
                    // (나중에 query에서 그냥 이 값을 따라가면 되도록 미리 연결)
                    trie[cur][i] = trie[fail[cur]][i];
                }
            }
        }
    }

    // query(String text)
    // 실제 질의 문자열을 한 글자씩 읽으면서 트라이를 타고 내려가다가
    // 도중에 "isEnd" 노드(= S의 어떤 단어의 끝)를 밟으면 그 순간 "포함되어 있다"고 판정.
    private static boolean query(String text) {
        //1. 현재 위치(cur)를 루트 노드(0)로 설정한다.
        int cur = 0;

        //2. text의 각 글자를 처음부터 끝까지 하나씩 순회한다.
        //6. 2~5번을 문자열 끝까지 반복한다.
        for(int i = 0; i < text.length(); i++) {
            //3. 현재 글자를 인덱스(0~25)로 변환한다.
            int idx = text.charAt(i) - 'a';
            //4. 현재 위치(cur)를 trie[cur][idx]로 이동한다. (buildFail에서 이미 "자식 없을 때 갈 곳"까지 다 채워놨기 때문에, 여기서는 없음 걱정 없이 무조건 이동만 하면 됨)
            cur = trie[cur][idx];
            //5. 이동한 노드가 끝 노드(isEnd)라면, 그 즉시 true를 반환한다. (더 볼 필요 없이 이미 하나 찾았으니까)
            if(isEnd[cur]) {
                return true;
            }
        }

        //7. 끝까지 다 돌았는데 한 번도 isEnd를 못 만났으면 false를 반환한다.
        return false;
    }

}
