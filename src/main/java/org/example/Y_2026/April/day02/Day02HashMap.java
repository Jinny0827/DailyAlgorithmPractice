package org.example.Y_2026.April.day02;

import java.util.HashMap;

/**
 * #42576 — 완주하지 못한 선수
 *
 * participant 길이: 1 ~ 100,000
 * completion 길이: participant 길이 - 1
 * 참가자 이름은 알파벳 소문자, 1~20자
 * 동명이인 존재 가능 (예시 3번 참고)
 */
public class Day02HashMap {
    public String solution(String[] participant, String[] completion) {
        HashMap<String, Integer> map = new HashMap<>();

        for(int i = 0; i < participant.length; i++) {
            map.put(participant[i], map.getOrDefault(participant[i], 0) + 1);
        }

        for(int i = 0; i < completion.length; i++) {
            map.put(participant[i], map.get(completion[i]) - 1);
        }

        for(String key : map.keySet()) {
            if (map.get(key) == 1) return key;
        }

        return "";
    }
}
