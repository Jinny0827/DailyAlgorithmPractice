package org.example.Y_2026.July;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Day46 회의실 배정
 *
 * 한 개의 회의실이 있고, N개의 회의에 대해 회의실 사용표를 만들려 한다.
 * 각 회의 i는 시작시간 S(i)와 종료시간 E(i)가 주어지며,
 * 겹치지 않게 회의실을 사용할 수 있는 최대 회의 개수를 구하라.
 *
 * 회의는 한 번 시작하면 중간에 중단할 수 없다.
 * 두 회의가 같은 시간대(끝나는 시간 = 다음 시작 시간)에 겹칠 경우, 먼저 끝나는 회의가 끝나는 시간에 다음 회의가 시작될 수 있다.
 */
public class Day46ConferenceRoomAllocation {

    public static void main(String[] args) {

        // 겹치는 회의는 동시에 선택할 수 없고, 그중 최대한 많은 회의를 진행

        Scanner scanner = new Scanner(System.in);

        // N개의 회의
        int N = scanner.nextInt();

        // 회의 시작은 N개 회의 종료 두 개
        // meetings[i][0] = 시작, meetings[i][1] = 종료
        int[][] meetings = new int[N][2];

        // 첫번째 회의 [시작시간, 종료시간] 인풋
        for (int i = 0; i < N; i++) {
            for(int j = 0; j < 2; j++) {
                meetings[i][j] = scanner.nextInt();
            }
        }

        // 종료시간이 빠른 회의부터 봐야 다음 회의를 위한 여유가 최대화됨
        // 종료 시간이 같을 시작 시간 정렬하는 이유 : 같은 시간에 끝나면 더 짧은 회의를 픽 (비교 로직 안정화)
        Arrays.sort(meetings, (a, b) -> {
            
            // 종료 시간이 같지 않으면
            if (a[1] != b[1]) {
                // 종료 시간 오름차순
                return a[1] - b[1];
            }

            // 시작 시간 오름 차순
            return a[0] - b[0];
        });

        // 선택한 회의의 종료시간을 순회
        int count = 0;
        int lastEnd = 0;

        for (int i = 0; i < N; i++) {
            if (meetings[i][0] >= lastEnd) {
                // 효율적인 정렬 시 하루에 열릴 수 있는 회의의 갯수를 카운트
                count++;
                // 다음 순회를 위해 다음 순회 비교 종료 시간을 현재 종료시간으로 삽입
                lastEnd = meetings[i][1];
            }
        }

        System.out.println(count);
    }

}
