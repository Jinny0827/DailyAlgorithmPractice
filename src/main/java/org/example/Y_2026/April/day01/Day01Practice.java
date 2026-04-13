package org.example.Y_2026.April.day01;

import java.util.Arrays;

/**
 * 프로그래머스 Level 1 | #42748 - K번째수
 *
 *
 * 배열 array의 i번째 숫자부터 j번째 숫자까지 자르고 정렬했을 때
 * k번째에 있는 수를 구하세요.
 */
public class Day01Practice {

    public static void main(String[] args) {

        // 테스트 기반
        int[] array = {1, 5, 2, 6, 3, 7, 4};
        int[][] commands = {{2,5,3}, {4,4,1}, {1,7,3}};

        int[] answer = new int[commands.length];

        for (int idx = 0; idx < commands.length; idx++) {
            int[] cmd = commands[idx];
            int i = cmd[0];
            int j = cmd[1];
            int k = cmd[2];


            int[] sliced = Arrays.copyOfRange(array, i-1, j);
            Arrays.sort(sliced);
            answer[idx] = sliced[k-1];

        }

        System.out.println(Arrays.toString(answer));

    }

}
