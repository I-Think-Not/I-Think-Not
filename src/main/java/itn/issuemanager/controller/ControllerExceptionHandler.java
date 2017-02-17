package itn.issuemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import itn.issuemanager.domain.ForbiddenTypeException;
import itn.issuemanager.domain.ForbiddenTypeFileException;


@Controller
public class ControllerExceptionHandler implements ErrorController{

	
	private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	private static final String PATH = "/error";
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@RequestMapping(value = PATH)
	@ExceptionHandler(Exception.class)
	public String DefaultError() {
		log.debug("exception");
		return "exception/serverError";
	}
	
	@Override
	public String getErrorPath() {
		return PATH;
	}
}
