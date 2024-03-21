package ma.adria.document_validation.administration.config.runners;

import ma.adria.document_validation.administration.dao.IADTConstDAO;
import ma.adria.document_validation.administration.model.entities.ADTConst;
import ma.adria.document_validation.administration.model.enums.ADTConstCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.transaction.Transactional;

@Transactional
@Configuration("adtConstRunner")
@Order(1)
public class ADTConstRunner implements CommandLineRunner {

    private final IADTConstDAO adtConstDAO;

    @Value("${app.constants.transaction-limit-per-day}")
    private String dailyTransactionLimit;

    @Value("${app.constants.document-max-size-MB}")
    private String documentMaxSizeMB;
    @Value("${app.constants.client-app-transaction-limit-per-day}")
    private String ClientAppDailyTransactionLimit;

    @Value("${app.constants.client-app-document-max-size-MB}")
    private String ClientAppDocumentMaxSizeMB;
    public ADTConstRunner(IADTConstDAO adtConstDAO) {
        this.adtConstDAO = adtConstDAO;
    }

    private void saveTransactionConst() {

        if (!adtConstDAO.existsByCode(ADTConstCode.TRANSACTION_LIMIT_PER_DAY)) {
            adtConstDAO.save(ADTConst.builder()
                            .code(ADTConstCode.TRANSACTION_LIMIT_PER_DAY)
                            .value(dailyTransactionLimit)
                            .build()
            );
        }

        if (!adtConstDAO.existsByCode(ADTConstCode.DOCUMENT_MAX_SIZE_MB)) {
            adtConstDAO.save(ADTConst.builder()
                            .code(ADTConstCode.DOCUMENT_MAX_SIZE_MB)
                            .value(documentMaxSizeMB)
                            .build()
            );
        }
        if (!adtConstDAO.existsByCode(ADTConstCode.CLIENT_APP_TRANSACTION_LIMIT_PER_DAY)) {
            adtConstDAO.save(ADTConst.builder()
                    .code(ADTConstCode.CLIENT_APP_TRANSACTION_LIMIT_PER_DAY)
                    .value(ClientAppDailyTransactionLimit)
                    .build()
            );
        }
        if (!adtConstDAO.existsByCode(ADTConstCode.CLIENT_APP_DOCUMENT_MAX_SIZE_MB)) {
            adtConstDAO.save(ADTConst.builder()
                    .code(ADTConstCode.CLIENT_APP_DOCUMENT_MAX_SIZE_MB)
                    .value(ClientAppDocumentMaxSizeMB)
                    .build()
            );
        }
    }

    @Override
    public void run(String... args) {

        saveTransactionConst();

    }
}
