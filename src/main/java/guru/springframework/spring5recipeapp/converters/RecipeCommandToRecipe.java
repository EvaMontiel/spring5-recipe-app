package guru.springframework.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import lombok.Synchronized;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe>{

	private final NotesCommandToNotes notesConverter;
	private final IngredientCommandToIngredient ingredientConverter;
	private final CategoryCommandToCategory categoryConverter;
	
	public RecipeCommandToRecipe(NotesCommandToNotes notesConverter, IngredientCommandToIngredient ingredientConverter,
			CategoryCommandToCategory categoryConverter) {
		this.notesConverter = notesConverter;
		this.ingredientConverter = ingredientConverter;
		this.categoryConverter = categoryConverter;
	}

    @Synchronized
    @Nullable
    @Override
	public Recipe convert(RecipeCommand recipeCommand) {
		if (recipeCommand == null) {
			return null;
		}
		final Recipe recipe = new Recipe();
		recipe.setId(recipeCommand.getId());
		recipe.setDescription(recipeCommand.getDescription());
		recipe.setPrepTime(recipeCommand.getPrepTime());
		recipe.setCookTime(recipeCommand.getCookTime());
		recipe.setServings(recipeCommand.getServings());
		recipe.setSource(recipeCommand.getSource());
		recipe.setUrl(recipeCommand.getUrl());
		recipe.setDirections(recipeCommand.getDirections());
		recipe.setDifficulty(recipeCommand.getDifficulty());
		recipe.setNotes(notesConverter.convert(recipeCommand.getNotes()));
		
		if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0) {
			recipeCommand.getIngredients().forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
		}
		
		if(recipeCommand.getCategories() != null && recipeCommand.getCategories().size() > 0) {
			recipeCommand.getCategories().forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));
		}
		
		return recipe;
	}

}
