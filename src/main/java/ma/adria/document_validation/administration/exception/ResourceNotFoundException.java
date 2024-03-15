package ma.adria.document_validation.administration.exception;

import ma.adria.document_validation.administration.model.enums.ErrorCode;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(ErrorCode err) {
		super(err.toString());
	}
}
