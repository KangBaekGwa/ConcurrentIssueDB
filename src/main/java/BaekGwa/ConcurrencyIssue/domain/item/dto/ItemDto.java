package BaekGwa.ConcurrencyIssue.domain.item.dto;

import BaekGwa.ConcurrencyIssue.domain.item.entity.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ItemDto {

    @Getter
    @AllArgsConstructor
    public static class NewItem {

        @NotNull
        private String name;

        @NotNull
        private Long price;

        @NotNull
        @Min(value = 1L, message = "최소 한개 이상부터 등록 가능합니다.")
        private Long stock;

        public Item toEntity(){
            return Item
                    .builder()
                    .name(this.getName())
                    .price(this.getPrice())
                    .stock(this.getStock())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class BuyItem {
        @NotNull
        private String name;

        @NotNull
        private Long amount;
    }
}
