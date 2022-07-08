package guru.springframework.spring5recipeapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleBadRequest(Exception exception) {
		log.error("Handling Number Format exception");
		log.error(exception.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("400error");
		modelAndView.addObject("exception", exception);
		
		return modelAndView;
	}
}
