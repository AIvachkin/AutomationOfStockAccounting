package pro.sky.attestation.AutomationOfStockAccounting.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.attestation.AutomationOfStockAccounting.model.Socks;

import java.util.List;


/**
 * Интерфейс для работы с БД по учету носков
 */
@Repository
public interface SocksRepository extends CrudRepository<Socks, Long> {

    Socks findByColorAndCottonPart(String color, Integer cottonPart);


    List<Socks> findSocksByColorAndCottonPartEquals(String color, Integer cottonPart);

    List<Socks> findSocksByColorAndCottonPartBefore(String color, Integer cottonPart);

    List<Socks> findSocksByColorAndCottonPartAfter(String color, Integer cottonPart);


}
