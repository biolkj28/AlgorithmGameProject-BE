package com.seventeam.algoritmgameproject.web.socketServer.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ReadyRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    // 채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장
    public static final String ROOM_READY = "ROOM_READY";

    public void ready(String roomId) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(ROOM_READY + roomId))) {
            redisTemplate.opsForValue().set(ROOM_READY + roomId, false);
        } else {
            redisTemplate.opsForValue().set(ROOM_READY + roomId, true);
        }
    }

    public boolean readyCheck(String roomId) {
        boolean allReady = false;
        Object o = redisTemplate.opsForValue().get(ROOM_READY + roomId);
        if (o != null) {
            if (Boolean.parseBoolean(o.toString())) {
                allReady = true;
            }
        }
        return allReady;
    }

    public void deleteReady(String roomId) {
        redisTemplate.delete(ROOM_READY + roomId);
    }
}
