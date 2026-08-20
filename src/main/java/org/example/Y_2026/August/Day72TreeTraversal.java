package org.example.Y_2026.August;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Day72 트리의 순회
 *
 * n개의 정점으로 이루어진 이진 트리가 있다.
 * 정점에는 1~n번 번호가 중복 없이 매겨져 있다.
 * 트리의 inorder(중위 순회)와 postorder(후위 순회) 결과가 주어질 때, preorder(전위 순회) 결과를 구하시오.
 *
 * 입력 >>
 * 3 (정점 갯수)
 * 1 2 3 (중위 순회)
 * 1 3 2 (후위 순회)
 *
 * 출력 >>
 * 2 1 3 (전위 순회)
 * 
 */
public class Day72TreeTraversal {

    static StringBuilder sb = new StringBuilder();
    static int[] inorder;
    static int[] postorder;
    static int[] inPos;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 정점의 개수 N
        int N = Integer.parseInt(st.nextToken());

        // 중위 순회
        inorder = new int[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            inorder[i] = Integer.parseInt(st.nextToken());
        }
        
        // 후위 순회
        postorder = new int[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            postorder[i] = Integer.parseInt(st.nextToken());
        }

        
        // 값 -> 인덱스 매핑 (중위탐색 O(1)용)
        // 재귀 중에 후위 순회에서 뽑은 루트 값이 중위 순회에서 어디있지? 를 찾아야 하는데 매번 inorder를 부르기엔 오버헤드
        // inPos[정점번호] = inorder(중위순회) 배열 내의 인덱스 -> inPos안에 inorder의 인덱스 값을 저장한다고 생각
        inPos = new int[N + 1];
        for(int i = 0; i < N; i++) {
            inPos[inorder[i]] = i;
        }

        // 재귀 함수 (첫 호출은 중위 0번째 인덱스 ~ 끝 인덱스(N - 1), 후위 0번째 인덱스 ~ 끝 인덱스(N - 1) > N-1인 이유는 0부터 시작)
        build(0, N - 1, 0, N - 1);
        System.out.println(sb.toString().trim());
        
        // 입력으로 주어지는건 중위, 후위만 주고 이를 역산해서 전위 순회 계산
        // 핵심은 루트가 어디있느냐의 문제 (전위는 맨앞/후위는 맨 뒤가 루트/중위는 루트를 기준으로 왼/오른쪽 서브트리가 나뉨)
        // 역산 원리 (중위 + 후위 -> 전위)
        // 1. 후위 순회의 마지막원소가 루트 노드
        // 2. 1에서 구한 루트 값으로 중위 순회에서 위치를 찾는다. 그 위치를 기준으로 중위 순회가 왼쪽 서브트리/루트/오른쪽서브트리
        // 3. 왼쪽 서브트리의 크기(개수)만큼 후위 순회 앞 부분이 왼쪽 서브트리의 후위 순회, 나머지(루트 제외)가 오른쪽 서브트리의 후위 순회
        // 4. 이 과정을 왼쪽/오른쪽 서브트리에 대해 재귀적으로 반복하여, 방문 순서를 루트 -> 왼쪽 결과 -> 오른쪽 결과를 출력하면 전위 순회

    }

    /**
     * 예시 입력 >>
     * 3 (정점 갯수)
     * 1 2 3 (중위 순회)
     * 1 3 2 (후위 순회)
     **/
    private static void build(int start, int inEnd, int postStart, int postEnd) {
        // start는 inorder의 시작 인덱스/inEnd는 inorder의 끝 인덱스
        // postStart는 postorder의 시작 인덱스/postEnd는 postorder의 끝 인덱스

        // build 재귀함수의 목적 : 주어진 서브트리를 전위 순서로 sb에 써넣는 것
        // 전위 = 루트 -> 왼쪽 -> 오른쪽 순서
        // 구한 루트 노드를 기준으로 왼쪽 구간을 그대로 넘기면 왼쪽 서브트리를 재귀시킬수 있고
        // 구한 루트 노드를 기준으로 오른쪽 구간을 그대로 넘기면 오른쪽 서브트리 재귀

        // 중위 노드의 시작 노드가 끝 노드를 넘기면 재귀 멈춤 (노드의 갯수 인덱스를 넘기는것)
        if (start > inEnd) {
            return;
        }
        
        // 1. 현재 구간 내 루트값 찾기 (후위 순회 맨 끝)
        int root = postorder[postEnd];

        // 2. 전위 순회이므로 루트를 먼저 sb에 출력
        sb.append(root).append(' ');

        // 3. 중위 순회에서 루트의 위치(인덱스) 찾기 (inPos 조회)
        int rootIdx = inPos[root];

        // 4. 왼쪽 서브트리 노드 개수 계산 (leftSize = rootIdx - start) 루트인덱스 - 시작인덱스 = 왼쪽 서브트리 갯수 표현 가능
        int leftSize = rootIdx - start;


        // 5. 왼쪽 서브트리 재귀 호출
        //    inorder 구간: [start, rootIdx - 1]
        //    postorder 구간: [postStart, postStart + leftSize - 1]
        build(start, rootIdx - 1, postStart, postStart + leftSize - 1);

        // 6. 오른쪽 서브트리 재귀 호출
        //    inorder 구간: [rootIdx + 1, inEnd]
        //    postorder 구간: [postStart + leftSize, postEnd - 1]
        build(rootIdx + 1, inEnd, postStart + leftSize, postEnd - 1);
    }


}
