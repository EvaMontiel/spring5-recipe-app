package guru.springframework.spring5recipeapp.services;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.spring5recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.debug("I'm in the service");
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		return recipeSet;
	}

	@Override
	public Recipe findById(Long id) {
		return recipeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Recipe Not Found for ID Value: " + id.toString()));
	}

	@Override
	@Transactional
	public RecipeCommand savedRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		log.debug("Saved RecipeId:" + savedRecipe.getId());
		return recipeToRecipeCommand.convert(savedRecipe);
	}

	@Override
	@Transactional
	public RecipeCommand findCommandById(Long id) {
		Recipe recipe = findById(id);
		return recipeToRecipeCommand.convert(recipe);
	}

	@Override
	public void deleteById(Long id) {
		recipeRepository.deleteById(id);
	}

}
