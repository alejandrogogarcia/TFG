package es.udc.tfg.app.rest.controller;

import es.udc.tfg.app.model.Category.Category;
import es.udc.tfg.app.rest.dtos.CategoryDto;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.categoryService.CategoryData;
import es.udc.tfg.app.service.categoryService.CategoryService;
import es.udc.tfg.app.util.conversors.CategoryConversor;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryData categoryData, @RequestAttribute Long userId) throws InstanceNotFoundException, InputValidationException {

        Category category = categoryService.createCategory(categoryData, userId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").build().toUri();
        return ResponseEntity.created(location).body(CategoryConversor.toCategoryDto(category));
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCategory(@PathVariable Long id, @RequestBody CategoryData categoryData) throws InstanceNotFoundException, InputValidationException {

        categoryService.updateCategory(id, categoryData);
    }

    @GetMapping("/{id}")
    public CategoryDto findCategoryById(@PathVariable Long id) throws InstanceNotFoundException {
        return CategoryConversor.toCategoryDto(categoryService.findCategoryById(id));
    }

    @GetMapping("/find")
    public Block<CategoryDto> findCategoriesByName(@RequestParam (required=false) String name, @RequestParam(defaultValue="0") int page) {
        Block<Category> categoryBlock = categoryService.findCategoryByName(name != null ? name.trim() : " ", page, 5 );
        return new Block<>(CategoryConversor.toCategoryListDto(categoryBlock.getItems()), categoryBlock.getExistMoreItems());
    }

}
