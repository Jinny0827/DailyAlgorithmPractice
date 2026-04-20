package org.example.Y_2026.April.day06;

/**
 * 타겟 넘버
 *
 * n개의 음이 아닌 정수가 있습니다.
 * 이 수를 적절히 더하거나 빼서 타겟 넘버를 만들려고 합니다.
 *
 * 사용할 수 있는 숫자가 담긴 배열 numbers, 타겟 넘버 target이 매개변수로 주어질 때,
 * 숫자를 더하고 빼서 타겟 넘버를 만드는 방법의 수를 return 하세요.
 */
public class Day06TargetNumber {
    int count = 0;

    public int solution(int[] numbers, int target) {
        dfs(numbers, target, 0, 0);
        return count;
    }

    void dfs(int[] numbers, int target, int index, int sum) {
        
        // 현 위치와 사용자 입력 배열의 맥스 공간이 일치 시
        if(index == numbers.length) {
            // 사용자가 입력한 연산의 최종 값 과 현재 값의 비교
           if(target == sum) {
               count++;
           }

           return;
        }

        // 현 위치가 배열 공간의 중간인 경우 (재귀로 호출)
        dfs(numbers, target, index + 1, sum + numbers[index]);
        dfs(numbers, target, index + 1, sum - numbers[index]);
    }

}
