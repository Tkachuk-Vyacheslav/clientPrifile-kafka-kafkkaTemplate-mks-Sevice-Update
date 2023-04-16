package org.client.repository.Wallet;

import org.client.common.entity.WalletMedium;
import org.client.common.entity.Wallets.EuroWallet;
import org.client.common.entity.Wallets.RubWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RubWalletRepository extends JpaRepository<RubWallet, String> {

    //find rubWallet by client icp
    @Query(value = "from RubWallet as rub join fetch rub.walletMedium as wallmed join fetch wallmed.individual as indiv where indiv.icp = :icp")
    RubWallet findRubWallByClientIcp(String icp);

}
