package guru.springframework.spring5recipeapp.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.spring5recipeapp.domain.Category;
import guru.springframework.spring5recipeapp.domain.Difficulty;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Notes;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.repositories.CategoryRepository;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final CategoryRepository categoryRepository;
	private final RecipeRepository recipeRepository;

	public DataLoader(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository,
			RecipeRepository recipeRepository) {
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.categoryRepository = categoryRepository;
		this.recipeRepository = recipeRepository;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		recipeRepository.saveAll(getRecipes());
	}

	private List<Recipe> getRecipes() {

		List<Recipe> recipes = new ArrayList<>(2);

		// get UOMs
		Optional<UnitOfMeasure> unitUomOptional = unitOfMeasureRepository.findByDescription("Unit");

		if (!unitUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM Not Found");
		}

		Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

		if (!tableSpoonUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM Not Found");
		}

		Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

		if (!teaSpoonUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM Not Found");
		}

		Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

		if (!dashUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM Not Found");
		}

		Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

		if (!pintUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM Not Found");
		}

		Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");

		if (!cupsUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM Not Found");
		}

		// get optionals
		UnitOfMeasure unitUom = unitUomOptional.get();
		UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
		UnitOfMeasure teaSpoonUom = tableSpoonUomOptional.get();
		UnitOfMeasure dashUom = dashUomOptional.get();
		UnitOfMeasure pintUom = pintUomOptional.get();
		UnitOfMeasure cupsUom = cupsUomOptional.get();

		// get Categories
		Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

		if (!americanCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category Not Found");
		}

		Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

		if (!mexicanCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category Not Found");
		}

		Category americanCategory = americanCategoryOptional.get();
		Category mexicanCategory = mexicanCategoryOptional.get();

		// Perfect Guacamole
		Recipe perfectGuacamole = new Recipe();
		perfectGuacamole.setDescription("Perfect Guacamole");
		perfectGuacamole.setPrepTime(10);
		perfectGuacamole.setCookTime(0);
		perfectGuacamole.setServings(4);
		perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
		perfectGuacamole.setDirections("1.Cut the avocado:\r\n"
				+ "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\\r\\n"
				+ "2.Mash the avocado flesh:\r\n"
				+ "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\r\n"
				+ "Add the remaining ingredients to taste:\r\n"
				+ "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\r\n"
				+ "\r\n"
				+ "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\r\n"
				+ "\r\n"
				+ "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\r\n"
				+ "Serve immediately:\r\n"
				+ "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\r\n"
				+ "\r\n"
				+ "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\r\n"
				+ "\r\n" + "Refrigerate leftover guacamole up to 3 days.\r\n" + "\r\n"
				+ "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");
		perfectGuacamole.setDifficulty(Difficulty.EASY);

		Notes perfectGuacamoleNote = new Notes();
		perfectGuacamoleNote.setRecipeNotes(
				"Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards");
		perfectGuacamoleNote.setRecipe(perfectGuacamole);
		perfectGuacamole.setNotes(perfectGuacamoleNote);

		Set<Ingredient> perfectGuacamoleIngredients = new HashSet<>();

		Ingredient avocado = new Ingredient();
		avocado.setAmount(new BigDecimal(2));
		avocado.setDescription("ripe avocado");
		avocado.setUom(unitUom);
		avocado.setRecipe(perfectGuacamole);
		perfectGuacamoleIngredients.add(avocado);

		Ingredient salt = new Ingredient();
		salt.setAmount(new BigDecimal(0.25));
		salt.setDescription("kosher salt");
		salt.setUom(teaSpoonUom);
		salt.setRecipe(perfectGuacamole);
		perfectGuacamoleIngredients.add(salt);

		Ingredient limeJuice = new Ingredient();
		limeJuice.setAmount(new BigDecimal(1));
		limeJuice.setDescription("fresh lime juice or lemon juice");
		limeJuice.setUom(tableSpoonUom);
		limeJuice.setRecipe(perfectGuacamole);
		perfectGuacamoleIngredients.add(limeJuice);

		Ingredient onion = new Ingredient();
		onion.setAmount(new BigDecimal(4));
		onion.setDescription("minced red onion or thinly sliced green onion");
		onion.setUom(tableSpoonUom);
		onion.setRecipe(perfectGuacamole);
		perfectGuacamoleIngredients.add(onion);

		Ingredient chilis = new Ingredient();
		chilis.setAmount(new BigDecimal(1));
		chilis.setDescription("serrano chilis, stems and seeds removed, minced");
		chilis.setUom(unitUom);
		chilis.setRecipe(perfectGuacamole);
		perfectGuacamoleIngredients.add(chilis);

		Ingredient cilantro = new Ingredient();
		cilantro.setAmount(new BigDecimal(2));
		cilantro.setDescription("cilantro");
		cilantro.setUom(tableSpoonUom);
		cilantro.setRecipe(perfectGuacamole);
		perfectGuacamoleIngredients.add(cilantro);

		Ingredient blackPepper = new Ingredient();
		blackPepper.setAmount(new BigDecimal(1));
		blackPepper.setDescription("freshly ground black pepper");
		blackPepper.setUom(dashUom);
		blackPepper.setRecipe(perfectGuacamole);
		perfectGuacamoleIngredients.add(blackPepper);

		Ingredient tomato = new Ingredient();
		tomato.setAmount(new BigDecimal(0.5));
		tomato.setDescription("ripe tomato, seeds and pulp removed, chopped");
		tomato.setUom(unitUom);
		tomato.setRecipe(perfectGuacamole);
		perfectGuacamoleIngredients.add(tomato);

		perfectGuacamole.setIngredients(perfectGuacamoleIngredients);

		perfectGuacamole.getCategories().add(mexicanCategory);
		perfectGuacamole.getCategories().add(americanCategory);

		recipes.add(perfectGuacamole);

		// Tacos
		Recipe tacosRecipe = new Recipe();
		tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
		tacosRecipe.setCookTime(9);
		tacosRecipe.setPrepTime(20);
		tacosRecipe.setServings(6);
		tacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
		tacosRecipe.setDifficulty(Difficulty.MODERATE);

		tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n"
				+ "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n"
				+ "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" + "\n"
				+ "\n"
				+ "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n"
				+ "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n"
				+ "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n"
				+ "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n"
				+ "\n" + "\n"
				+ "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

		Notes tacoNotes = new Notes();
		tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n"
				+ "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n"
				+ "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n"
				+ "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n"
				+ "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n"
				+ "\n" + "\n"
				+ "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
		tacoNotes.setRecipe(tacosRecipe);
		tacosRecipe.setNotes(tacoNotes);

		tacosRecipe.getIngredients()
				.add(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Dried Oregano", new BigDecimal(1), teaSpoonUom, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Dried Cumin", new BigDecimal(1), teaSpoonUom, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Sugar", new BigDecimal(1), teaSpoonUom, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Salt", new BigDecimal(".5"), teaSpoonUom, tacosRecipe));
		tacosRecipe.getIngredients()
				.add(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), unitUom, tacosRecipe));
		tacosRecipe.getIngredients()
				.add(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUom, tacosRecipe));
		tacosRecipe.getIngredients()
				.add(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom, tacosRecipe));
		tacosRecipe.getIngredients()
				.add(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUom, tacosRecipe));
		tacosRecipe.getIngredients()
				.add(new Ingredient("small corn tortillasr", new BigDecimal(8), unitUom, tacosRecipe));
		tacosRecipe.getIngredients()
				.add(new Ingredient("packed baby arugula", new BigDecimal(3), cupsUom, tacosRecipe));
		tacosRecipe.getIngredients()
				.add(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), unitUom, tacosRecipe));
		tacosRecipe.getIngredients()
				.add(new Ingredient("radishes, thinly sliced", new BigDecimal(4), unitUom, tacosRecipe));
		tacosRecipe.getIngredients()
				.add(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom, tacosRecipe));
		tacosRecipe.getIngredients()
				.add(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), unitUom, tacosRecipe));
		tacosRecipe.getIngredients()
				.add(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), unitUom, tacosRecipe));
		tacosRecipe.getIngredients().add(
				new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsUom, tacosRecipe));
		tacosRecipe.getIngredients()
				.add(new Ingredient("lime, cut into wedges", new BigDecimal(4), unitUom, tacosRecipe));

		tacosRecipe.getCategories().add(americanCategory);
		tacosRecipe.getCategories().add(mexicanCategory);

		recipes.add(tacosRecipe);

		return recipes;
	}

}
