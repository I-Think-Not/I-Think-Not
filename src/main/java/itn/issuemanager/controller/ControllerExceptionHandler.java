package itn.issuemanager.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import itn.issuemanager.domain.ForbiddenTypeFileException;


@Controller
public class ControllerExceptionHandler implements ErrorController{

	private static final String PATH = "/error";
	
	@RequestMapping(value = PATH)
	@ExceptionHandler(Exception.class)
	public String ForbiddenTypeError() {
		return "exception/serverError";
	}
	
	@Override
	public String getErrorPath() {
		return PATH;
	}
}
