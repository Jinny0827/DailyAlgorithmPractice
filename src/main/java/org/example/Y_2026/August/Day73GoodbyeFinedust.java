package org.example.Y_2026.August;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Day73 안녕 미세먼지
 *
 * 문제 설명 >>
 * R×C 격자에 미세먼지가 퍼져 있고, 공기청정기 2칸(위/아래로 붙어 있음, 같은 열)이 설치되어 있습니다.
 * 1초마다 다음 두 과정이 순서대로 일어납니다.
 *
 *  1) 미세먼지 확산 (모든 칸에서 동시에)
 *
 * 먼지가 있는 칸마다 확산량 = 현재 값 / 5 (버림)만큼 상하좌우 네 방향으로 각각 퍼집니다.
 * 격자 경계나 공기청정기 칸으로는 퍼지지 않습니다.
 * 원래 칸은 확산량 × (실제로 퍼진 방향 수)만큼 줄어듭니다.
 * 모든 칸을 동시에 계산한 뒤 한번에 반영합니다.
 *
 * 2) 공기청정기 작동
 *
 * 위쪽 청정기: 반시계방향으로 순환 — 청정기 칸 → 위로 → 맨 윗행을 오른쪽으로 → 맨 오른쪽열을 아래로 → 청정기가 있는 행을 왼쪽으로(청정기 칸 앞까지)
 * 아래쪽 청정기: 시계방향으로 순환 — 청정기 칸 → 아래로 → 맨 아랫행을 오른쪽으로 → 맨 오른쪽열을 위로 → 청정기가 있는 행을 왼쪽으로(청정기 칸 앞까지)
 * 바람 경로를 따라 먼지가 한 칸씩 밀려 이동하고, 청정기로 들어가는 먼지는 정화되어 사라집니다.
 *
 * T초가 지난 뒤 격자에 남아있는 미세먼지의 총합을 구하세요.
 *
 *
 * 입출력 예시
 *
 * 입력 >>
 *
 * 7 8 1
 * 0 0 0 0 0 0 0 9
 * 0 0 0 0 3 0 0 8
 * -1 0 5 0 0 0 22 0
 * -1 8 0 0 0 0 0 0
 * 0 0 0 0 0 10 43 0
 * 0 0 5 0 15 0 0 0
 * 0 0 40 0 0 0 20 0
 *
 * 출력 >>
 *
 * 188
 *
 */
public class Day73GoodbyeFinedust {

    // 실제 지도
    static int[][] map;

    // 미세먼지 확산량을 담을 next 배열
    static int[][] next;

    public static void main(String[] args) {
        // 0 :  먼지가 없는 칸
        // 양수 (1~1000) 해당 칸의 미세먼지 양
        // -1 : 공청기가 있는 칸(먼지 없음, 항상 -1 고정)
        // R(row) * C(column) 과 T(공청기 가동 시간)을 입력받는다.

        // 입력값이 갯수가 50(최대) * 50(최대) = 2500개 정도, 입력은 딱 한번만 읽으면 끝이므로 Scanner 사용
        Scanner scanner = new Scanner(System.in);
        int result = 0;

        // R * C
        int R = scanner.nextInt();
        int C = scanner.nextInt();

        // T : 공청기 가동시간
        int T = scanner.nextInt();

        // 지도 배열
        map = new int[R][C];

        for(int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                map[i][j] = scanner.nextInt();
            }
        }

