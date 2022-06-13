package guru.springframework.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.spring5recipeapp.commands.CategoryCommand;
import guru.springframework.spring5recipeapp.domain.Category;
import lombok.Synchronized;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category>{

	@Synchronized
	@Nullable
	@Override
	public Category convert(CategoryCommand categoryCommand) {
		if(categoryCommand == null) {
			return null;
		}
		final Category category = new Category();
		category.setId(categoryCommand.getId());
		category.setDescription(categoryCommand.getDescription());
		return category;
	}

}
