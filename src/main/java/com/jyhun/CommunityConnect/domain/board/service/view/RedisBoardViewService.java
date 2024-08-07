package com.jyhun.CommunityConnect.domain.board.service.view;

import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class RedisBoardViewService implements BoardViewService {

    private final BoardRepository boardRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedissonClient redissonClient;

    private final static String VIEW_COUNT_KEY_PREFIX = "board:viewCount";
    private final static String LOCK_KEY_PREFIX = "lock:board:viewCount";

    @Override
    public void view(Long id) {
        String key = VIEW_COUNT_KEY_PREFIX + id;
        String lockKey = LOCK_KEY_PREFIX + id;
        RLock lock = redissonClient.getLock(lockKey);
        boolean isLocked = false;

        try {
            isLocked = lock.tryLock(5,10, TimeUnit.SECONDS);
            if(isLocked) {
                ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
                if (valueOperations.get(key) == null) {
                    valueOperations.set(key, "1");
                } else {
                    valueOperations.increment(key);
                }
            }else{
                log.warn("Could not acquired lock for key: {}", key);
            }
        }catch (InterruptedException e) {
            log.error("Lock acquisition interrupted for key: {}", key, e);
            Thread.currentThread().interrupt();
        }finally {
            if (isLocked) {
                lock.unlock();
            }
        }
    }

    @Transactional
    @Scheduled(fixedRate = 6000)
    public void scheduleRedisToDatabase() {
        Set<String> keys = redisTemplate.keys(VIEW_COUNT_KEY_PREFIX + "*");
        if (keys != null) {
            for (String key : keys) {
                Long id = Long.parseLong(key.replace(VIEW_COUNT_KEY_PREFIX, ""));
                String lockKey = LOCK_KEY_PREFIX + id;
                RLock lock = redissonClient.getLock(lockKey);
                boolean isLocked = false;
                try {
                    isLocked = lock.tryLock(5,10,TimeUnit.SECONDS);
                    if(isLocked) {
                        Long viewCount = Long.parseLong(redisTemplate.opsForValue().get(key));
                        if (viewCount != null) {
                            Board board = boardRepository.findById(id).orElseThrow();
                            board.increaseView(viewCount);
                            boardRepository.save(board);
                            redisTemplate.delete(key);
                        }
                    }else {
                        log.warn("Could not acquire lock for key: {}", key);
                    }
                }catch (InterruptedException e){
                    log.error("Lock acquisition interrupted for key: {}", key, e);
                    Thread.currentThread().interrupt();;
                }finally {
                    if(isLocked) {
                        lock.unlock();
                    }
                }
            }
        }
    }
}
