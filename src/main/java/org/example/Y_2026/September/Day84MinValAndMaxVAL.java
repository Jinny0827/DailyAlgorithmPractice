package org.example.Y_2026.September;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Day84 최솟값과 최댓값
 *
 * 세그먼트 트리 개념
 *
 * - 언제 사용?
 * 배열에 대해 구간 합/최솟값/최댓값 등의 질의가 여러번 반복되고, 값이 업데이트 될 수 있는 상황에서 빠르게 답해야 할때 사용
 *
 * - 핵심 동작 원리
 * 배열을 이진 트리 형태로 분할해 각 노드가 특정 구간의 대표값(합/최소/최대 등)을 저장하고, 질의 시 트리를 타고 내려가며 필요한 구간만 결합해 답을 구한다.
 *
 * - 시간 복잡도
 * 구간 질의와 갱신 모두 O(log N) - 배열 전체를 매번 순회하는 O(N)보다 훨씬 빠르다
 *
 *
 *
 * 문제 설명
 *
 * N개의 정수가 주어졌을 때, 임의의 구간 [a, b]에 속한 정수들 중 최솟값과 최댓값을 구하는 M개의 질의를 처리하는 프로그램을 작성하세요.
 *
 * 입력
 * 첫째 줄: 정수의 개수 N (1 ≤ N ≤ 100,000), 구간 질의의 개수 M (1 ≤ M ≤ 100,000)
 * 둘째 줄부터 N개의 줄에 걸쳐 정수가 하나씩 주어짐 (절댓값 1,000,000 이하)
 * 다음 M개의 줄에 걸쳐 정수 a, b (1 ≤ a ≤ b ≤ N)가 주어짐
 * 출력
 *
 * M개의 줄에 걸쳐 각 질의 구간 [a, b]의 최솟값과 최댓값을 공백으로 구분하여 출력
 *
 * 입출력 예시
 *
 * 입력
 *
 * 10 4
 * 75
 * 30
 * 45
 * 50
 * 55
 * 25
 * 35
 * 75
 * 40
 * 50
 * 1 10
 * 3 5
 * 6 9
 * 8 10
 *
 * 출력
 *
 * 25 75
 * 45 55
 * 25 75
 * 40 75
 * 제한 조건
 * 시간 제한: 3초 (일반적으로), 메모리 256MB 수준
 * N, M ≤ 100,000 → O(N log N) 이하 전처리, 질의당 O(log N) 필요
 */
public class Day84MinValAndMaxVAL {
    static int[] arr;

    static int[] minTree;

    static int[] maxTree;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 입력된 숫자 총 갯수
        int N = Integer.parseInt(st.nextToken());
        // 최솟값 최댓값을 구하는 요청 갯수
        int T = Integer.parseInt(st.nextToken());

        // 1부터 시작하는 1-index 사용
        arr = new int[N + 1];
        // 입력 숫자 arr 삽입
        for(int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 세그먼트 트리용 배열(구간 크기의 안전한 상한이 보통 4*N)
        // 세그먼트 트리는 *** 완전 이진트리 형태 *** 
        // N이 딱 2의 거듭제곱이 아니면 트리가 꽉찬 모양이 안되고 마지막 레벨이 일부만 채워진 상태
        // build / query는 매번 반씩 쪼개가면서 최솟값/최댓값 계산함 (세그먼트 트리를 내려간다.) -> 한번 쪼개질때마다 log2(N)
        // 결론 : log2(N) = 몇 번 반으로 쪼개야 하는지(=트리 높이=시간복잡도), ceil = N이 딱 안 떨어질 때 정수 층수로 올림 처리
        minTree = new int[4 * N];
        maxTree = new int[4 * N];

        // build는 딱 한 번, 루트(node=1)에서 전체 구간(1~N)으로 시작
        build(1, 1, N);


        // query는 T번, 각 줄마다 a, b 읽어서 바로 호출 + 출력
        // 커맨드에 대한 처리
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int min = queryMin(1, 1, N, a, b);
            int max = queryMax(1, 1, N, a, b);

            sb.append(min).append(' ').append(max).append('\n');

        }

