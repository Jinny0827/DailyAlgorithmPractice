package org.example.Y_2026.August;

import java.util.*;

/**
 * Day66 아기 상어
 * N×N 공간에 아기 상어와 물고기들이 있다.
 * 아기 상어는 처음 크기 2, 초당 상하좌우로 한 칸씩 이동.
 * 자기보다 작은 크기의 물고기만 먹을 수 있고, 같은 크기는 지나갈 수만 있음(못 먹음), 더 큰 크기는 지나갈 수 없음.
 * 이동 규칙: 먹을 수 있는 가장 가까운 물고기로 이동(최단거리 우선 → 거리 같으면 위쪽 행 우선 → 그다음 왼쪽 열 우선).
 * 자기 크기와 같은 수의 물고기를 먹으면 크기가 1 증가.
 * 더 이상 먹을 물고기가 없으면 종료.
 * 아기 상어가 몇 초 동안 움직였는지 구하라.
 *
 * 입력
 *
 * 첫째 줄: N (2 ≤ N ≤ 20)
 * N개의 줄: 공간 정보 (0: 빈칸, 1~6: 물고기 크기, 9: 아기 상어 초기 위치, 시작 크기는 2)
 *
 * 출력
 *
 * 아기 상어가 물고기를 더 이상 먹을 수 없을 때까지 걸린 시간(초)
 */
public class Day66BabyShark {

    static int time;
    static int eatCount;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();

        int[][] map = new int[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                map[i][j] = scanner.nextInt();
            }
        }

        // 상어의 위치 찾기 X, Y -> 위치 찾아 좌표 저장 후 0으로 바꿔야함 (차후 계산 시 빈칸으로 봐야함)
        int sharkX = 0;
        int sharkY = 0;

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(map[i][j] == 9) {
                    sharkX = i;
                    sharkY = j;
                    //빈칸으로 변경
                    map[i][j] = 0; 
                }
            }
        }

        // 초기 상어 사이즈와 먹은 먹이 물고기 갯수에 대한 변수 준비
        int sharkSize = 2;
        eatCount = 0;
        // 움직인 시간
        time = 0;
        
       while(true) {
           // BFS 함수
           int[] result = BFS(map, sharkX, sharkY, sharkSize, N);
           if (result == null) {
               break;
           }

           time += result[0];
           sharkX = result[1];
           sharkY = result[2];
           // 먹은 먹이칸은 0 처리
           map[sharkX][sharkY] = 0;
           eatCount++;

           if(eatCount == sharkSize) {
               sharkSize++;
               eatCount = 0;
           }
       }

        // 상어가 N칸의 마지막까지 가는것이 아니고 먹이를 다먹어치울때까지의 시간
        System.out.println(time);
    }

    // BFS는 map, 상어 위치, 현재 크기를 받아서
    // 결과로 {거리,x,y} 배열 리턴
    private static int[] BFS(int[][] map, int sharkX, int sharkY, int sharkSize, int N) {
        // 이번 BFS 탐색에서 이미 방문한 칸 체크 (중복 방문 방지)
        boolean[][] visited = new boolean[N][N];
        // BFS 탐색용 큐 -> 원소는 {x 좌표, y좌표, 상어 시작점으로부터 거리}
        Queue<int[]> queue = new LinkedList<>();
        
        // 상어의 현재 위치를 시작점으로 큐에 삽입, 거리 0
        queue.add(new int[] {sharkX, sharkY, 0});
        // 시작점은 방문 처리
        visited[sharkX][sharkY] = true;

        // 먹을 수 잇는 물고기 후보들을 모아두는 리스트
        // 원소는 {거리, x좌표, y좌표} -> 나중에 우선 순위(거리 -> 행 -> 열) 비교용
        List<int[]> candidates = new ArrayList<>();

        // 상하좌우 이동 방향 벡터 (행 변화량, 열 변화량)
        // 순서를 위쪽부터 두면 나중에 후보 비교 시 실수 줄이는데 유용
        int[] dx = {-1, 1, 0, 0}; // 위 아래 - -
        int[] dy = {0, 0, -1, 1}; // - - 좌 우

        while(!queue.isEmpty()) {
            int[] cur = queue.poll();

            for(int dir = 0; dir < 4; dir++) {
                // 다음 x칸의 좌표
                int nx = cur[0] + dx[dir];
                // 다음 y칸의 좌표
                int ny = cur[1] + dy[dir];

                // 범위 벗어나거나 이미 방문했으면 스킵
                if(nx < 0 || ny < 0 || nx >= N || ny >= N || visited[nx][ny]) {
                    continue;
                }

                // 상어보다 큰 물고기는 지나갈 수 없음
                if(map[nx][ny] > sharkSize) {
                    continue;
                }

                visited[nx][ny] = true;
                // 큐에 추가 -> 거리는 + 1 처리
                queue.add(new int[] {nx, ny, cur[2] + 1 });
                
                // 상어보다 작은 물고기가 있는 칸 -> 먹을 수 있는 후보
                if(map[nx][ny] > 0 && map[nx][ny] < sharkSize) {
                    candidates.add(new int[] {cur[2] + 1, nx, ny});
                }
            }
        }


        if(candidates.isEmpty()) {
            // 먹을 물고기 없음
            return null;
        }

        // 후보 중 거리 최소 -> 행 (x) 최소 -> 열(y) 최소 순으로 정렬해서 첫번째 반환
        candidates.sort((a, b) -> {
            if(a[0] != b[0]) {
                // 거리 비교
                return a[0] - b[0];
            }
            if(a[1] != b[1]) {
                // 행 비교
                return a[1] - b[1];
            }

            // 열 비교
            return a[2] - b[2];
        });

        return candidates.get(0);
    }

}
