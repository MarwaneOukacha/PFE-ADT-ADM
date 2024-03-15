package ma.adria.document_validation.administration.config.runners;

import ma.adria.document_validation.administration.dao.IADTConstDAO;
import ma.adria.document_validation.administration.model.entities.ADTConst;
import ma.adria.document_validation.administration.model.enums.ADTConstCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.transaction.Transactional;

@Transactional
@Configuration("adtConstRunner")
@Order(1)
public class ADTConstRunner {
    @Value("${app.constants.DEFAULT_MAX_TRANSACTIONS}")
    private String numbreTransactionMax;
    @Value("${app.constants.SIZE}")
    private String size;
    @Bean
    public CommandLineRunner commandLine(IADTConstDAO adtConstDAO) {
        return args -> {
            if (!adtConstDAO.existsByCode(ADTConstCode.MAX_NUMBRE_TRANSACTION)) {
                adtConstDAO.save(
                        ADTConst.builder()
                                .code(ADTConstCode.MAX_NUMBRE_TRANSACTION)
                                .value(numbreTransactionMax)
                                .build()
                );
            }
            if (!adtConstDAO.existsByCode(ADTConstCode.MAX_SIZE)) {
                adtConstDAO.save(
                        ADTConst.builder()
                                .code(ADTConstCode.MAX_SIZE)
                                .value(size)
                                .build()
                );
            }
        };
    }
}
