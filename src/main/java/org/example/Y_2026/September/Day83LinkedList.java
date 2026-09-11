package org.example.Y_2026.September;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Day83 연결리스트
 *  
 *  연결리스트 (배열 기반 이중 연결 리스트 시뮬레이션)
 *  - 언제 쓰나?
 *  삭제, 복구가 반복되면서 순서를 유지해야하는 경우, 특히 "삭제 취소"까지 요구될때 사용
 *  
 *  - 핵심 원리
 *  각 원소에 prev[], next[] 배열을 두고, 삭제 시 이웃끼리 바로 연결
 *  (next[prev[x]] = next[x] 등) 해 O(1)로 끊어내고, 복구 시 저장해둔 연결 정보를 그대로 되돌린다.
 *  
 *  -시간 복잡도
 *  삭제/이동/복구 모두 O(1) -> 전체 명령 처리 O(N+M)
 *
 *
 *  문제 설명
 *
 *  크기 n인 표가 있고, 0번 행부터 n-1번 행까지 있습니다.
 *  처음엔 k번 행이 선택된 상태입니다.
 *  다음 명령어를 순서대로 처리한 뒤, 삭제되지 않은 행은 O, 삭제된 행은 X로 표시한 길이 n 문자열을 반환해야 합니다.
 *
 * U X: 현재 선택된 행에서 위로 X칸 떨어진, 삭제되지 않은 행을 선택
 * D X: 현재 선택된 행에서 아래로 X칸 떨어진, 삭제되지 않은 행을 선택
 * C: 현재 선택된 행을 삭제하고, 바로 아래 행(없으면 바로 위 행)을 선택
 * Z: 가장 최근에 삭제된 행을 원래 자리에 복구 (선택된 행은 그대로 유지)
 *
 * 입출력 예시
 * n	k	cmd	result
 * 8	2	["D 2","C","U 3","C","D 4","C","U 2","Z","Z"]	"OOOOXOOO"
 * 8	2	["D 2","C","U 3","C","D 4","C","U 2","Z","Z","U 1","C"]	"OOXOXOOO"
 *
 * 제한 조건
 * 5 ≤ n ≤ 1,000,000
 * 0 ≤ k < n
 * 1 ≤ cmd 원소 개수 ≤ 200,000
 * U/D의 이동 칸 수는 항상 유효한 범위 내로 주어짐
 *
 * U = Up, 위로 이동 (선택만 바뀜)
 * D = Down, 아래로 이동 (선택만 바뀜)
 * C = 삭제 (Delete/Cut)
 * Z = 복구 (Ctrl+Z, undo에서 따온 것 — 되돌리기)
 *
 * naive하게 리스트/배열 삭제로 접근하면 n, cmd가 커질 때 시간초과 나기 딱 좋은 문제입니다 — 그 지점이 핵심입니다.
 */
public class Day83LinkedList {

    public static void main(String[] args) {
        String[] cmd = {"D 2","C","U 3","C","D 4","C","U 2","Z","Z"};
        String result = solution(8, 2, cmd);
        System.out.println(result);
    }

   private static String solution(int n, int k, String[] cmd) {
        // -1, 0 ~ n-1, n까지 (양끝 경계용 sentinel 포함)
        // 삭제는 해당 자리로 이동해서 가능 / 복구는 어디서나 가능
        int[] prev = new int[n + 2];
        int[] next = new int[n + 2];

        for(int i = 0; i < n + 1; i++) {
            prev[i] = i - 1;
            next[i] = i + 1;
        }

        boolean[] deleted = new boolean[n];
        Deque<Integer> deletedStack = new ArrayDeque<>();

       // 실제 행 k -> 배열 인덱스는 k + 1
       int cur = k + 1;

       for(int i = 0; i < cmd.length; i++) {
           // 목적 파싱 (U/D/C/Z 중 하나)
           char type = cmd[i].charAt(0);

           // U/D면 뒤의 숫자를 서브스트링
           // 위/아래로 이동
           if(type == 'U' || type == 'D') {
               // "U 3", "3
               int x = Integer.parseInt(cmd[i].substring(2));
               for(int j = 0; j < x; j++) {
                   // 타입의 U 여부에 따라 이전 연결을 바라볼지 다음 연결을 바라볼지 결정
                   cur = (type == 'U') ? prev[cur] : next[cur];
               }
           } else if(type == 'C') {
               // 삭제
               // p, nx 구하고 → next[p]=nx, prev[nx]=p → deleted 표시 → 스택 push → cur 갱신
               // p = 위에 있는 행 번호
               int p = prev[cur];
               // nx = 아래에 있는 행 번호
               int nx = next[cur];

               // 위 행이 보고 있는 다음을 cur가 아니고 nx로 바꿔라 = 위에 행이 바라보는건 내가 아니고 내 아래행으로 바꿔라
               next[p] = nx;
               // 아래 행이 보고 있는 이전을 cur가 아니고 p로 바꿔라 = 아래에 있는 행이 바라보는건 내가 아니고 내 위에행으로 바꿔라
               prev[nx] = p;

               deleted[cur - 1] = true;
               deletedStack.push(cur);

               // 추가되고 아래 행번호가 n의 갯수를 초과했는지 여부에 따라 p 이전행이냐 : nx 다음행이냐로 구분 (맨 아래 경계)
               cur = (nx == n + 1) ? p : nx;

           } else {
               // 복구
               // 삭제된 스택을 꺼내준다.
               int idx = deletedStack.pop();
               int p = prev[idx];
               int nx = next[idx];

               // 위 행이 다시 idx 가리키고
                next[p] = idx;
                // 아래 행이 다시 idx를 가리키게
                prev[nx] = idx;

                deleted[idx - 1] = false;
           }
       }

       StringBuilder sb = new StringBuilder();
       for(int i = 0; i < n; i++) {
           // 어느 누구의 배열을 바라볼 필요 없이 삭제된 행이 있으면 기록되었을거라 이걸 3항 연산으로 조건 처리
           sb.append(deleted[i] ? 'X' : 'O');
       }

       return sb.toString();
   }


}
