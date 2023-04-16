package org.client.repository;

import org.client.common.entity.Individual;
import org.client.common.entity.WalletMedium;
import org.client.common.entity.Wallets.EuroWallet;
import org.client.common.entity.Wallets.RubWallet;
import org.client.common.entity.Wallets.UsdWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<WalletMedium, String>{

    Optional<WalletMedium> findWalletByUuid (String uuid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query( // СОЗДАНИЕ WalletMedium, используем sql-запрос с именованными параметрами
            value = "insert into public.wallet_medium values " +
                    "(:uuid, :euro_walletid, :rub_walletid, :usd_walletid)",
            nativeQuery = true)//
    void createWalletMedium(@Param("uuid") String uuid, @Param("euro_walletid") String euro_walletid, @Param("rub_walletid") String rub_walletid,
                    @Param("usd_walletid") String usd_walletid);

    //find walletMedium by client icp
    @Query(value = "from WalletMedium as wallmed join fetch wallmed.individual as indiv where indiv.icp = :icp")
    WalletMedium findWalletMediumByClientIcp(String icp);

    //find Optional walletMedium by client icp
    @Query(value = "from WalletMedium as wallmed join fetch wallmed.individual as indiv where indiv.icp = :icp")
    Optional<WalletMedium> findWalletMediumByClientIcpOptional(String icp);

//    //find eurWallet by client icp
//    @Query(value = "from EuroWallet as eurow join fetch eurow.walletMedium as wallmed join fetch wallmed.individual as indiv where indiv.icp = :icp")
//    EuroWallet findWEuroWalletByClientIcp(String icp);
//
//    //find rubWallet by client icp
//    @Query(value = "from RubWallet as rub join fetch rub.walletMedium as wallmed join fetch wallmed.individual as indiv where indiv.icp = :icp")
//    RubWallet findRubWalletByClientIcp(String icp);
//
//    //find usdWallet by client icp
//    @Query(value = "from UsdWallet as usd join fetch usd.walletMedium as wallmed join fetch wallmed.individual as indiv where indiv.icp = :icp")
//    UsdWallet findUsdWalletByClientIcp(String icp);

}
