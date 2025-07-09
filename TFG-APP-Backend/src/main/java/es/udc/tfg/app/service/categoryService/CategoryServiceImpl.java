package es.udc.tfg.app.service.categoryService;
import es.udc.tfg.app.model.Category.CategoryDao;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.model.user.UserDao;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import es.udc.tfg.app.util.validator.ValidatorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.udc.tfg.app.model.Category.Category;

import java.util.List;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Category createCategory(CategoryData categoryData, Long creatorId) throws InstanceNotFoundException, InputValidationException {
        User creator = userDao.find(creatorId);
        ValidatorProperties.validateString(categoryData.getName());
        Category category = new Category(categoryData.getName(), categoryData.getDescription(), creator);
        categoryDao.save(category);
        return category;
    }

    @Override
    public void updateCategory(Long categoryId, CategoryData categoryData) throws InstanceNotFoundException, InputValidationException {

        Category category = categoryDao.find(categoryId);
        ValidatorProperties.validateString(categoryData.getName());
        category.setName(categoryData.getName());
        category.setDescription(categoryData.getDescription());
    }

    @Override
    public Category findCategoryById(Long categoryId) throws InstanceNotFoundException {
        return categoryDao.find(categoryId);
    }

    @Override
    public Block<Category> findCategoryByName(String name, int page, int size) {
        Slice<Category> slice = categoryDao.findByName(name, page, size);

        return new Block<>(slice.getContent(), slice.hasNext());
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryDao.findAll();
    }
}
