package org.example.Y_2026.August;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Period;
import java.util.*;

/**
 * Day67 Strongly Connected Component (SCC)
 *
 * 방향 그래프가 주어졌을 때, 그 그래프를 SCC(강한 연결요소)들로 나누는 프로그램을 작성하시오.
 *
 * 방향 그래프의 SCC는 정점의 최대 부분집합으로, 그 부분집합에 속한 서로 다른 임의의 두 정점 u, v에 대해 u→v 경로와 v→u 경로가 모두 존재하는 경우를 말한다.
 * 예를 들어 그래프에 {a, b, e}, {c, d}, {f, g}, {h} 라는 SCC들이 있을 수 있다.
 * h에서 자기 자신으로 가는 간선이 없어도 {h}는 그 자체로 SCC를 이룬다.
 *
 * 입력
 *
 * 첫째 줄: V(정점 수, 1 ≤ V ≤ 10,000), E(간선 수, 1 ≤ E ≤ 100,000)
 * 다음 E개 줄: A B (A→B 방향 간선)
 * 정점 번호는 1~V
 *
 * 출력
 *
 * 첫째 줄: SCC 개수 K
 * 다음 K개 줄: 각 SCC에 속한 정점을 오름차순으로 나열하고 줄 끝에 -1. SCC들은 그 안에서 가장 작은 정점 번호 순으로 출력
 */
public class Day67StronglyConnectedComponent {

    // 인접 리스트 (정점 간 직연결 리스트)
    static List<Integer>[] adj;

    // 크기 V + 1, 방문 순서
    static int[] id;
    
    // 크기 V + 1, 로우링크값
    static int[] low;
    
    // 정점이 현재 확정 대기 스택에 있는지 boolean 배열
    static boolean[] onStack;

    // SCC 확정용 보조 스택(Tarjan의 그 스택)
    static Deque<Integer> assistStack;
    
    // Deque용 -> 반복문 DFS를 시뮬레이션 할 스택, 여기에 현재 정점뿐 아니라 그 정점 인접리스트 중 몇 번째까지 처리했는지 인덱스도 담기
    // {정점, 다음으로 처리할 이웃 인덱스}
    static Deque<int[]> callStack;

    // DFS 방문 순서 채번용 카운터, 전체 정점 수회하며 계속 누적
    static int idCounter;

    // 결과 담을 그릇
    static List<List<Integer>> sccList;



    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        // 인접리스트
        // adj 배열 리스트 초기화 -> 공간을 미리채워두지 않고 값 밀어넣으면 에러 발생 (NPE)
        adj = new ArrayList[V + 1];
        for(int i = 1; i <= V; i++) {
            adj[i] = new ArrayList<>();
        }

        // 각 정점이 DFS로 방문한 순서 및 -1로 초기화 (몇번째로 방문했는지/방문했는지 여부)
        id = new int[V + 1];
        for(int i = 1; i <= V; i++) {
            id[i] = -1;
        }

        // 각 정점에서 back-edge로 도달 가능한 가장 낮은 id
        low = new int[V + 1];

        // 전역 변수 초기화
        onStack = new boolean[V+1];
        assistStack = new ArrayDeque<>();
        callStack = new ArrayDeque<>();
        sccList = new ArrayList<>();
        idCounter = 0;

        // A 정점에 인접 정점 B를 넣어주는 작업
        for(int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            adj[A].add(B);
        }

        // 주의점 - V가 최대 10,000개라 재귀 DFS로 로직 구성 시 StackOverFlow 발생 가능성 UP
        // -> 재귀 대신 반복문 스택 기반 DFS로 구현하거나 재귀 쓴다면 스레드에 큰 스택 크기 주고 실행하는 방식 고려
        for(int start = 1; start <= V; start++) {
            if(id[start] == -1) {
                // 미방문 노드인경우
                DFS(start);
            }
        }

        // 안쪽 배열 > scc 정렬 처리
        for (List<Integer> scc : sccList) {
            Collections.sort(scc);
        }

        // 바깥 배열 > SCC들 끼리는 가장 작은 정점 기준 오름차순 정렬
        sccList.sort((a, b) -> a.get(0) - b.get(0));

        StringBuilder sb = new StringBuilder();
        sb.append(sccList.size()).append("\n");
        for (List<Integer> scc : sccList) {
            for(int v : scc) {
                sb.append(v).append(" ");
            }

            sb.append("-1\n");
        }

        System.out.println(sb);
    }

    // 스택 맨 위를 들여다보면서 아직 안 가본 이웃이 있으면 그쪽으로 한칸 더 들어가고, 다봤으면 빠져나온다를 반복
    private static void DFS(int start) {
        // start 정점을 "처음 방문"으로 처리
        // id[start], low[start]를 현재 idCounter 값으로 설정하고, idCounter를 1 증가
        // start를 assistStack에 쌓기, onStack[start]를 true로 표시
        // callStack에 "{start 정점, 다음에 볼 이웃 인덱스 0}" 프레임을 쌓기

        // id = 이 정점을 몇 번째로 방문했나? -> 고유 타임스탬프(안겹치게)
        // low = 이 정점에서 사이클을 타고 도달가능한 가장 작은 id
        // 왜 idCounter 사용? -> 전역 카운터이므로 정점마다 겹치지 않는 순차적 id 발급 용도
        // idCounter의 현 값을 대입시키고 idCounter는 대입 후 하나 증가
        id[start] = low[start] = idCounter++;

        // 이 정점에서 아직 SCC로 확정되지 않았고, 현재 탐색 경로상 살아있다는 표시
        // -> 나중에 id[v] == low[v]가 되면 이 스택에서 정점들을 꺼내 하나의 SCC로 묶는다.
        assistStack.push(start);
        onStack[start] = true;

        // 반복문 DFS의 지금 여기 들어왔고 아직 이웃 하나도 안봤다라는 상태를 스택에 등록 -> while문 정점 처리 시작을 위한 단계
        callStack.push(new int[] {start, 0});

        while(!callStack.isEmpty()) {
            // 프레임 전체
            int[] top = callStack.peek();
            // v = 정점번호
            int v = top[0];
            // idx = 다음으로 바라볼 이웃 인덱스
            int idx = top[1];

            if(idx < adj[v].size()) {
                // 아직 안 본 이웃이 있는 경우 처리
                int B = adj[v].get(idx);
                top[1] = idx + 1;

                if(id[B] == -1) {
                    // 논리가 같다고 다시 DFS 불러오면 재귀를 사용하는거로 돌아가는 것
                    // idCounter의 현 값을 대입시키고 idCounter는 대입 후 하나 증가
                    id[B] = low[B] = idCounter++;
                    assistStack.push(B);
                    onStack[B] = true;
                    callStack.push(new int[] {B, 0});
                } else if(onStack[B]) {
                    low[v] = Math.min(low[v], id[B]);
                } else {

                }

            } else {
                // 이웃 다봤음 -> pop 처리
                callStack.pop();

                if(!callStack.isEmpty()) {
                    int parent = callStack.peek()[0];
                    low[parent] = Math.min(low[parent], low[v]);
                }

                if(id[v] == low[v]) {
                    // v가 SCC의 뿌리인지 확인 (자기 자신보다 더 이른 곳으로 못돌아감 = 뿌리로 못돌아감)
                    List<Integer> scc = new ArrayList<>();
                    while(true) {
                        int w = assistStack.pop();
                        onStack[w] = false;
                        scc.add(w);
                        if (w == v) {
                            break;
                        }
                    }
                    sccList.add(scc);
                }
            }

        }
    }

}
