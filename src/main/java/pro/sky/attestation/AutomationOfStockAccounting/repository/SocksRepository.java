package pro.sky.attestation.AutomationOfStockAccounting.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.attestation.AutomationOfStockAccounting.model.Socks;


/**
 * Интерфейс для работы с БД по учету носков
 */
@Repository
public interface SocksRepository extends CrudRepository<Socks, Long> {

    Socks findByColorAndCottonPart(String color, Integer cottonPart);

    Socks findSocksByColorAndCottonPart (String color, Integer cottonPart);

}
