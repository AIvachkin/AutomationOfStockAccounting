package pro.sky.attestation.AutomationOfStockAccounting.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.attestation.AutomationOfStockAccounting.model.Socks;
import pro.sky.attestation.AutomationOfStockAccounting.repository.SocksRepository;

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
        if (!socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart())) {
            log.info("Was invoked method for create socks");
            return socksRepository.save(socks);
        } else {
            log.info("Was invoked method for update socks");
            Socks socksTemp = socksRepository.findSocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
            socksTemp.setQuantity(socks.getQuantity() + socksTemp.getQuantity());
            return socksRepository.save(socksTemp);
        }
    }
}
