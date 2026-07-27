package org.example.Y_2026.July;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Day57 친구 네트워크
 *
 * 친구 관계가 한 쌍씩 주어질 때마다, 방금 친구가 된 두 사람 중 한 명이 속한 친구 네트워크(직접·간접으로 연결된 사람들)의 크기를 출력한다.
 */
public class Day57FriendNetwork {

    static int[] parent;
    static int[] size;
    static HashMap<String, Integer> map;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // M개의 테스트케이스에 갯수 타임라인을 넣어줄 객체
        StringBuilder sb = new StringBuilder();

        // T = 테스트 케이스 갯수
        // M = 테스트 케이스당 친구 관계 수
        int T = Integer.parseInt(st.nextToken());

        for(int i = 0; i < T; i++) {
            // 테스트 케이스만큼 반복
            st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());

            // 이름 -> 번호 매핑
            map = new HashMap<>();

            // 배열 사이즈는 관계의 갯수 (사람, 사람) -> 최대 2명 (관계 M * 2)
            // 초기엔 자신이 부모
            parent = new int[2 * M];
            for(int j= 0; j < 2 * M; j++) {
                parent[j] = j;
            }
            
            // 그룹 크기 추적 초기값은 1로 초기화
            size = new int[2 * M];
            Arrays.fill(size, 1);

            for(int k = 0; k < M; k++) {
                st = new StringTokenizer(br.readLine());
                String name1 = st.nextToken();
                String name2 = st.nextToken();

                int result = union(name1, name2);
                sb.append(result).append("\n");
            }
        }

        System.out.print(sb);
    }

    private static int union(String name1, String name2) {

        // 이름 → id 확보: map에 없으면 새 id 부여하고 parent/size 초기화, 있으면 기존 id 사용
        int id1 = getId(name1);
        int id2 = getId(name2);

        // 각자의 루트 찾기: find(id1), find(id2) — parent를 계속 따라 올라가서 최상위(자기 자신이 부모인 지점) 찾기
        int root1 = find(id1);
        int root2 = find(id2);

        // 같은 루트면: 이미 한 그룹이니 그냥 size[루트] 반환
        if (root1 == root2) {
            return size[root1];
        }

        // 다른 루트면: 작은 그룹을 큰 그룹 밑에 붙이고(parent[루트B] = 루트A), size[루트A] += size[루트B], 그 합산값 반환
        parent[root2] = root1;
        // 크기 합산
        size[root1] += size[root2];
        return size[root1];
    }


    private static int getId(String name) {
        // 이름을 번호로 변환 -> 처음보는 이름이면 새 id 부여
        if(!map.containsKey(name)) {
            // 새이름 -> 다음 번호 부여
            map.put(name, map.size());
        }
        return map.get(name);
    }

    private static int find(int x) {
        // 번호를 받아서 루트 번호를 찾는다 (부모 번호) -> 경로 압축 포함
        if(parent[x] == x) {
            return x;
        }
        // 부모의 부모까지 찾는
        parent[x] = find(parent[x]);
        return parent[x];
    }


}
