package BaekGwa.ConcurrencyIssue.domain.item.service;

import BaekGwa.ConcurrencyIssue.domain.item.dto.ItemDto.BuyItem;
import BaekGwa.ConcurrencyIssue.domain.item.dto.ItemDto.NewItem;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

/**
 * Facade 패턴 적용 ItemServiceImplV7 -> ItemServiceImplV1
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImplV7 implements ItemService {

    private final ItemServiceImplV1 itemService;
    private final RedissonClient redissonClient;

    //해당 key 는 분산환경에서 공유되어야함.
    //트랜잭션의 범위를 설정하는 역할을 함.
    //이렇게 사용하기 보다, 명확한 KEY 네이밍 규칙과 관리가 필요함.
    private static final String ITEM_SERVICE_LOCK_KEY = "1";

    @Override
    public Boolean RegisterItem(NewItem newItem) {

        RLock lock = redissonClient.getLock(ITEM_SERVICE_LOCK_KEY);

        try {
            boolean available = lock.tryLock(10, 2, TimeUnit.SECONDS);

            if (!available) {
                log.info("{} : lock 획득 실패", Thread.currentThread().getName());
                return false;
            }

            return itemService.RegisterItem(newItem);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Boolean buyItem(BuyItem buyItem) throws InterruptedException {

        RLock lock = redissonClient.getLock(ITEM_SERVICE_LOCK_KEY);

        try {
            boolean available = lock.tryLock(10, 2, TimeUnit.SECONDS);

            if (!available) {
                log.info("{} : lock 획득 실패", Thread.currentThread().getName());
                return false;
            }

            return itemService.buyItem(buyItem);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
