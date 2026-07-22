package org.example.Y_2026.July;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Day52 공유기 설치
 *
 * 크기가 N인 조밀한 마을에 집이 일직선으로 있고, 각 집의 좌표가 주어집니다.
 * 이 마을에 공유기 C개를 설치해야 합니다.
 * 각 집에는 공유기를 최대 1개 설치할 수 있고, 가장 인접한 두 공유기 사이의 거리를 최대로 하고 싶습니다.
 * C개의 공유기를 적절히 설치했을 때 얻을 수 있는 "가장 인접한 두 공유기 사이 거리"의 최댓값을 구하세요.
 *
 * 이진탐색을 사용하는 이유 = 중간 값까지만 성공할거란 확신이 있을 경우 뒷 결과를 날리겠다는 생각으로 가운데를 쳐서 아랫부분만 계산
 *
 */
public class Day53InstallingRouter {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // N = 마을이 나열되어있는 X축 크기
        int N = Integer.parseInt(st.nextToken());

        // C = 공유기 설치 갯수
        int C = Integer.parseInt(st.nextToken());

        // 마을 간 좌표 입력 받기
        int[] map = new int[N];
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            map[i] = Integer.parseInt(st.nextToken());
        }

        // 1차원 배열은 따로 계산식 필요 없이 배열 객체만 집어넣음
        // primitive 배열엔 못 쓰고, 꼭 쓰고 싶으면 Integer[]로 박싱
        Arrays.sort(map);

        // 간격 d를 이진탐색으로 탐색
        // 첫 값 1
        int lo = 1;
        // 가장 멀리 떨어진 두 집의 거리 = hi
        int hi = map[N-1] - map[0];
        int answer = 0;

        while(lo <= hi) {
            int mid = (lo + hi) / 2;
            // 설치 갯수를 공유기 갯수와 비교
            if(count(map, mid) >= C) {
                // 성공 시 출력 값 갱신
                answer = mid;
                // 최소 값을 mid + 1
                lo = mid + 1;
            } else {
                // 실패 시 최댓값을 mid - 1 값으로 갱신
                hi = mid - 1;
            }
        }

        // 결과 값 출력
        System.out.println(answer);
    }

    private static int count(int[] map, int mid) {
        // 첫번째 집에 무조건 설치 -> 설치 개수 1, 마지막 설치 위치 변수를 map[0]으로 저장
        // 산출하여 반환할 C와 비교할 설치된 공유기 갯수
        int count = 1;
        // 마지막으로 설치된 공유기 위치
        int lastPos = map[0];

        // 두 번째 집부터 끝까지 순회하면서
        // 현재 집 좌표 - 마지막 설치 위치 >= mid면 -> 설치 (개수 + 1)
        for (int i = 1; i < map.length; i++) {
            if(map[i] - lastPos >= mid) {
                count++;
                lastPos = map[i];
            }
        }
        
        // 다 돌면 설치 개수 반환
        return count;
    }

}
