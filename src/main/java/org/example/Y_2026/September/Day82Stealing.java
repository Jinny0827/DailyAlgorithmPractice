package org.example.Y_2026.September;

/** 
 * Day82 도둑질
 *
 * 문제 설명
 * 집들이 원형으로 배치되어 있고, 각 집에는 훔칠 수 있는 금액이 있습니다.
 * 인접한 두 집을 동시에 털면 경보가 울리므로(첫 번째 집과 마지막 집도 인접으로 간주), 경보 없이 훔칠 수 있는 최대 금액을 구하세요.
 *
 * 입출력 예시
 *
 * money	        return
 * [1, 2, 3, 1]	    4
 *
 * 제한 조건
 *
 * 금고(집)의 수: 3 이상 1,000,000 이하
 * 각 집에 있는 돈: 0 이상 1,000 이하의 정수
 *
 */
public class Day82Stealing {

    public static void main(String[] args) {
        int[] inputMoney = {1, 2, 3, 1};
        System.out.println(solution(inputMoney));
    }

    // 원을 억지로 일자로 펴서 배열 형태 계산 
    // 단 마지막 집을 없는 셈 치고 끊기 / 첫집을 아예 없는 셈 치고 끊기
    public static int solution(int[] money) {
        // 들어온 돈의 갯수
        int N = money.length;

        // 마지막 집 없는 계산과 첫 집 없는 계산을 최대치로 계산해 더 큰 값 반환(최대값)
        return Math.max(
                robLinear(money, 0, N - 2),
                robLinear(money, 1, N - 1)
        );
    }

    // 매칸마다 스킵/훔침을 비교해서 그때그때 최적인 조합을 스스로 찾아가는 것
    private static int robLinear(int[] money, int start, int end) {
        //1. prev1, prev2 두 변수를 0으로 시작
        // prev1 = 바로 직전 집 (i - 1)까지 봤을때의 최대 값
        int prev1 = 0;
        // prev2  = 그보다 한칸 더 전, 즉 두 칸 전 집 (i - 2)까지 봤을 때의 최대값
        int prev2 = 0;

        // prev1,2가 왜 필요함? -> 현재 집(i)에서 선택이 갈린다
        // 이번 집을 스킵한다. (방금까지의 최고 기록을 그대로 이어받는다.)
        // 이번 집을 훔친다. (바로 옆집(i - 1)은 절대 못 훔치는 상태가 되어야한다.)

        //2. start부터 end까지 돌면서, 매 칸마다 "스킵(prev1)" vs "훔침(prev2+money[i])" 중 큰 값을 cur로 정하기
        // 인접 집을 제외한다는 조건을 만족시키기 위해 2칸씩 건너 뛰진 않음
        for(int i = start; i <= end; i++) {
            int cur = Math.max(prev1, prev2 + money[i]);

            //3. prev2 = prev1, prev1 = cur로 갱신
            prev2 = prev1;
            prev1 = cur;
        }

        //4. 루프 끝나면 prev1 반환
        return prev1;
    }

}
