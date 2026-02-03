package es.udc.tfg.app.categoryService;

import es.udc.tfg.app.model.Category.Category;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.categoryService.CategoryData;
import es.udc.tfg.app.service.categoryService.CategoryService;
import es.udc.tfg.app.service.userService.RegisterData;
import es.udc.tfg.app.service.userService.UserService;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    private final String VALID_NAME = "Electrónica";
    private final String VALID_NAME_2 = "Libros";
    private final String VALID_DESCRIPTION = "Categoría de produtos electrónicos";
    private final String VALID_DESCRIPTION_2 = "Categoría de libros e novelas";

    private CategoryData getValidCategoryData1() {
        return new CategoryData(VALID_NAME, VALID_DESCRIPTION);
    }

    private CategoryData getValidCategoryData2() {
        return new CategoryData(VALID_NAME_2, VALID_DESCRIPTION_2);
    }

    private User getCreatorUser() throws Exception{
        // Rexistramos un usuario novo
        RegisterData registerData = new RegisterData(
                "Pepe",
                "Pérez",
                "12345678A",
                "pepe@example.com",
                "01/01/1990",
                "ESP",
                "ADMIN",
                "");
        User user = userService.registerUser(registerData);
        userService.setUserPassword(user.getDni(),user.getToken(),"passwordValido1!");
        return user;
    }

    @Test
    public void testCreateAndFindCategoryById() throws Exception {
        User creator = getCreatorUser();
        Category category = categoryService.createCategory(getValidCategoryData1(), creator.getId());

        Category found = categoryService.findCategoryById(category.getId());

        assertEquals(VALID_NAME, found.getName());
        assertEquals(VALID_DESCRIPTION, found.getDescription());
        assertEquals(creator.getId(), found.getCreator().getId());
        assertNotNull(found.getCreateDate());
    }

    @Test(expected = InputValidationException.class)
    public void testCreateCategoryInvalidName() throws Exception {
        User creator = getCreatorUser();
        CategoryData invalidData = new CategoryData("", VALID_DESCRIPTION);
        categoryService.createCategory(invalidData, creator.getId());
    }

    @Test
    public void testUpdateCategory() throws Exception {
        User creator = getCreatorUser();
        Category category = categoryService.createCategory(getValidCategoryData1(), creator.getId());

        categoryService.updateCategory(category.getId(), getValidCategoryData2());

        assertEquals(VALID_NAME_2, category.getName());
        assertEquals(VALID_DESCRIPTION_2, category.getDescription());
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateCategoryNotFound() throws Exception {
        CategoryData data = getValidCategoryData1();
        categoryService.updateCategory(1L, data);
    }

    @Test
    public void testFindCategoryByNameAndFindAll() throws Exception {
        User creator = getCreatorUser();
        categoryService.createCategory(getValidCategoryData1(), creator.getId());
        categoryService.createCategory(getValidCategoryData2(), creator.getId());

        Block<Category> block = categoryService.findCategoryByName("Libros", 0, 10);

        assertEquals(1, block.getItems().size());
        assertEquals(VALID_NAME_2, block.getItems().get(0).getName());

        block = categoryService.findAllCategories(0, 10);

        assertEquals(2, block.getItems().size());
        assertFalse(block.getExistMoreItems());
    }
}
