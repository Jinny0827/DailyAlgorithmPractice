package org.example.Y_2026.May;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * Day22 줄 세우기
 *
 * N명의 학생을 일렬로 세우려 한다.
 * 일부 학생들의 키를 비교한 결과 M개의 정보가 주어질 때, 가능한 순서 중 하나를 출력하라.
 * 비교 정보 A B 는 "A가 B 앞에 서야 한다" 는 의미다.
 *
 */
public class Day22LineUp {

    public static void main(String[] args) throws IOException {
        // 입력 방식 = M N / A B / A B / ... / An Bn

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            // 학생의 명수만큼 리스트 공간 생성 (숫자에 대한 관계의 숫자들 리스트 나열)
            graph.add(new ArrayList<>());
        }

        // indegree[i] = i 앞에 서야 하는 사람 수 (카운팅)
        int[] indegree = new int[N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(bf.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            // A에 대해 B 관계를 입력
            graph.get(A).add(B);
            // B 앞에 A가 몇 개있는지 카운트
            indegree[B]++;
        }

        // 본인(i) 앞에 숫자관계가 없는 배열부터 queue에 담기 (제일 먼저 설 수 있는 사람)
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if(indegree[i] == 0) {
                queue.add(i);
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            sb.append(cur).append(" ");

            for(int next : graph.get(cur)) {
                indegree[next]--; // 앞 사람 한명이 처리됨
                if (indegree[next] == 0) { // 앞이 다 빠지면
                    queue.add(next); // 큐에 추가
                }
            }
        }

        System.out.println(sb);



    }

}
