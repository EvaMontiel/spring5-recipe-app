package guru.springframework.spring5recipeapp.services;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

public class IngredientServiceImplTest {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	IngredientService ingredientService;

	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, unitOfMeasureRepository, ingredientCommandToIngredient);
	}

	@Test
	public void testFindByRecipeIdAndIngredientId() {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);
		
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		//when
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);
		
		//then
		assertEquals((Long)3L, ingredientCommand.getId());
		assertEquals((Long)1L, ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());
		
	}
	
	@Test
	public void testSaveIngredientCommand() throws Exception {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(3L);
		ingredientCommand.setRecipeId(2L);
		
		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(3L);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		//when
		 IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
		 
		 //then
		 assertEquals((Long)3L, savedIngredientCommand.getId());
		 verify(recipeRepository, times(1)).findById(anyLong());
		 verify(recipeRepository, times(1)).save(any());
	}

}
