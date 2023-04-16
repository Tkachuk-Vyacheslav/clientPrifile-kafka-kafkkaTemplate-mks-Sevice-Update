package org.client.common.dto.Wallets;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.client.common.util.Сurrency;




@Schema(description = "модель, описывающая валютный  счет пользователя (евро)")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EuroWalletDto {

    @Schema(example = "91319384252421979732757189", description = "Идентификатор счета клиента")
    @JsonProperty(Fields.UUID)
    private String uuid;

    @Schema(example = "565.43", description = "количество средств на счету клиента")
    @JsonProperty(Fields.VALUE)
    String value;

    @Schema(example = "EURO", description = "валюта счета")
    Сurrency currency;

    public static class Fields {
        public static final String UUID = "uuid";
        public static final String VALUE = "value";

    }

}
