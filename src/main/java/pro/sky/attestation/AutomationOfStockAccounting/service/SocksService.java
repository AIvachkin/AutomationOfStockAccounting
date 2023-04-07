package pro.sky.attestation.AutomationOfStockAccounting.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.attestation.AutomationOfStockAccounting.model.Socks;
import pro.sky.attestation.AutomationOfStockAccounting.repository.SocksRepository;

import java.util.Optional;

@Service
@Slf4j
public class SocksService {

    private final SocksRepository socksRepository;

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    /**
     * Метод добавления поступившей партии носков в БД
     *
     * @param socks носки
     */
    public Socks createSocks(Socks socks) {
        Optional<Socks> socks1 = Optional.ofNullable(socksRepository.findByColorAndCottonPart(socks.getColor().toUpperCase(), socks.getCottonPart()));
        if (socks1.isEmpty()) {
            log.info("Was invoked method for create socks");
            socks.setColor(socks.getColor().toUpperCase());
            return socksRepository.save(socks);
        } else {
            log.info("Was invoked method for update socks");
            Socks socksTemp = socks1.get();
            socksTemp.setColor(socks.getColor().toUpperCase());
            socksTemp.setQuantity(socks.getQuantity() + socksTemp.getQuantity());
            return socksRepository.save(socksTemp);
        }
    }
}
