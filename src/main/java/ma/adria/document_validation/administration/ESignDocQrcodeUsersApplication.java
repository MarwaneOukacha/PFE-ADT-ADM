package ma.adria.document_validation.administration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;




@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaAuditing
@EnableAsync
public class ESignDocQrcodeUsersApplication {
	
    
	public static void main(String[] args) {
		SpringApplication.run(ESignDocQrcodeUsersApplication.class, args);
	}

	
}
