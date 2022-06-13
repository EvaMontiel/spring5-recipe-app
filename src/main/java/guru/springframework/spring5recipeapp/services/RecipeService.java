package guru.springframework.spring5recipeapp.services;

import java.util.Set;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();
	
	Recipe findById(Long id);
	
	RecipeCommand savedRecipeCommand(RecipeCommand command);
	
	RecipeCommand findCommandById(Long id);
	
	void deleteById(Long id);
}
