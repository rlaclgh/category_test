package com.rlaclgh.hanteo.category;

import com.rlaclgh.hanteo.dto.CategoryDTO;
import com.rlaclgh.hanteo.dto.GetCategoriesDTO;
import com.rlaclgh.hanteo.entity.Category;
import com.rlaclgh.hanteo.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

  @Autowired
  private CategoryRepository categoryRepository;

  @GetMapping("/v1")
  public List<CategoryDTO> getCategoriesV1(
      GetCategoriesDTO getCategoriesDTO
  ) {

    List<Long> rootIds = categoryRepository.getRootCategoryIds(getCategoriesDTO);
    List<Category> categories = categoryRepository.findCategoriesByIdIn(rootIds);

    return getChildren(categories);

  }

  @GetMapping("/v2")
  public List<CategoryDTO> getCategoriesV2(
      GetCategoriesDTO getCategoriesDTO
  ) {
    List<Long> filteredIds = categoryRepository.getFilteredCategoryIds(getCategoriesDTO);
    List<Category> categories = categoryRepository.findCategoriesByIdIn(filteredIds);

    List<CategoryDTO> filteredWithChildren = getChildren(categories);


    List<CategoryDTO> rootCategories = new ArrayList<>();

    for (CategoryDTO categoryDTO: filteredWithChildren ) {
      if (categoryDTO.getParentId() == null ) {
        rootCategories.add(categoryDTO);
      } else {
        Long parentId = categoryDTO.getParentId();

        CategoryDTO parentCategoryDTO = null;
        CategoryDTO beforeCategoryDTO = categoryDTO;

        while (parentId != null) {
          Category parentCategory = categoryRepository.findById(parentId).orElseThrow(null);
          parentCategoryDTO = new CategoryDTO(parentCategory);
          parentCategoryDTO.addChild(beforeCategoryDTO);
          beforeCategoryDTO = parentCategoryDTO;
          parentId = parentCategoryDTO.getParentId();
        }
        rootCategories.add(parentCategoryDTO);
      }
    }
    return rootCategories;
  }

  @GetMapping("/v3")
  public List<CategoryDTO> getCategoriesV3(
      GetCategoriesDTO getCategoriesDTO
  ){
    List<Long> filteredIds = categoryRepository.getFilteredCategoryIds(getCategoriesDTO);
    List<Category> categories = categoryRepository.findCategoriesByIdIn(filteredIds);
    return getChildren(categories);
  }


  @GetMapping("/{categoryId}")
  public CategoryDTO getCategory(
      @PathVariable("categoryId") Long categoryId
  ) {

    Category category = categoryRepository.findById(categoryId).orElseThrow(null);
    List<CategoryDTO> categoryWithChildren = getChildren(Arrays.asList(category));


    CategoryDTO parentCategoryDTO = categoryWithChildren.get(0);
    CategoryDTO beforeCategoryDTO = categoryWithChildren.get(0);

    Long parentId = parentCategoryDTO.getParentId();

    if (parentId == null ) {
      return parentCategoryDTO;
    } else {

      while (parentId != null ) {
        Category parentCategory = categoryRepository.findById(parentId).orElseThrow(null);
        parentCategoryDTO = new CategoryDTO(parentCategory);
        parentCategoryDTO.addChild(beforeCategoryDTO);
        beforeCategoryDTO = parentCategoryDTO;
        parentId = parentCategoryDTO.getParentId();
      }
      return parentCategoryDTO;
    }
  }

  private List<CategoryDTO> getChildren(List<Category> categories) {


    Map<Long, CategoryDTO> categoryMap = new HashMap<>();
    List<CategoryDTO> rootCategories = new ArrayList<>();


    Queue<CategoryDTO> queue = new LinkedList<>();

    for (Category category : categories) {
      CategoryDTO categoryDTO = new CategoryDTO(category);
      categoryMap.put(categoryDTO.getId(), categoryDTO);
      queue.offer(categoryDTO);
      rootCategories.add(categoryDTO);
    }


    while (!queue.isEmpty()) {

      CategoryDTO categoryDTO = queue.poll();

      List<Category> categoryList = categoryRepository.findByParentId(categoryDTO.getId());

      for (Category category: categoryList) {
        CategoryDTO categoryDTO1 = new CategoryDTO(category);
        categoryMap.put(categoryDTO1.getId(), categoryDTO1);
        queue.offer(categoryDTO1);
        categoryDTO.addChild(categoryDTO1);
      }
    }

    return rootCategories;

  }


}
