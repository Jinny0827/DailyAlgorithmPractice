package org.example.Y_2026.June;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *  Day39 요세푸스 문제
 *
 *  N명이 원형으로 앉아 있다.
 *  1번부터 N번까지 번호가 매겨져 있고, 순서대로 K번째 사람을 제거한다.
 *  제거된 순서를 출력하라.
 */
public class Day39Josephuse {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        
        // N개의 숫자
        int N = scanner.nextInt();

        // K번째 제거할 시퀀스
        int K = scanner.nextInt();

        // Queue(LinkedList) 를 쓰는 게 핵심
        // LinkedList = 다음 노드주소, 값 이 들어있는 리스트 (노드 - 다음노드 연결리스트)
        // N까지의 값을 넣어준다.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            queue.add(i);
        }

        sb.append("<");

        // queue가 빌때까지 반복
        while(!queue.isEmpty()) {
            // K-1번을 앞에서 빼서 뒤로 넣어준다. (=K번째 앞까지 패스)
            for (int i = 0; i < K - 1; i++) {
                // poll하면서 add하며 넣어준다.
                queue.add(queue.poll());
            }
            
            // K번째 제거하고 정답에 기록
            int removed = queue.poll();
            sb.append(removed);
            if(!queue.isEmpty()) {
                sb.append(", ");
            }
        }
        sb.append(">");

        System.out.println(sb);
    }

}
