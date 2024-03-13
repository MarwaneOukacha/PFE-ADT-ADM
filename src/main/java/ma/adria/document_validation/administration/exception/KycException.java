package ma.adria.document_validation.administration.exception;

public class KycException extends RuntimeException{
	public KycException(){
		super("User already exist in keycloak");
	}
}
