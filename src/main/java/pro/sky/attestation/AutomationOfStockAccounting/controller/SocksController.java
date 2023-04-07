package pro.sky.attestation.AutomationOfStockAccounting.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.attestation.AutomationOfStockAccounting.model.Socks;
import pro.sky.attestation.AutomationOfStockAccounting.service.SocksService;

/**
 * Контроллер для работы с сущностями "носки"
 */
@RestController
@RequestMapping("/api/socks")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }


    @Operation(
            summary = "Регистрация прихода носков на склад",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "удалось добавить приход",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Socks.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "параметры запроса отсутствуют или имеют некорректный формат"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "произошла ошибка, не зависящая от вызывающей стороны " +
                                    "(например, база данных недоступна)."
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Внесение партии носков в базу",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Socks.class)
                    )
            ), tags = "Socks"
    )
    @PostMapping("/income")
    public ResponseEntity<Object> createSocks(@RequestBody Socks socks) {

        if (socksService.checkingQueryParameters(socks)) {
            return ResponseEntity.status(400).build();
        }

        Socks createdSocks = socksService.createSocks(socks);
        return ResponseEntity.ok(createdSocks);
    }

    @Operation(
            summary = "Отпуск носков со склада",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "удалось отпустить партию носков",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Socks.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "параметры запроса отсутствуют или имеют некорректный формат"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "произошла ошибка, не зависящая от вызывающей стороны " +
                                    "(например, база данных недоступна)."
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Отпуск партии носков из базы",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Socks.class)
                    )
            ), tags = "Socks"
    )
    @PostMapping("/outcome")
    public ResponseEntity<Object> outcomeSocks(@RequestBody Socks socks) {

        if (socksService.checkingQueryParameters(socks)) {
            return ResponseEntity.status(400).build();
        }

        Socks outSocks = socksService.outcomeSocks(socks);
        return ResponseEntity.ok(outSocks);
    }


    @Operation(
            summary = "Получение общего количества носков на складе, соответствующих полученным параметрам",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "удалось получить общее количество носков по параметрам",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Socks.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "параметры запроса отсутствуют или имеют некорректный формат"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "произошла ошибка, не зависящая от вызывающей стороны " +
                                    "(например, база данных недоступна)."
                    )
            },
            tags = "Socks"
    )
    @GetMapping()
    public ResponseEntity<Object> hello(@Parameter(description = "цвет носков", example = "red")
                                        @RequestParam String color,
                                        @Parameter(description = "операция сравнения", example = "moreThan, lessThan, equal")
                                        @RequestParam String operation,
                                        @Parameter(description = "значение процента хлопка", example = "55")
                                        @RequestParam Integer cottonPart) {
        if (socksService.checkingQueryParameters(color, operation, cottonPart)) {
            return ResponseEntity.status(400).build();
        }

        String numberOfSocks = socksService.returnTheNumberOfSocks(color.toUpperCase(), operation, cottonPart);
        return ResponseEntity.ok(numberOfSocks);
    }

}
