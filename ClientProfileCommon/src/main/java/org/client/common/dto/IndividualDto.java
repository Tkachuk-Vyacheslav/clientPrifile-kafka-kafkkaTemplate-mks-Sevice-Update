package org.client.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.intellij.lang.annotations.Pattern;


import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

@Schema(description = "Модель, описывающая пользоватяле в ЛК банка")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IndividualDto {

    public static final String UUID_PATTERN = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
    @Schema(example = "4800c301-50a5-46f9-8c5f-6d6b3fbc55nf", description = "Идентификатор клиента по стандарту RFC4122")
    @Pattern(value = UUID_PATTERN)
    @JsonProperty(Fields.UUID)
    private String uuid;

    @Schema(example = "312", description = "Идентификатор Клиентского профиля")
    @JsonProperty(Fields.ICP)
    private String icp;

    @Schema(example = "Иван", description = "Имя клиента")
    private String name;

    @Schema(example = "Петров", description = "Фамилия клиента")
    private String surname;

    @Schema(example = "Федорович", description = "Отчество клиента")
    private String patronymic;

    @Schema(example = "Иван Федорович Петров", description = "ФИО клиента")
    private String fullName;

    @Schema(example = "М", description = "Пол клиента (мужской)")
    private String gender;

    @Schema(example = "Северодвинск", description = "Город, в котором родился клиент")
    private String placeOfBirth;

    @Schema(example = "Белорусь", description = "Страна, в которой родился клиент")
    private String countryOfBirth;

    @Schema(example = "1990-12-03", description = "Дата рождения клиента")
    private Date birthDate;
    @Schema(description = "Флаг, показывающий архивный ли клиент")
    private boolean isArchived = false;
    @Schema(example = "123", description = "Если клиент актуальный, то пусто, если клиент заархивирован, хранит актуальный icp этого клиента")
    private String actualIcp = "";

    @Schema(example = "4800c301-50a5-46f9-8c5f-6d6b3fbc55nf", description = "Идентификатор документа по стандарту RFC4122")
    @Pattern(value = UUID_PATTERN)
    private String documentsUuid;

    @Schema(example = "4800c301-50a5-46f9-8c5f-6d6b3fbc55nf", description = "Идентификатор паспорта по стандарту RFC4122")
    private UUID rfPassportUuid;

    @Schema(example = "4800c301-50a5-46f9-8c5f-6d6b3fbc55nf", description = "Идентификатор контактов пользователя по стандарту RFC4122")
    @Pattern(value = UUID_PATTERN)
    private String contactsUuid;

    @Hidden
    @JsonProperty(Fields.ADDRESS)
    private Collection<AddressDto> address;

    @Hidden
    @JsonProperty(Fields.PASSPORT)
    private Collection<RFPassportDto> passport;

    @Hidden
    @JsonProperty(Fields.WALLET)
    private WalletDto wallet;

    @JsonProperty(Fields.AVATAR)
    private Collection<AvatarDto> avatar;


    public static class Fields {
        public static final String UUID = "uuid";

        public static final String ICP = "icp";

        public static final String ADDRESS = "address";

        public static final String WALLET = "wallet";

        public static final String AVATAR = "avatar";

        public static final String PASSPORT = "passport";
    }

    // Serializing Dto To Json object.
    public static String Serializer (IndividualDto individualDto) {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json= null;
        try {
            json = objectMapper.writeValueAsString(individualDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;

    }

    //Deserializing from Json to IndividualDto.
    public static IndividualDto Deserializer(String json) {

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        IndividualDto individualDto = null;
        try {
            individualDto = objectMapper.readValue(json, IndividualDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return individualDto;

    }
    private static Logger logger=Logger.getLogger(IndividualDto.class.getName());



}
