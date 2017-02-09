package itn.issuemanager.domain;

public class ValidationError {
	private String fieldName;
	
	private String errorMessage;
	
	public ValidationError(String fieldName, String errorMessage){
		this.fieldName = fieldName;
		this.errorMessage = errorMessage;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ValidationError [fieldName=" + fieldName + ", errorMessage=" + errorMessage + "]";
	}
	
	
	
	
}
