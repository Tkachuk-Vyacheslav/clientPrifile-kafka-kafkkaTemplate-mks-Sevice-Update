package org.client.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.client.common.util.Сurrency;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MoneyTransferDto {

    @Schema(example = "MoneyTransfer", description = "Событие,  при котором пройдет перевод")
    @JsonProperty(Fields.event)
    String event;

    @Schema(example = "+7-905-338-94-07", description = "телефон клиента, которому переводят средства")
    @JsonProperty(Fields.phonenumber)
    String phonenumber;

    @Schema(example = "10500", description = "сумма перевода")
    @JsonProperty(Fields.payment)
    String payment;

    @Schema(example = "RUB, EURO, USD", description = "валюта перевода")
    @JsonProperty(Fields.currency)
    Сurrency currency;

    @Schema(example = "1219", description = "уникальный идентификатор клиента, который совершает перевод средств")
    @JsonProperty(Fields.icp)
    String icp;

    public static class Fields {

        public static final String event = "event";

        public static final String phonenumber = "phonenumber";

        public static final String payment = "payment";

        public static final String currency = "currency";

        public static final String icp = "icp";
    }

}
