package org.client.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.client.common.dto.Wallets.EuroWalletDto;
import org.client.common.dto.Wallets.RubWalletDto;
import org.client.common.dto.Wallets.UsdWalletDto;
import org.intellij.lang.annotations.Pattern;
import java.util.Collection;

@Schema(description = "Модель, описывающая баланс пользоватяле в ЛК банка")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletDto {

    public static final String UUID_PATTERN = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
//    @Schema(example = "4800c301-50a5-46f9-8c5f-6d6b3fbc55nf", description = "Идентификатор кошелька in WalletMedium по стандарту RFC4122")
//    @Pattern(value = UUID_PATTERN)
//    @JsonProperty(Fields.UUID)
//    private String uuid;

    @Schema(example = "13356", description = "Идентификатор Клиентского профиля")
    @JsonProperty(Fields.INDIVIDUAL_ICP)
    private String individualIcp;

    @Hidden
    @Schema(example = "30101810645250000416 ", description = "Рублевый счет клиента")
    @JsonProperty(Fields.RUB_WALLET)
    private RubWalletDto rubWalletDto;

    @Hidden
    @Schema(example = "30101810645250000416 ", description = "euro счет клиента")
    @JsonProperty(Fields.EUR_WALLET)
    private EuroWalletDto euroWalletDto;

    @Hidden
    @Schema(example = "30101810645250000416 ", description = "usd счет клиента")
    @JsonProperty(Fields.USD_WALLET)
    private UsdWalletDto usdWalletDto;


    public static class Fields {
//        public static final String UUID = "uuid";

        public static final String RUB_WALLET = "rubWallet";

        public static final String EUR_WALLET = "eurWallet";

        public static final String USD_WALLET = "usdWallet";

        public static final String INDIVIDUAL_ICP = "icp";
    }
}
