package BaekGwa.ConcurrencyIssue.global.redis.repository;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     *
     * @param key
     * key는 트랜잭션 범위를 지정한다.
     * 같은 트랜잭션은 같은 key를 사용해야한다.
     * @return
     */
    public Boolean lock(Long key) {
        return redisTemplate.opsForValue()
                .setIfAbsent(generateKey(key), "lock", Duration.ofMillis(3000));
    }

    /**
     *
     * @param key
     * key는 트랜잭션 범위를 지정한다.
     * 같은 트랜잭션은 같은 key를 사용해야한다.
     * @return
     */
    public Boolean unlock(Long key) {
        return redisTemplate.delete(generateKey(key));
    }

    private String generateKey(Long key){
        return key.toString();
    }
}
