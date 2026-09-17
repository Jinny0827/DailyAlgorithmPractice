package org.example.Y_2026.September;

import java.awt.print.Pageable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Day87 다각형의 면적
 * 
 * 기하 (Computational Geometry) - 좌표 계산
 *
 * - 언제 쓰나?
 * 점/다각형의 넓이, 방향, 교차 여부 등을 좌표만을 판별해야 할때
 *
 * - 핵심 원리
 * 벡터의 외적(Cross Product)을 이용해 세 점의 회전 방향이나 넓이를 계산 (Shoelace Formula = 신발끈 공식)
 * 
 * - 시간 복잡도
 * 보통 O(N) ~ O(N log N)
 *
 * 문제 설명:
 * 2차원 평면 위의 N개의 점으로 이루어진 다각형이 있다.
 * 이 다각형의 면적을 구하는 프로그램을 작성하시오. (점은 시계방향 또는 반시계방향 순서로 주어진다)
 *
 * 입출력 예시:
 * 입력
 *
 * 3
 * 0 0
 * 1 1
 * 1 0
 *
 * 출력
 *
 * 0.5
 *
 */
public class Day87AreaOfaPolygon {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 정점의 갯수
        int N = Integer.parseInt(br.readLine().trim());

        // 좌표 저장용 배열 x,y
        int[] x = new int[N];
        int[] y = new int[N];

        // 누적합의 변수 (좌표 곱셈값이 커질수 있어 int가 아닌 long 사용)
        long sum = 0;

        for(int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            x[i] = a;
            y[i] = b;
        }


    }

}