        // 미세먼지 확산은 네방향으로 동시에 퍼지고(한 칸이 최대 4개 이웃칸에 영향)
        // 공청 순환은 정해진경로(반시계/시계)를 따라 값이 한 방향으로 한칸씩만 밀려 이동(컨베이어 벨트처럼)
        
        
        // 1단계 공청기 위치 찾기 (map을 순회하면서 -1인 행 번호 두개를 찾아 저장, topRow/bottomRow / 열은 항상 0번이니 따로 저장 X)
        int topRow = 0;
        int bottomRow = 0;
        for(int i = 0; i < R; i++) {
            if(map[i][0] == -1) {
                // 해당 칸이 공청이 있다면
                if(topRow == 0 && bottomRow == 0) {
                    // 공청을 한개도 못찾았다가 찾으면 topRow로 초기화
                    topRow = i;
                }
                
                // 공청기 위치 행
                // 공청을 하나 찾아서 topRow로 초기화했으면 bottomRow로 초기화
                bottomRow = i;
            }
        }

        // 확산과 순환은 한번에 이루어져야한다. (시간 T에 대한..)
        // 4단계 T번 반복하며 diffuse() -> circulate() 순서로 호출
        for(int i = 0; i < T; i++) {
            diffuse();
            circulate(topRow, bottomRow);
        }
        
        // 5단계 결과 합산 (모든 칸을 순회하며 -1(공청칸)이 아닌 값만 더해서 출력)
        for(int i = 0; i < R; i++) {
            for(int j = 0; j < C; j++) {
                if(map[i][j] != -1) {
                    result += map[i][j];
                }
            }
        }

