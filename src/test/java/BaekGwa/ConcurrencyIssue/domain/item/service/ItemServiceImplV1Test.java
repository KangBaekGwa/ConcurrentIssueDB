package BaekGwa.ConcurrencyIssue.domain.item.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import BaekGwa.ConcurrencyIssue.domain.item.dto.ItemDto.BuyItem;
import BaekGwa.ConcurrencyIssue.domain.item.dto.ItemDto.NewItem;
import BaekGwa.ConcurrencyIssue.domain.item.entity.Item;
import BaekGwa.ConcurrencyIssue.domain.item.repository.ItemRepository;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemServiceImplV1Test {

    @Autowired
    private ItemServiceImplV1 itemService;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    public void init() {
        NewItem newItem = new NewItem("상품A", 1000L, 100L);
        itemService.RegisterItem(newItem);
    }

    @AfterEach
    public void clear() {
        itemRepository.deleteAll();
    }

    @Test
    void 단일_구매_요청() {
        BuyItem ItemA = new BuyItem("상품A", 1L);
        Boolean isSuccess = itemService.buyItem(ItemA);

        Item findItem = itemRepository.findAllByName("상품A");

        assertEquals(isSuccess, true);
        assertEquals(99, findItem.getStock());
    }

    @Test
    void 다중_구매_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService es = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            es.submit(() -> {
                BuyItem ItemA = new BuyItem("상품A", 1L);
                Boolean isSuccess = itemService.buyItem(ItemA);
                assertEquals(isSuccess, true);
            });
        }

        es.shutdown();
        es.awaitTermination(10, TimeUnit.SECONDS);

        Item findItem = itemRepository.findAllByName("상품A");
        assertEquals(0, findItem.getStock());
    }
}