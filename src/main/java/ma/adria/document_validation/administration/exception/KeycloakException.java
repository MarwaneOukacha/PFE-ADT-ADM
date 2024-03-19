package ma.adria.document_validation.administration.exception;

public class KeycloakException extends RuntimeException{
	public KeycloakException(){
		super("User already exist in keycloak");
	}
}
