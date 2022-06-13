package guru.springframework.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.spring5recipeapp.services.IngredientService;
import guru.springframework.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {
	
	private final RecipeService recipeService;
	private final IngredientService ingredientService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}

	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(Model model, @PathVariable String recipeId) {
		log.debug("Getting ingredient list for recipe id: " + recipeId);
		model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(recipeId)));
		return "recipe/ingredient/list";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
	public String getIngredients(Model model, @PathVariable String recipeId, @PathVariable String ingredientId) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.parseLong(recipeId), Long.parseLong(ingredientId)));
		return "recipe/ingredient/show";
	}
}
