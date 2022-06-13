package guru.springframework.spring5recipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
	
	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImpl(RecipeRepository recipeRepository,
								 IngredientToIngredientCommand ingredientToIngredientCommand, 
								 UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			log.debug("recipe id not found. Id: " + recipeId);
			//TODO
		}
		Recipe recipe = recipeOptional.get();
		
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
				.findFirst();
		
		if (!ingredientCommandOptional.isPresent()) {
			log.debug("Ingredient id not found. Id: " + ingredientId);
			//TODO 
		}
		
		return ingredientCommandOptional.get();
	}

	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
		if (!recipeOptional.isPresent()) {
			//TODO toss error if not found
			log.debug("recipe id not found. Id: " + ingredientCommand.getRecipeId());
			return new IngredientCommand();
		} else {
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
					.findFirst();
			
			if (ingredientOptional.isPresent()) {
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(ingredientCommand.getDescription());
				ingredientFound.setAmount(ingredientCommand.getAmount());
				ingredientFound.setUom(unitOfMeasureRepository.
						findById(ingredientCommand.getUom().getId())
						.orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));//TODO
			} else {
				recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
			}
			
			Recipe savedRecipe = recipeRepository.save(recipe);
			
			return savedRecipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
					.map(ingredientToIngredientCommand::convert)
					.findFirst()
					.get();
				
		}
		
	}

}
