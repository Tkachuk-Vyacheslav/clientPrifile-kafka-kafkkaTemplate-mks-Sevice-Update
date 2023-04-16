package org.client.common.entity.Wallets;

import lombok.Getter;
import lombok.Setter;
import org.client.common.entity.WalletMedium;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.client.common.entity.Individual;
import org.client.common.entity.WalletMedium;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Getter
@Setter
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsdWallet {


    @Id  //String id = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)).substring(2, 28);
    private String uuid;

    private String value;

    //Двусторонний OneToOne
    @OneToOne(mappedBy = "usdWallets", cascade = CascadeType.ALL)
    private WalletMedium walletMedium;

}
