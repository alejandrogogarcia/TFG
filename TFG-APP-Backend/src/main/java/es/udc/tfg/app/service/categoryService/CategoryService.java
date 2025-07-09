package es.udc.tfg.app.service.categoryService;
import es.udc.tfg.app.model.Category.Category;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;

import java.util.List;

public interface CategoryService {

    public Category createCategory(CategoryData categoryData, Long creatorId) throws InstanceNotFoundException, InputValidationException;

    public void updateCategory(Long categoryId, CategoryData categoryData) throws InstanceNotFoundException, InputValidationException;

    public Category findCategoryById(Long categoryId) throws InstanceNotFoundException;

    public Block<Category> findCategoryByName(String name, int page, int size);

    public List<Category> findAllCategories();

}
