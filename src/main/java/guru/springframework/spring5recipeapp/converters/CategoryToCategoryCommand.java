package guru.springframework.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.spring5recipeapp.commands.CategoryCommand;
import guru.springframework.spring5recipeapp.domain.Category;
import lombok.Synchronized;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand>{

	@Synchronized
	@Nullable
	@Override
	public CategoryCommand convert(Category category) {
		if(category == null) {
			return null;
		}
		final CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(category.getId());
		categoryCommand.setDescription(category.getDescription());
		return categoryCommand;
	}

}
