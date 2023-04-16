package org.client.repository.Wallet;

import org.client.common.entity.Individual;
import org.client.common.entity.WalletMedium;
import org.client.common.entity.Wallets.EuroWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EuroWalletRepository  extends JpaRepository<EuroWallet, String> {

    //find euroWallet by client icp
    @Query(value = "from EuroWallet as eur join fetch eur.walletMedium as wallmed join fetch wallmed.individual as indiv where indiv.icp = :icp")
    EuroWallet findEuroWallByClientIcp(String icp);

    @Query(value = "from EuroWallet as eur join fetch eur.walletMedium as wallmed join fetch wallmed.individual as indiv where indiv.icp = :icp")
    Optional<EuroWallet> findEuroWallByClientIcpOptional(String icp);

}
