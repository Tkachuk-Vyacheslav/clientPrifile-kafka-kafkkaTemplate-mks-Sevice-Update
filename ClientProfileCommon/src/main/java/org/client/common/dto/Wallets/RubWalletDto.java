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

@Schema(description = "модель, описывающая рублевый счет пользователя")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RubWalletDto {

    @Schema(example = "91319384252421979732757189", description = "Идентификатор рублевого счета клиента")
    @JsonProperty(Fields.UUID)
    private String uuid;

    @Schema(example = "56498", description = "количество средств на счету клиента")
    @JsonProperty(Fields.VALUE)
    String value;

    @Schema(example = "RUB", description = "валюта счета")
    Сurrency currency;

    public static class Fields {
        public static final String UUID = "uuid";
        public static final String VALUE = "value";

    }

}
