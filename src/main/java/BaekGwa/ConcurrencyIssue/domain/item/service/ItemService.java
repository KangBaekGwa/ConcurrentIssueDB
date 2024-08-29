package BaekGwa.ConcurrencyIssue.domain.item.service;

import BaekGwa.ConcurrencyIssue.domain.item.dto.ItemDto;

public interface ItemService {

    Boolean RegisterItem(ItemDto.NewItem newItem) throws InterruptedException;

    Boolean buyItem(ItemDto.BuyItem buyItem) throws InterruptedException;
}
