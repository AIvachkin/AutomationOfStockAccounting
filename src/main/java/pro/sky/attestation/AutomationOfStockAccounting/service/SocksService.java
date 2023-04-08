package pro.sky.attestation.AutomationOfStockAccounting.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import pro.sky.attestation.AutomationOfStockAccounting.exception.OperationNotFoundException;
import pro.sky.attestation.AutomationOfStockAccounting.exception.SocksNotFoundException;
import pro.sky.attestation.AutomationOfStockAccounting.model.Color;
import pro.sky.attestation.AutomationOfStockAccounting.model.Operation;
import pro.sky.attestation.AutomationOfStockAccounting.model.Socks;
import pro.sky.attestation.AutomationOfStockAccounting.repository.SocksRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с партиями носков
 */
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
     * Метод возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса
     *
     * @param color      цвет носков
     * @param operation  тип операции сравнения
     * @param cottonPart значение процента хлопка в составе
     */
    public String returnTheNumberOfSocks(String color, String operation, Integer cottonPart) {

        if (!EnumUtils.isValidEnumIgnoreCase(Operation.class, operation)) {
            log.warn("Invalid comparison operation entered");
            throw new OperationNotFoundException("Введена недопустимая операция сравнения");
        }
        if (operation.equalsIgnoreCase("EQUAL")) {
            List<Socks> socksList = socksRepository.findSocksByColorAndCottonPartEquals(color, cottonPart);
            if (!socksList.isEmpty()) {
                return "Количество носков цвета " + color + " со значением процента хлопка в составе - " + cottonPart +
                        "% : " + sumSocks(socksList) + " пар";
            }
        } else if (operation.equalsIgnoreCase("MORETHAN")) {
            List<Socks> socksList = socksRepository.findSocksByColorAndCottonPartAfter(color, cottonPart);
            if (!socksList.isEmpty()) {
                return "Количество носков цвета " + color + " со значением процента хлопка в составе более " + cottonPart +
                        "% : " + sumSocks(socksList) + " пар";
            }
        } else {
            List<Socks> socksList = socksRepository.findSocksByColorAndCottonPartBefore(color, cottonPart);
            if (!socksList.isEmpty()) {
                return "Количество носков цвета " + color + " со значением процента хлопка в составе менее " + cottonPart +
                        "% : " + sumSocks(socksList) + " пар";
            }
        }
        return noPosition();
    }

    /**
     * Метод суммирует количество носков в коллекции
     *
     * @param socksList коллекция носков
     */
    public Integer sumSocks(List<Socks> socksList) {
        return socksList.stream()
                .map(Socks::getQuantity)
                .reduce(0, Integer::sum);

    }

    /**
     * Метод возвращает сообщение об отсутствии искомых позиций на складе
     *
     */
    public String noPosition (){
        return "Позиций этого цвета с таким содержанием хлопка на складе нет";
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

    /**
     * Метод для проверки корректности прихода параметров запросов по добавлению и отпуску носков
     *
     * @param color      цвет носков
     * @param operation  тип операции сравнения
     * @param cottonPart значение процента хлопка
     */
    public Boolean checkingQueryParameters(String color, String operation, Integer cottonPart) {
        return (!EnumUtils.isValidEnumIgnoreCase(Operation.class, operation) || cottonPart < 0 || cottonPart > 100
                || !EnumUtils.isValidEnumIgnoreCase(Color.class, color));
    }

}
