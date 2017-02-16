package itn.issuemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import itn.issuemanager.domain.ForbiddenTypeFileException;

@Controller
@RequestMapping("/error")
public class ControllerExceptionHandler {
	@ExceptionHandler(ForbiddenTypeFileException.class)
	public String ForbiddenTypeError() {
		return "exception/serverError";
	}
}
