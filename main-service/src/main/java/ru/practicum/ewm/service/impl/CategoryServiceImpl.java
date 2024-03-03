package ru.practicum.ewm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.CategoryMapper;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;


    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto categoryDto) {
        Category category = CategoryMapper.toCategory(categoryDto);
        log.info("Создание категории - {}", category);
        return CategoryMapper.toCategoryDto(repository.save(category));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long catId) {
        Category category = returnIfExists(catId);
        category.setName(categoryDto.getName());
        log.info("Обновление категории - {}", category);
        return CategoryMapper.toCategoryDto(repository.save(category));
    }

    @Override
    public void deleteCategory(Long catId) {
        Category category = returnIfExists(catId);
        log.info("Удаление категории - {}", category);
        repository.delete(category);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        log.info("Получение категорий от - {} размером - {}", from, size);
        PageRequest page = PageRequest.of(from / size, size);
        Page<Category> categories = repository.findAll(page);
        return categories.stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        Category category = returnIfExists(catId);
        log.info("Получение категории - {}", category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public Category returnIfExists(Long catId) {
        return repository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category", catId));
    }
}
