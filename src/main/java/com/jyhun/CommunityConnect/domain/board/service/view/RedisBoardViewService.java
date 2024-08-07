package com.jyhun.CommunityConnect.domain.board.service.view;

import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class RedisBoardViewService implements BoardViewService {

    private final BoardRepository boardRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private final static String VIEW_COUNT_KEY_PREFIX = "board:viewCount";

    @Override
    public void view(Long id) {
        String key = VIEW_COUNT_KEY_PREFIX + id;
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        if (valueOperations.get(key) == null) {
            valueOperations.set(key,"1");
        } else {
            valueOperations.increment(key);
        }
    }

    @Transactional
    @Scheduled(fixedRate = 6000) // 60초 마다 실행
    public void scheduleRedisToDatabase() {
        Set<String> keys = redisTemplate.keys(VIEW_COUNT_KEY_PREFIX + "*");
        if (keys != null) {
            for (String key : keys) {
                Long id = Long.parseLong(key.replace(VIEW_COUNT_KEY_PREFIX, ""));
                Long viewCount = Long.parseLong((String) redisTemplate.opsForValue().get(key));
                if (viewCount != null) {
                    Board board = boardRepository.findById(id).orElseThrow();
                    board.increaseView(viewCount);
                    boardRepository.save(board);
                    redisTemplate.delete(key);
                }
            }
        }
    }
}
