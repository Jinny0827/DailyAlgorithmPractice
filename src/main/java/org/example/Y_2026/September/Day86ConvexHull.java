package org.example.Y_2026.September;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Day 86 볼록껍질
 *
 * 기하 (Convex Hull)
 *
 * 언제 쓰나?
 * - 여러 점이 주어졌을 때 이 점들을 모두 포함하는 최소 크기의 볼록 다각형(외곽선)을 구해야할 때 사용
 *
 * 핵심 동작 원리?
 * - 점들을 x 좌표 (동률이면 y좌표) 기준으로 정렬한 뒤,
 * CCW(반시계방향) 판정으로 아래쪽 체인과 위쪽 체인을 각각 스택처럼 쌓으면서 오른쪽으로 꺽이거나 일직선이 되는 점을 pop하는
 * Monotone Chain(Andrew's Algorithm)이 대표적이다.
 *
 * 시간 복잡도?
 * 정렬 O(N log N) + 스캔 O(N) -> 전체 O(N log N)
 *
 * - 문제 설명
 * 2차원 평면 위에 N개의 점이 주어진다.
 * 이 점들 중 일부를 꼭짓점으로 하는 볼록 다각형을 만들어 나머지 모든 점을 포함하도록 할 때,
 * 이 볼록 껍질(Convex Hull)을 이루는 점의 개수를 구하시오.
 *
 * - 입출력 예시
 * 1. 입력
 * 10
 * 0 0
 * 4 1
 * 5 4
 * 3 6
 * 0 5
 * 2 3
 * 1 1
 * 3 2
 * 2 4
 * 4 3
 *
 * 2. 출력
 * 5
 *
 * (볼록 껍질을 이루는 점: (0,0), (4,1), (5,4), (3,6), (0,5) — 나머지 5개 점은 내부에 위치)
 *
 *- 제한 조건
 * 1 ≤ N ≤ 100,000
 * 좌표는 절댓값 40,000 이하의 정수
 * 좌표가 같은 두 점은 없음
 */
public class Day86ConvexHull {

    // n개의 점
    static int n;

    // n개의 점에 대한 좌표 2차원 배열
    static int[][] points;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine().trim());
        
        // 1번 공간은 점의 갯수만큼, 2번 공간은 x,y 좌표 두 개 각 점은 x,y
        points = new int[n][2];

        for(int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            points[i][0] = Integer.parseInt(st.nextToken());
            points[i][1] = Integer.parseInt(st.nextToken());
        }

       int result = convexHull();

        System.out.println(result);
    }

    public static int convexHull() {
        // 정렬 + CCW + 스택 로직 (Monotone Chain(Andrew's Algorithm))

        // 1단계. 점 정렬
        // 모든 점을 x좌표 기준 오름차순, x가 같으면 y좌표 기준 오름차순으로 정렬한다.
        // 1차원이면 원소=값, 차원(배열의 배열)이면 원소=작은 배열이라 그 안에서 원하는 값을 인덱스로 꺼내서 비교
        List<int[]> sorted = Arrays.stream(points)
                .sorted((a, b) -> a[0] != b[0] ? a[0]-b[0] : a[1]-b[1])
                .collect(Collectors.toList());

        //3단계. 아래쪽 체인(lower hull) 만들기
        //빈 스택(리스트)을 준비한다.
        //정렬된 점을 왼쪽에서 오른쪽으로 하나씩 순회하며:
        //스택에 점이 2개 이상 쌓여있고, "스택의 마지막 2개 점 + 새 점"이 CCW 기준으로 오른쪽으로 꺾이거나(외적 ≤ 0) 일직선이면 → 스택의 맨 위 점을 pop
        //조건을 만족할 때까지 반복한 뒤, 새 점을 push
        //이렇게 끝까지 순회하면 아래쪽 체인 완성
        List<int[]> lower = buildChain(sorted);

        //4단계. 위쪽 체인(upper hull) 만들기
        //같은 방식이지만, 이번엔 오른쪽에서 왼쪽으로(정렬 역순) 순회하며 3단계와 동일한 로직 적용
        //위쪽 체인 완성
        List<int[]> reversedList = new ArrayList<>(sorted);
        Collections.reverse(reversedList);
        List<int[]> upper = buildChain(reversedList);

        //5단계. 두 체인 합치기
        // 아래쪽 체인과 위쪽 체인을 합친다.
        // 이때 양 끝(시작점, 끝점)이 두 체인에 중복으로 들어가므로, 각 체인에서 마지막 점을 하나씩 제외하고 합쳐야 한다.
        // upper에서 마지막 원소가 겹치므로 제외하고 lower를 뒤에 붙이면 된다.
        lower.addAll(upper.subList(1, upper.size() - 1));


        //6단계. 결과 반환
        //합쳐진 볼록 껍질에 속한 점들의 개수를 반환한다.
        return lower.size();
    }

    // 아래쪽 체인과 위쪽 체인이 완전히 동일한 로직(순서만 다름)

    // O(0,0) → A(1,0)로 오른쪽(동쪽)을 보고 걷다가, B(0,1) 쪽으로 방향을 틀면,
    // → **왼쪽(북쪽)**으로 꺾는 것 = 반시계 방향 = 외적값 양수(+)

    // O(0,0) → A(1,0)로 걷다가, B(0,-1) 쪽으로 방향을 틀면
    // → **오른쪽(남쪽)**으로 꺾는 것 = 시계 방향 = 외적값 음수(-)

    //O, A, B가 일직선이면 꺾지 않은 것 = 외적값 0

    //세 점 O, A, B가 있을 때, 벡터 OA와 OB의 외적을 계산하는 함수를 만든다.
    //외적값 > 0 이면 반시계(왼쪽 turn), < 0 이면 시계(오른쪽 turn), = 0 이면 일직선.
    private static List<int[]> buildChain(List<int[]> sortedList) {

        //결과를 담을 빈 리스트(chain) 생성
        List<int[]> chain = new ArrayList<>();

        // sortedList를 처음부터 끝까지 순회 (for문, 현재 점 = p)
        for(int i = 0; i < sortedList.size(); i++) {
            // 지금 순서상 검토해야 할 점을 꺼냅니다.
            int[] p = sortedList.get(i);

            // chain에 점이 2개 이상 쌓여있는 동안(while문), ccw(끝에서 두번째, 맨 끝, p)가 0 이하면 → 맨 끝 원소 pop
            // chain에 최소 2개는 쌓여 있어야 O,A를 뽑아서 비교할수 있으니 앞 조건 필요
            // chain의 끝에서 두 번째가 O, 맨 끝이 A, 그리고 새로 검토중인 p가 B -> 추가되는건(add) 제일 마지막으로 붙기 때문이다.
            // 이 셋으로 방향 판정 -> <= 0 자체가 음수이기 때문에 오른쪽 회전(음수/y방향 회전) 혹은 직선방향(0)이면 정점 제거
            while (chain.size() >= 2 &&
                    ccw(chain.get(chain.size() - 2), chain.get(chain.size() - 1), p) <= 0) {
                // 오른쪽 회전/일직선이면 마지막 정점 제거
                // 열의 마지막 원소를 지우는 것 = 스택의 pop과 같은 동작
                chain.remove(chain.size() - 1);
            }

            // while을 빠져나오면 p를 chain에 push
            chain.add(p);
        }


        // 순회 끝나면 chain 반환
        return chain;
    }

    private static long ccw(int[] o, int[] a, int[] b) {
        // 벡터의 외적 공식 작성
        long oaX = a[0] - o[0];
        long oaY = a[1] - o[1];
        long obX = b[0] - o[0];
        long obY = b[1] - o[1];

        // "OA의 x와 OB의 y", "OA의 y와 OB의 x"처럼 서로 다른 두 벡터의 성분을 섞어서 곱
        long cross = oaX * obY - oaY * obX;

        return cross;
    }

}
