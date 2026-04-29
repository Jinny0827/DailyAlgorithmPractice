package org.example.Y_2026.April.day12;


import java.util.HashSet;

/**
 * 소수 찾기
 *
 * 한자리 숫자가 적힌 종이 조각이 여러 장 있습니다.
 * 이 숫자들로 만들 수 있는 모든 숫자 조합 중에서 소수의 개수를 구하세요.
 */
public class Day12Decimal {

    public static void main(String[] args) {
        Day12Decimal day12Decimal = new Day12Decimal();
        int result = day12Decimal.solution("17");
        System.out.println(result);
    }

    public int solution(String numbers) {
        HashSet<Integer> result = new HashSet<>();
        char[] chars = numbers.toCharArray();
        boolean[] visited = new boolean[chars.length];

        // 모든 숫자에 대한 조합 리스트
        dfs(chars, visited, "", result);

        // 소수 검사
        int count = 0;
        for (int num : result) {
            if(isPrime(num)) {
                count++;
            }
        }

        return count;
    }

    private void dfs(char[] chars, boolean[] visited, String current, HashSet<Integer> result) {
        if(!current.isEmpty()) {
            result.add(Integer.parseInt(current));
        }

        for(int i = 0; i < chars.length; i++) {
            if(!visited[i]) {
                visited[i] = true;
                dfs(chars, visited, current + chars[i], result);
                visited[i] = false; // 백트래킹 처리
            }
        }
    }

    private boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }

        for(int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }

        return true;
    }

}