        System.out.println(sb);
    }

    private static void build(int node, int start, int end) {
        // 1. 종료 조건: 구간 크기가 1이면 리프 노드
        if(start == end) {
            // 시작과 종료가 같다면 어차피 같은 인덱스 값 (리프와 자식 노드가 같은 자리)
            minTree[node] = arr[start];
            maxTree[node] = arr[start];
            return;
        }

        // 2. 구간을 절반으로 나눌 중간 지점 계산
        int mid = (start + end) / 2;

        // 좌측 노드는 node * 2 (1이면 2, 2면 4, 3이면 6)
        // 우측 노드는 node * 2 + 1 좌측 노드에서 1을 더하면 우측노드가 됨 (1이면 3, 2면 5, 등..)

        // 3. 왼쪽 절반을 재귀로 먼저 채움
        build(node * 2, start, mid);

        // 4. 오른쪽 절반을 재귀로 채움
        build(node * 2 + 1, mid + 1, end);

        // 5. 자식 두 개(왼쪽/오른쪽)가 채워졌으니 비교해서 내 값 결정
        minTree[node] = Math.min(minTree[node * 2], minTree[node * 2 + 1]);
        maxTree[node] = Math.max(maxTree[node * 2], maxTree[node * 2 + 1]);
    }

    private static int queryMin(int node, int start, int end, int left, int right) {
        // 1. 현재 구간[start, end]이 질의 구간[left, right]과 완전히 벗어난 경우
        //    → 최솟값 계산에 영향 없어야 하므로 "무한대"에 해당하는 값 반환
        if(end < left || right < start) {
            // 시작 값이 질의 끝 범위보다 크거나 질의 끝 값이 시작 범위보다 작으면
            return Integer.MAX_VALUE;
        }


        // 2. 현재 구간이 질의 구간에 완전히 포함되는 경우
        //    → 더 내려갈 필요 없이 이 노드 값이 곧 답
        if(left <= start && end <= right) {
            return minTree[node];
        }

        // 3. 일부만 겹치는 경우
        //    → 왼쪽/오른쪽 자식으로 나눠서 각각 물어보고, 둘 중 더 작은 값 반환
        // 좌측 노드는 node * 2 (1이면 2, 2면 4, 3이면 6)
        // 우측 노드는 node * 2 + 1 좌측 노드에서 1을 더하면 우측노드가 됨 (1이면 3, 2면 5, 등..)
        int mid = (start + end) / 2;
        int leftResult = queryMin(node * 2, start, mid, left, right); // 좌측
        int rightResult = queryMin(node * 2 + 1, mid + 1, end, left, right); // 우측

        return Math.min(leftResult, rightResult);
    }
    private static int queryMax(int node, int start, int end, int left, int right) {
        // 1. 현재 구간이 질의 구간과 완전히 벗어난 경우
        //    → 최댓값 계산에 영향 없어야 하므로 "매우 작은 값" 반환
        if(end < left || right < start) {
            return Integer.MIN_VALUE;
        }


        // 2. 현재 구간이 질의 구간에 완전히 포함되는 경우
        //    → 이 노드의 maxTree 값이 곧 답
        if(left <= start && end <= right) {
            return maxTree[node];
        }


        // 3. 일부만 겹치는 경우
        //    → 왼쪽/오른쪽 자식에게 각각 물어보고, 둘 중 더 큰 값 반환
        // 좌측 노드는 node * 2 (1이면 2, 2면 4, 3이면 6)
        // 우측 노드는 node * 2 + 1 좌측 노드에서 1을 더하면 우측노드가 됨 (1이면 3, 2면 5, 등..)
        int mid = (start + end) / 2;
        int leftResult = queryMax(node * 2, start, mid, left, right); // 좌측
        int rightResult = queryMax(node * 2 + 1, mid + 1, end, left, right); // 우측

        return Math.max(leftResult, rightResult);
    }

}
