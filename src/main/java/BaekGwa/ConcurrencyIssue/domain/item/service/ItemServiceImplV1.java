package BaekGwa.ConcurrencyIssue.domain.item.service;

import BaekGwa.ConcurrencyIssue.domain.item.dto.ItemDto.BuyItem;
import BaekGwa.ConcurrencyIssue.domain.item.dto.ItemDto.NewItem;
import BaekGwa.ConcurrencyIssue.domain.item.entity.Item;
import BaekGwa.ConcurrencyIssue.domain.item.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImplV1 implements ItemService{

    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public Boolean RegisterItem(NewItem newItem) {
        try {
            itemRepository.save(newItem.toEntity());
        } catch (Exception e){
            log.error("Data 저장 실패. Message : {}", e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean buyItem(BuyItem buyItem) {

        try {
            //검증 로직
            Item findItem = itemRepository.findAllByName(buyItem.getName());

            //금액 검증
            //if(보유현금 > 구매 금액) return false 등등...

            //상품 검증
            if( findItem.getStock() < buyItem.getAmount() ){
                log.error("구매하려는 상품의 재고 수량이 부족합니다.");
                return false;
            }

            //서비스 로직
            itemRepository.save(Item
                    .builder()
                    .id(findItem.getId())
                    .name(findItem.getName())
                    .stock(findItem.getStock() - buyItem.getAmount())
                    .price(findItem.getPrice())
                    .build());

            return true;

        } catch (Exception e) {
            log.error("구매 실패. Message = {}", e.getMessage() + e.getCause());
            return false;
        }
    }
}
