package guru.springframework.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.services.RecipeService;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@RequestMapping("/{id}/show")
	public String showById(Model model, @PathVariable String id) {
		model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));
		return "recipe/show";
	}
	
	@RequestMapping("/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	@RequestMapping("/{id}/update")
	public String updateRecipe(Model model, @PathVariable String id) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));
		return "recipe/recipeform";
	}
	
	@PostMapping
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		RecipeCommand savedCommand = recipeService.savedRecipeCommand(command);
		return "redirect:/recipe/" + savedCommand.getId() + "/show" ;
	}
}
