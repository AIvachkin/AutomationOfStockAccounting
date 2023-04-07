package pro.sky.attestation.AutomationOfStockAccounting.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.attestation.AutomationOfStockAccounting.model.Color;
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
    public ResponseEntity createSocks(@RequestBody Socks socks) {

        if (socks.getQuantity() < 1 || socks.getCottonPart() < 0 || socks.getCottonPart() > 100
                || !EnumUtils.isValidEnumIgnoreCase(Color.class, socks.getColor())
        ) {
            return ResponseEntity.status(400).build();
        }

        Socks createdSocks = socksService.createSocks(socks);
        return ResponseEntity.ok(createdSocks);
    }

    @GetMapping()
    public String hello() {
        return "hello";
    }

}
