package org.client.repository;

import org.client.common.entity.ContactMedium;
import org.client.common.entity.Documents;
import org.client.common.entity.Individual;
import org.client.common.entity.RFPassport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, String>{

    Optional<Individual> findIndividualByIcp(String icp);
    Optional<Individual> findIndividualByUuid(String uuid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query( // связываем индивидуала и контакты
            value = "update public.individual set contactid = :contactsUuid where uuid = :uuid ",
            nativeQuery = true)//
    void updateUserContactsByUuid(@Param("contactsUuid") String contactsUuid, @Param("uuid") String uuid);

    //этот метод больше не нужен, далее я работал с Дто (преобразование дто в сущность и наоборот)
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query( // СОЗДАНИЕ НОВОГО ПОЛЬЗОВАТЕЛЯ, используем sql-запрос с именованными параметрами
            value = "insert into public.individual values " +
                    "(:uuid, :birthDate, :countryOfBirth, :fullName, :gender, :icp, :name, :patronymic, " +
                    ":placeOfBirth, :surname, :contactsUuid, :documentsuuid, :rfPassportUuid)",
            nativeQuery = true)//
    void createUser(@Param("uuid") String uuid, @Param("birthDate") Date birthDate, @Param("countryOfBirth") String countryOfBirth,
                    @Param("fullName") String fullName, @Param("gender") String gender, @Param("icp") String icp, @Param("name") String name,
                    @Param("patronymic") String patronymic, @Param("placeOfBirth") String placeOfBirth, @Param("surname") String surname,
                    @Param("contactsUuid") String contactsUuid, @Param("documentsuuid") String documentsuuid, @Param("rfPassportUuid") UUID rfPassportUuid);

    //  ищем uuid contact_medium юзера по icp юзера
    @Query("from ContactMedium as cont join fetch cont.individual as indiv where indiv.icp = :icp")
    ContactMedium findContactByIndivIcp(@Param("icp") String icp);

    //  ищем пользователя по номеру телефона  (джоин из трех таблиц через jpql)
    @Query("from Individual as indiv join fetch indiv.contacts as cont join fetch cont.phoneNumbers as phnum  where phnum.value = :number")
    Individual findByPhNum(@Param("number") String number);

    //  ищем Optional пользователя по номеру телефона  (джоин из трех таблиц через jpql)
    @Query("from Individual as indiv join fetch indiv.contacts as cont join fetch cont.phoneNumbers as phnum  where phnum.value = :number")
    List<Individual> findByPhNumOptional(@Param("number") String number);


    //  ищем uuid passport юзера по icp юзера
    @Query("from RFPassport as passp join fetch passp.individual as indiv where indiv.icp = :icp")
    RFPassport findPassportUuidByIndividIcp(@Param("icp") String icp);

    //  ищем uuid document юзера по icp юзера
    @Query("from Documents as doc join fetch doc.individual as indiv where indiv.icp = :icp")
    Documents findDocumentUuidByIndividIcp(@Param("icp") String icp);

    //  ищем все поля пользователя по uuid с помощью запроса
    @Query(value = "from Individual as i where i.uuid = :uuid")
    Individual findAllFieldsByUuid(@Param("uuid") String uuid);

    //  ищем  пользователя по uuid wallet
    @Query(value = "from Individual as i join fetch i.wallets as w where w.uuid = :walletUuid")
    Individual findCleintByWalletUuid(@Param("walletUuid") String walletUuid);

    //  ищем  пользователя по uuid passport
    @Query(value = "from Individual as i join fetch i.passport as p where p.uuid = :passpUuid")
    Individual findCleintByPasspuuid(@Param("passpUuid") String passpUuid);

    // вроде в этом методе нет необходимости (уже не актуален, можно обойтись без него, буде грамотнее)
    //  пересохраняем поля: контакты, документы, паспорт в табл. индивидуал
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update public.individual set contactid = :contactUuid, documentid = :documentUuid, rf_passport = :passpId where uuid = :individUuid",
            nativeQuery = true)
    void rewriteContactDocPassp(@Param("contactUuid") String contactUuid, @Param("documentUuid") String documentUuid,
                                      @Param("passpId") UUID passpId, @Param("individUuid") String individUuid);

    // запишем (перезапишем) contactid в таблицу Individual
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update public.individual set contactid = :contactUuid where uuid = :individUuid",
            nativeQuery = true)
    void rewriteIndividContactUuid(@Param("contactUuid") String contactUuid, @Param("individUuid") String individUuid);

    // запишем (перезапишем) documentid в таблицу Individual
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update public.individual set documentid = :documentUuid where uuid = :individUuid",
            nativeQuery = true)
    void rewriteIndividDocumUuid(@Param("documentUuid") String contactUuid, @Param("individUuid") String individUuid);

    //find client with passport series and number
    @Query(value = "from Individual as indiv join fetch indiv.passport as passport where passport.series = :series and passport.number = :number")
    Individual findByPassport(String series, String number);

}
