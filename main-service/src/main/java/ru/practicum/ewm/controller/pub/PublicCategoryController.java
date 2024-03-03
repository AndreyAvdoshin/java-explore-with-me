package ru.practicum.ewm.controller.pub;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.service.CategoryService;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CategoryService service;

    public PublicCategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                           @RequestParam(defaultValue = "10") @Positive int size) {
        return service.getCategories(from, size);
    }

    @GetMapping
    @RequestMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable @Positive Long catId) {
        return  service.getCategoryById(catId);
    }
}
