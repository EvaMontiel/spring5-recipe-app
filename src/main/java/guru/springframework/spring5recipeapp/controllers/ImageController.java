package guru.springframework.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.spring5recipeapp.services.ImageService;
import guru.springframework.spring5recipeapp.services.RecipeService;

@Controller
public class ImageController {

	private final ImageService imageService;
	private final RecipeService recipeService;
	
	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}
	
	@GetMapping("/recipe/{recipeId}/image")
	public String getImageForm(Model model, @PathVariable String recipeId) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(recipeId)));
		return "recipe/imageuploadform";
	}
	
	@PostMapping("/recipe/{recipeId}/image")
	public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
		imageService.saveImageFile(Long.parseLong(recipeId), file);
		return "redirect:/recipe/" + recipeId + "/show"; 
	}
}
