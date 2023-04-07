package pro.sky.attestation.AutomationOfStockAccounting.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import pro.sky.attestation.AutomationOfStockAccounting.exception.SocksNotFoundException;
import pro.sky.attestation.AutomationOfStockAccounting.model.Color;
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


    /**
     * Метод отпуска партии носков со склада (с удалением партии из БД)
     *
     * @param socks носки
     */
    public Socks outcomeSocks(Socks socks) {
        log.info("Was invoked method for outcome socks");
        Optional<Socks> socks1 = Optional.ofNullable(socksRepository.findByColorAndCottonPart(socks.getColor().toUpperCase(), socks.getCottonPart()));
        if (socks1.isEmpty()) {
            log.warn("Socks with these parameters are not in stock");
            throw new SocksNotFoundException("Носков с такими параметрами нет на складе");

        } else if (socks1.get().getQuantity() < socks.getQuantity()) {
            log.warn("Socks with these parameters are not in stock");
            throw new SocksNotFoundException("Носков с такими параметрами на складе меньше, чем запрашивается");
        } else {
            Socks socksTemp = socks1.get();
            socksTemp.setColor(socks.getColor().toUpperCase());
            socksTemp.setQuantity(socksTemp.getQuantity() - socks.getQuantity());
            return socksRepository.save(socksTemp);
        }
    }

    /**
     * Метод для проверки корректности прихода параметров запросов по добавлению и отпуску носков
     *
     * @param socksBody носки
     */
    public Boolean checkingQueryParameters(Socks socksBody) {
        return (socksBody.getQuantity() < 1 || socksBody.getCottonPart() < 0 || socksBody.getCottonPart() > 100
                || !EnumUtils.isValidEnumIgnoreCase(Color.class, socksBody.getColor()));
    }
}
