package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.model.Category;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(NewCategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Long catId);

    void deleteCategory(Long catId);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(Long catId);

    Category returnIfExists(Long catId);
}
