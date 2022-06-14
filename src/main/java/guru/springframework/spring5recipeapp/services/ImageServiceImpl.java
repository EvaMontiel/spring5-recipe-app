package guru.springframework.spring5recipeapp.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
	
	private final RecipeRepository recipeRepository;

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {
		log.debug("Recieved a file");
		try {
			Recipe recipe = recipeRepository.findById(recipeId).get();
			
			Byte[] byteObjects = new Byte[file.getBytes().length];
			
			int i = 0;
			
			for(byte b : file.getBytes()) {
				byteObjects[i++] = b;
			}
			
			recipe.setImage(byteObjects);
			
			recipeRepository.save(recipe);
		}catch (IOException e) {
			//TODO handle better
			log.debug("Error ocurred", e);
			e.printStackTrace();
		}
	}

}
