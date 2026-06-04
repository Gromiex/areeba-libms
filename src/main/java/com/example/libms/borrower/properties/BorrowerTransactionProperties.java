package com.example.libms.borrower.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "borrower.transaction")
public class BorrowerTransactionProperties {

    private int limit;

}