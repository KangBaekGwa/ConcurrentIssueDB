package BaekGwa.ConcurrencyIssue.domain.item.controller;

import BaekGwa.ConcurrencyIssue.domain.item.dto.ItemDto;
import BaekGwa.ConcurrencyIssue.domain.item.service.ItemServiceImplV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/item")
public class ItemController {

    private final ItemServiceImplV1 itemService;

    @PostMapping("/register")
    public String NewItem(
            @RequestBody ItemDto.NewItem newItem){
        Boolean isSuccess = itemService.RegisterItem(newItem);
        return isSuccess ? "OK" : "NG";
    }

    @PostMapping("/buy")
    public String BuyItem(
            @RequestBody ItemDto.BuyItem buyItem){
        Boolean isSuccess = itemService.buyItem(buyItem);
        return isSuccess ? "OK" : "NG";
    }
}
