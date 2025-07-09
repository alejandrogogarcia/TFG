package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.model.Category.Category;
import es.udc.tfg.app.rest.dtos.CategoryDto;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryConversor {

    public static CategoryDto toCategoryDto(Category category) {

        return new CategoryDto(category.getId(), category.getName(), category.getDescription(), CalendarConversor.calendarToString(category.getCreateDate()), category.getCreator().getId());
    }

    public static List<CategoryDto> toCategoryListDto(List<Category> categories) {

        return categories.stream().map(CategoryConversor::toCategoryDto).collect(Collectors.toList());
    }
}
