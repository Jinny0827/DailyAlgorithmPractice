package org.example.Y_2026.June;

import java.util.Scanner;

/**
 * Day31 N과 M (1) BackTraking
 *
 * 자연수 N과 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.
 * -> 1부터 N까지 자연수 중에서 중복 없이 M개를 고른 수열
 */

public class Day31Backtracking {
    // 1 ~ N 까지의 중복되지 않는 M개의 자연수의 수열을 출력 (4 2 ->  1 2, 1 3, 1 4, 2 1, 2 2, ... , 4 3)

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int M = scanner.nextInt();

        int[] arr = new int[M];
        boolean[] visited = new boolean[N + 1];

        dfs(0, N, M, arr, visited);
    }

    static void dfs(int depth, int N, int M, int[] arr, boolean[] visited) {
        if(depth == M) {
            // 출력
            StringBuilder sb = new StringBuilder();

            // M개의 중복되지 않는 자연수 출력
            for (int j = 0; j < M; j++) {
                // 배열의 값을 호출
                sb.append(arr[j]);
                //
                if(j < M - 1) {
                    sb.append(" ");
                }
            }
            System.out.println(sb);
            return;
        }

        for(int i = 1; i <= N; i++) {
            // i를 아직 안 썼으면 -> arr에 넣고 -> 재귀 -> 되돌리기
            if(!visited[i]) {
                arr[depth] = i;
                
                // i 사용 / 다음 뎁스 재귀 / 미사용
                visited[i] = true;
                dfs(depth + 1, N, M, arr, visited);
                visited[i] = false;
            }
        }
    }
}


/**
 *
 * dfs(depth=0)
 * ├── i=1: arr=[1,_], visited[1]=true
 * │     dfs(depth=1)
 * │     ├── i=1: visited[1]=true → 스킵
 * │     ├── i=2: arr=[1,2], visited[2]=true
 * │     │     dfs(depth=2) → depth==M → 출력 "1 2"
 * │     │     visited[2]=false
 * │     ├── i=3: arr=[1,3], visited[3]=true
 * │     │     dfs(depth=2) → depth==M → 출력 "1 3"
 * │     │     visited[3]=false
 * │     └── i=4: arr=[1,4], visited[4]=true
 * │           dfs(depth=2) → depth==M → 출력 "1 4"
 * │           visited[4]=false
 * │     visited[1]=false ← 되돌리기
 * │
 * ├── i=2: arr=[2,_], visited[2]=true
 * │     dfs(depth=1)
 * │     ├── i=1: arr=[2,1] → 출력 "2 1"
 * │     ├── i=2: visited[2]=true → 스킵
 * │     ├── i=3: arr=[2,3] → 출력 "2 3"
 * │     └── i=4: arr=[2,4] → 출력 "2 4"
 * │     visited[2]=false
 * │
 * ├── i=3: arr=[3,_] → "3 1" "3 2" "3 4"
 * └── i=4: arr=[4,_] → "4 1" "4 2" "4 3"
 */
