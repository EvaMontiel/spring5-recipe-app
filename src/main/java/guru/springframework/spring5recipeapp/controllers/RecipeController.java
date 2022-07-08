package guru.springframework.spring5recipeapp.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping("/{id}/show")
	public String showById(Model model, @PathVariable String id) {
		model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));
		return "recipe/show";
	}
	
	@GetMapping("/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return RECIPE_RECIPEFORM_URL;
	}
	
	@GetMapping("/{id}/update")
	public String updateRecipe(Model model, @PathVariable String id) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));
		return RECIPE_RECIPEFORM_URL;
	}
	
	@PostMapping
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			bindingResult.getAllErrors().forEach(objectError -> {
				log.debug(objectError.toString());
			});
			
			return RECIPE_RECIPEFORM_URL;
		}
		
		RecipeCommand savedCommand = recipeService.savedRecipeCommand(command);
		return "redirect:/recipe/" + savedCommand.getId() + "/show" ;
	}
	
	@GetMapping("/{id}/delete")
	public String deleteRecipe(@PathVariable String id) {
		log.debug("Deleting id: " + id);
		recipeService.deleteById(Long.parseLong(id));
		return "redirect:/";
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		log.error("Handling not found exception");
		log.error(exception.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("404error");
		modelAndView.addObject("exception", exception);
		
		return modelAndView;
	}
	
}
