package org.example.Y_2026.August;

import java.util.Scanner;

/**
 * Day70 구슬 찾기
 *
 * N은 홀수
 * 목표: 무게가 정중앙((N+1)/2번째)이 될 수 없는 구슬의 개수를 구하는 것
 * 어떤 구슬이 중간이 될 수 없으려면: 자신보다 확실히 무거운 구슬 수가 (N+1)/2개 이상 이거나, 자신보다 확실히 가벼운 구슬 수가 (N+1)/2개 이상이면 됨
 *
 */
public class Day70FindingtheBids {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 0;

        // 구슬 갯수
        int N = scanner.nextInt();
        // 구슬과 구슬간의 관계 갯수
        int M = scanner.nextInt();

        boolean[][] heavier = new boolean[N + 1][N + 1];

        // heavier[a][b] == true → a가 b보다 무겁다
        for (int i = 0; i < M; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            heavier[a][b] = true;
        }

        // 삼중 for문을 통해 구슬 3개를 각각 관계 정립시켜주는 목적 a -> b, a -> c, b -> c, d -> a, d -> c ....
        // 가운데 숫자가 같고 끝숫자와 첫숫자에 대한 비교
        // for문은 i -> j-> k 순이므로 제일 안쪽이 i, 인덱스 범위는 1부터 시작
        for (int k = 1; k <= N; k++) {
            for (int j = 1; j <= N; j++) {
                for (int i = 1; i <= N; i++) {
                    if (heavier[i][k] && heavier[k][j]) {
                        heavier[i][j] = true;
                    }
                }
            }
        }
        
        // N + 1을 하는 이유 == 반올림의 여지를 없애기 위해 (N이 홀수이기 때문)
        // 구슬 N개 중 무게가 정중앙에 위치((N+1)/2번째)이 될 수 없다고 확정할 수 있는 구슬 갯수
        // 확정 할 수 없다 == heavier[][]를 통해 x보다 확실히 무거운 구슬 수가 (N+1)/2 개 이상이거나 
        // 확실히 가벼온 구슬 수 (N+1)/2개 이상이면 x개는 중간이 될 수 없음
        for(int x = 1; x <= N; x++) {
            // 확실히 무겁거나 확실히 가벼운 구슬 갯수가 중간값 이상이면 중간이 될 수 없음

            // 최대값과 최소값에 대한 중간값 차감에 대한 카운트
            int heavierCnt = 0;
            int lighterCnt = 0;

            // 중간 인덱스
            int mid = (N + 1) / 2;
            for (int y = 1; y <= N; y++) {
                // x == y 는 비교군이 같은 값이면 비교 X
                if(x == y) {
                    continue;
                }

                // y가 x보다 무겁다 == x보다 무거운 구슬 갯수
                if(heavier[y][x]) {
                    heavierCnt++;
                }

                // x가 y보다 무겁다 == x보다 가벼운 구슬 갯수
                if(heavier[x][y]) {
                    lighterCnt++;
                }
            }

            // y가 x보다 무거운 구슬 갯수가 정중앙 인덱스 이상일때
            if(heavierCnt >= mid) {
                count++;
            }
            
            // x가 y보다 무거운 구슬 갯수가 정중앙 인덱스 이상일때
            if(lighterCnt >= mid) {
                count++;
            }
        }

        System.out.println(count);
    }

}
