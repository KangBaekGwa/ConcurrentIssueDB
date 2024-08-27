package BaekGwa.ConcurrencyIssue.domain.item.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //이름
    @Column(nullable = false, name = "name")
    private String name;

    //가격
    @Column(nullable = false, name = "price")
    private Long price;

    //재고 수량
    @Column(nullable = false, name = "stock")
    private Long stock;

    @Version
    private Long version;

    public void changeStock(Long amount){
        this.stock = this.stock - amount;
    }
}