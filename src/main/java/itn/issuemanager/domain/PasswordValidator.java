package itn.issuemanager.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordValidator implements ConstraintValidator<Password, String>{
	
	private static final Logger log = LoggerFactory.getLogger(PasswordValidator.class);
	
	@Override
	public void initialize(Password arg0) {}
	
	@Override
	public boolean isValid(String password, ConstraintValidatorContext ctx) {
		log.debug("validate : " + password);
		if(password == null) {
			log.debug("null : " + password);
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate("비밀번호를 입력해주세요.").addConstraintViolation();
			return false;			
		}
		else if(password.length() < 8 || password.length() > 13) {
			log.debug("length : " + password);
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate("비밀번호는 8자 이상 12자 이하를 입력하셔야 합니다.").addConstraintViolation();
			return false;
		}
		else if(!password.matches(".*[!,@,#,$,%,^,&,*,?,_,~]+.*")) {
			log.debug("no special : " + password);
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate("특수문자가 입력되지 않았습니다. 비밀번호는 문자,숫자,특수문자가 모두 한글자 이상 포함되어야 합니다.").addConstraintViolation();
			return false;
		}
		else if(!password.matches(".*[a-zA-Z]+.*")) {
			log.debug("no riteral : " + password);
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate("문자가 입력되지 않았습니다. 비밀번호는 문자,숫자,특수문자가 모두 한글자 이상 포함되어야 합니다.").addConstraintViolation();
			return false;
		}
		else if(!password.matches(".*[0-9]+.*")) {
			log.debug("no riteral : " + password);
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate("숫자가 입력되지 않았습니다. 비밀번호는 문자,숫자,특수문자가 모두 한글자 이상 포함되어야 합니다.").addConstraintViolation();
			return false;
		}
		
		return true;
	}
}
