package ma.adria.document_validation.administration;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;




@SpringBootApplication
@EnableTransactionManagement
public class ESignDocQrcodeUsersApplication {
	
    
	public static void main(String[] args) {
		SpringApplication.run(ESignDocQrcodeUsersApplication.class, args);
	}

	
}
