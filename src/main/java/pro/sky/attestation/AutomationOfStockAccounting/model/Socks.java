package pro.sky.attestation.AutomationOfStockAccounting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;


/**
 * Класс - сущность "Носки"
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Socks {

    /**
     * id вида носков в БД
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "socks_id")
    private Long id;


    /**
     * цвет носков
     */
    private String color;

    /**
     * процентное содержание хлопка в носках
     */
    private Integer cottonPart;

    /**
     * количество пар носков
     */
    private Integer quantity;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return Objects.equals(color, socks.color) && Objects.equals(cottonPart, socks.cottonPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, cottonPart);
    }

    @Override
    public String toString() {
        return "Socks{" +
                "color='" + color + '\'' +
                ", cottonPart='" + cottonPart + '\'' +
                '}';
    }
}