        System.out.println(result);
    }

    // 2단계 미세먼지 확산 함수
    private static void diffuse() {
        // R과 C를 직접 가져오지 않고 R * C로 만들어둔 배열을 통해 length로 가져온다.
        // R = 행의 크기, C는 1행의 열 크기
        int R = map.length;
        int C = map[0].length;
        
        // 같은 크기의 next 배열 만들고 map을 복사(또는 0으로 시작해서 누적)
        next = new int[R][C];

        // map의 모든칸 순회하며 값이 0보다 크면 map[i][j] / 5 (확산량) 를 계산
        // -1이면 공청값으로 계산
        // 상하좌우로 퍼질 확산량을 계산하여 next 배열에 넣는다.
        for(int i = 0; i < R; i++) {
            for(int j = 0; j < C; j++) {
                if(map[i][j] == -1) {
                    next[i][j] = -1;
                } else if(map[i][j] > 0) {
                    int amount = map[i][j] / 5;
                    int spreadCount = 0;

                    // 4방향(상,하,좌,우) 확인하여 각방향이 격자 안이고 -1이 아니면 amount 값 합산
                    int[] dx = {-1, 1, 0, 0};
                    int[] dy = {0, 0, -1, 1};

                    for(int k = 0; k < 4; k++) {
                        // x는 행 (위, 아래)
                        // i가 작아짐 = 위, 커짐 = 아래 칸 이동
                        int ni = i + dx[k];
                        // y는 열 (좌, 우)
                        // j가 작아짐 = 좌, 커짐은 = 우측 칸으로 이동
                        int nj = j + dy[k];

                        // 격자 범위 안인지 확인 (0보다 크고 최대 위아래/양옆 값인 R과 C를 각각 넘지 않는 범위)
                        if(ni >= 0 && ni < R && nj >= 0 && nj < C) {
                            if(map[ni][nj] != -1) {
                                // 확산량을 next 배열 지도에 넣어주고
                                next[ni][nj] += amount;
                                
                                // 몇 방향까지 확산되었는지 카운트 (공청이 있을수도있고 벽이 있을수도 있으므로)
                                spreadCount++;
                            }
                        }
                    }

                    // 자기칸 남는값 계산
                    // 원래 칸은 확산량 × (실제로 퍼진 방향 수)만큼 줄어듭니다.
                    next[i][j] += map[i][j] - (amount * spreadCount);
                }
            }
        }
        
        map = next;
    }

    // 3단계 공청기 순환 함수
    private static void circulate(int topRow, int bottomRow) {

        // map 의 오른쪽끝, 아래끝 변수 선언
        int R = map.length;
        int C = map[0].length;

        // 위쪽은 반시계, 아래쪽은 시계방향 경로를 각각 리스트나 좌표 순서로 정의
        // 경로의 끝(청정기 쪽)부터 값을 밀어내는 방식으로 한칸씩 이동(직접 배열 값을 옮기면된다, 임시배열 불필요)

        // 공기 순환은 위쪽이건 아래쪽이건 벽쪽으로만 돈다. -> 안쪽 행은 X
        // topRow는 바로 윗칸부터 반시계방향으로 돌면서 본인까지돌아온다.
        // bottomRow는 바로 아래칸부터 시계방향으로 돌면서 본인까지 돌아온다.

        // 좌표리스트를 만들어 순환시킨다.
        List<int[]> topCycle = new ArrayList<>();
        List<int[]> bottomCycle = new ArrayList<>();

        // topRow의 4가지 방향 사이클

        // 1. 청정기 바로 윗칸부터 맨윗행까지(0,0)까지 0열을 따라 위로 축적
        // 청정기 바로 위 = topRow - 1, 0 행까지 줄여나간다.
        for(int i = topRow - 1; i >= 0; i--) {
            topCycle.add(new int[] {i, 0});
        }
        
        // 2. 맨윗행따라 왼쪽에서 오른쪽 끝까지
        // 0,0 구간이 이미 올라오면서 처리되었기 때문에 (0, 1)부터 시작
        for (int j = 1; j < C; j++) {
            topCycle.add(new int[] {0, j});
        }
        
        // 3. 맨오른쪽열을 따라 아래로, topRow(공청기 위치 행)까지
        for (int k = 1; k <= topRow; k++) {
            topCycle.add(new int[] {k, C - 1});
        }

        // 4. topRow행을 따라 오른쪽에서 왼쪽으로 (청정기 바로 앞(1열)까지)
        for (int z = C - 2; z >= 1; z--){
            topCycle.add(new int[] {topRow, z});
        }


        // bottomRow의 4가지 방향 사이클

        // 1. 청정기 바로 아래칸부터 맨 아랫행(R-1)까지, 0열을 따라 아래로
        for(int i = bottomRow + 1; i < R; i++) {
            bottomCycle.add(new int[] {i, 0});
        }

        // 2.맨 아래행을 따라 왼쪽에서 오른쪽 끝까지
        for(int j = 1; j < C; j++) {
            bottomCycle.add(new int[] {R - 1, j});
        }

        // 3. 맨 오른쪽열을 따라 위로, topRow까지
        for(int k = R - 2; k >= bottomRow; k--) {
            bottomCycle.add(new int[] {k, C - 1});
        }
        
        // 4. bottomRow 행을 따라 오른쪽에서 왼쪽으로, 청정기 앞까지
        for (int z = C - 2; z > 0; z--) {
            bottomCycle.add(new int[] {bottomRow , z});
        }


        // 실제로 값을 미는 로직
        // 뒤에서부터 앞의 값을 당겨오고 맨 마지막에 첫 칸만 0으로 처리
        pushCycle(topCycle);
        pushCycle(bottomCycle);

    }

    private static void pushCycle(List<int[]> cycle) {
        // 1. 리스트 끝(청정기 바로 앞 칸)부터 시작해서, 두번째 칸까지 거꾸로 진행 -> (topRow 기준)반시계방향, (borromRow 기준) 정시계 방향
        for (int idx = cycle.size() - 1; idx >= 1; idx--) {
            // 방향 이전의 인덱스와 이전 인덱스의 이전 인덱스 변수
            int[] cur = cycle.get(idx);
            int[] prev = cycle.get(idx - 1);
            
            // 이전값으로 밀기
            map[cur[0]][cur[1]] = map[prev[0]][prev[1]];
        }
        
        
        // 2. 다 옮기고 난 뒤, 맨 마지막에 첫 칸만 0 처리
        int[] first = cycle.get(0);
        map[first[0]][first[1]] = 0;
    }

}
