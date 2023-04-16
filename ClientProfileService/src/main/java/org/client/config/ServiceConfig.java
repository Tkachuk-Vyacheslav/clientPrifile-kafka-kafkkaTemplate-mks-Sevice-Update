package org.client.config;

import org.client.MainApp;
import org.client.service.AddressService;
import org.client.service.IndividualService;
import org.client.service.PassportService;
import org.client.service.WalletService;
import org.client.service.impl.AddressServiceImpl;
import org.client.service.impl.IndividualServiceImpl;
import org.client.service.impl.PassportServiseImpl;
import org.client.service.impl.WalletServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class ServiceConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public IndividualService individualService() {

        return new IndividualServiceImpl();
    }

    @Bean
    public AddressService addressService(IndividualService individualService) {
        return new AddressServiceImpl(individualService);
    }

    @Bean
    public WalletService walletService(IndividualService individualService) {
        return new WalletServiceImpl(individualService);
    }

    @Bean
    public PassportService passportService(IndividualService individualService) {
        return new PassportServiseImpl(individualService);
    }

    @Bean
    public MainApp mainApp(WalletService walletService) {
        return new MainApp(walletService);
    }


//    @Bean
//    public ContactService contactService(IndividualService individualService) {
//        return new ContactServiceImpl(individualService);
//    }

}
