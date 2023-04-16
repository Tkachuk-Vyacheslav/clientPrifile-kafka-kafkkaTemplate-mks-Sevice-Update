package org.client.common.entity.Wallets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.client.common.entity.WalletMedium;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EuroWallet {

    @Id   //String id = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)).substring(2, 28);
    private String uuid;

    private String value;

    //Двусторонний OneToOne
    @OneToOne(mappedBy = "euroWallets", cascade = CascadeType.ALL)
    private WalletMedium walletMedium;

}
