package BaekGwa.ConcurrencyIssue.domain.item.repository;

import BaekGwa.ConcurrencyIssue.domain.item.entity.Item;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findAllByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Item findUseLockByName(String name);

    @Lock(LockModeType.OPTIMISTIC)
    Item findUseOptimisticLockByName(String name);
}
