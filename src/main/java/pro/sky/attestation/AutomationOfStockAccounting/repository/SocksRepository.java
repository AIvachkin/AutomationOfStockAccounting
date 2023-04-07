package pro.sky.attestation.AutomationOfStockAccounting.repository;

import org.springframework.data.repository.CrudRepository;
import pro.sky.attestation.AutomationOfStockAccounting.model.Socks;

/**
 * Интерфейс для работы с БД по учету носков
 */
public interface SocksRepository extends CrudRepository<Socks, Long> {

    Boolean findByColorAndCottonPart(String color, String cottonPart);

    Socks findSocksByColorAndCottonPart (String color, String cottonPart);

}
