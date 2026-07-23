package org.example.Y_2026.July;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Day55 열혈강호 (이분매칭)
 *
 * 강호네 회사에는 직원이 N명이 있고, 해야할 일이 M개가 있다.
 * 직원은 1번부터 N번까지 번호가 매겨져 있고, 일은 1번부터 M번까지 번호가 매겨져 있다.
 *
 * 각 직원은 한 개의 일만 할 수 있고, 각각의 일을 담당하는 사람은 1명이어야 한다.
 *
 * 각각의 직원이 할 수 있는 일의 목록이 주어졌을 때, M개의 일 중에서 최대 몇 개를 할 수 있는지 구하는 프로그램을 작성하시오.
 *
 *
 * 입력 >>
 * 첫째 줄에 직원의 수 N과 일의 개수 M이 주어진다. (1 ≤ N, M ≤ 1,000)
 * 둘째 줄부터 N개의 줄의 i번째 줄에는 i번 직원이 할 수 있는 일의 개수와 할 수 있는 일의 번호가 주어진다.
 *
 * 출력 >>
 * 첫째 줄에 강호네 회사에서 할 수 있는 일의 개수를 출력한다.
 *
 */
public class Day55BipartiteMatching {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();

        // 미리 더미 하나를 넣어놓는다 -> 인덱스를 1부터 채울 수 있게
        List<List<Integer>> canDo = new ArrayList<>();
        canDo.add(new ArrayList<>());

        // 사원별 업무 갯수와 목록 입력받기
        // 사원 수만큼 목록 리스트 갯수 삽입
        for (int i = 1; i <= N; i++) {
            canDo.add(new ArrayList<>());

            // 해당 직원의 업무 목록 삽입 (갯수(cnt) 만큼)
            int cnt = scanner.nextInt();
            for (int j = 0; j < cnt; j++) {
                // i번째 사원에게 삽입
                canDo.get(i).add(scanner.nextInt());
            }
        }

        // 업무별 담당자 지정
        // 직원 N명을 한 명씩 순서대로 시도해서, 배정에 성공(canAssign() == true)할 때마다 count를 올리고, 최종 count가 답
        int[] owner = new int [M + 1];
        int count = 0;

        for(int k = 1; k <= N; k++) {
            boolean[] visited = new boolean[M + 1];
            if(canAssign(k, canDo, owner, visited) == true){
                count++;
            }
        }

        System.out.println(count);
    }

    private static boolean canAssign(int emp, List<List<Integer>> canDo, int[] owner, boolean[] visited) {
        // 1. 현재 직원(emp)이 할 수 있는 업무 목록을 하나씩 꺼내서 반복
        for (int job : canDo.get(emp)) {
            // 2. 이번 탐색에서 이미 검토한 job이면 회피
            if (visited[job]) {
                continue;
            }

            // 3. 검토했다고 표시
            visited[job] = true;
            
            // 4. 이 job이 비어있거나 (owner[job] == 0 / 담당자가 없음) 
            // 이미 주인이 있어도 그 주인이 다른 job을 할 수 있는지 확인(다른 job으로 이동하는 재귀 호출)
            if (owner[job] == 0 || canAssign(owner[job], canDo, owner, visited)) {
                // 5. 이 job의 주인을 emp로 교체
                owner[job] = emp;
                return true; // 배정 성공
            }
        }
        
        // 6. 모든 후보를 다 시도했는데도 자릴 못만들면 실패
        return false;
    }       
    
}
