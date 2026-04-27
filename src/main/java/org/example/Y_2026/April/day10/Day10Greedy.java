package org.example.Y_2026.April.day10;

import java.util.HashSet;

/**
 * Greedy
 *
 *
 * 체육복 도난 사건 발생
 * 여벌체육복은 앞, 뒤 번호의 학생에게만 빌려줄 수 있다.
 * 체육복을 입지 못하면 체육수업을 들을 수 없다.
 * 체육 수업을 들을 수 있는 학생의 최대 값을 구하라
 */
public class Day10Greedy {

    /**
     * n = 5          → 1번 ~ 5번 학생 존재
     * lost = [2, 4]  → 2번, 4번이 체육복 도난
     * reserve = [1, 3, 5] → 1번, 3번, 5번이 여벌 보유
     */
    public int solution(int n, int[] lost, int[] reserve) {
        // 중복 없는 집합
        HashSet<Integer> lostSet = new HashSet<>();

        // lostSet과 reserveSet을 통해 중복 없는 손실학생, 예비학생 객체화
        for (int l : lost) {
            lostSet.add(l);
        }

        // 예비 체육복이 있는 학생에 대한 객체화 (예비체육복은 단 한개)
        for (int r : reserve) {
            // lost와 reserve가 동일하다면 제거 = 예비 체육복까지 뺏겨서 본인 사용할 체육복만 존재
            if(lostSet.contains(r)) {
                lostSet.remove(r);
            } else if(lostSet.contains(r - 1)) {
                // 예비 체육복이 있는 친구가 앞뒤 친구에게 빌려주는 로직
                // r 이라는 예비 체육복이 있는 친구의 앞뒤로 잃어버린 친구가 있나 확인
                lostSet.remove(r - 1);
            } else if (lostSet.contains(r + 1)) {
                lostSet.remove(r + 1);
            }

        }

        // 총 인원에서 체육복이 없는 친구의 갯수를 리턴해준다.
        return n - lostSet.size();
    }

}
