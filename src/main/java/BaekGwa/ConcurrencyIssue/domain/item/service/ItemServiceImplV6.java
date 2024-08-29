package BaekGwa.ConcurrencyIssue.domain.item.service;

import BaekGwa.ConcurrencyIssue.domain.item.dto.ItemDto.BuyItem;
import BaekGwa.ConcurrencyIssue.domain.item.dto.ItemDto.NewItem;
import BaekGwa.ConcurrencyIssue.global.redis.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.RedissonRedLock;
import org.springframework.stereotype.Service;

/**
 * Facade 패턴 적용 ItemServiceImplV1 -> ItemServiceImplV6
 */
@Service
@RequiredArgsConstructor
public class ItemServiceImplV6 implements ItemService {

    private final ItemServiceImplV1 itemService;
    private final RedisRepository redisRepository;

    //해당 key 는 분산환경에서 공유되어야함.
    //트랜잭션의 범위를 설정하는 역할을 함.
    //이렇게 사용하기 보다, 명확한 KEY 네이밍 규칙과 관리가 필요함.
    private static final Long ITEM_SERVICE_LOCK_KEY = 1L;

    @Override
    public Boolean RegisterItem(NewItem newItem) {

        getLock(ITEM_SERVICE_LOCK_KEY);

        try {
            return itemService.RegisterItem(newItem);
        } finally {
            unLock(ITEM_SERVICE_LOCK_KEY);
        }
    }

    @Override
    public Boolean buyItem(BuyItem buyItem) throws InterruptedException {
        getLock(ITEM_SERVICE_LOCK_KEY);

        try {
            return itemService.buyItem(buyItem);
        } finally {
            unLock(ITEM_SERVICE_LOCK_KEY);
        }
    }

    private void getLock(Long key) {
        while (!redisRepository.lock(key)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void unLock(Long key) {
        redisRepository.unlock(key);
    }
}
