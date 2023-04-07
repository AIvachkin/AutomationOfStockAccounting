package pro.sky.attestation.AutomationOfStockAccounting.model;

import jakarta.persistence.*;
import lombok.Data;


/**
 * Класс - сущность "Носки"
 */
@Entity
@Data
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
    private String cottonPart;

    /**
     * количество пар носков
     */
    private Integer quantity;


}
