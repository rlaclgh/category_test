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

    // 필터링된 카테고리의 최상단 ID 리스트를 불러옵니다.
    List<Long> rootIds = categoryRepository.getRootCategoryIds(getCategoriesDTO);

    // ID 리스트에 해당하는 카테고리들을 불러옵니다.
    List<Category> categories = categoryRepository.findCategoriesByIdIn(rootIds);

    // category 리스트의 하위 카테고리들을 불러옵니다.
    return getChildren(categories);

  }

  @GetMapping("/v2")
  public List<CategoryDTO> getCategoriesV2(
      GetCategoriesDTO getCategoriesDTO
  ) {

    // 필터링된 카테고리 ID 리스트를 불러옵니다.
    List<Long> filteredIds = categoryRepository.getFilteredCategoryIds(getCategoriesDTO);

    // ID 리스트에 해당하는 카테고리들을 불러옵니다.
    List<Category> categories = categoryRepository.findCategoriesByIdIn(filteredIds);

    // category 리스트의 하위 카테고리들을 불러옵니다.
    List<CategoryDTO> filteredWithChildren = getChildren(categories);


    // 상위 카테고리를 불러옵니다.
    List<CategoryDTO> rootCategories = new ArrayList<>();

    for (CategoryDTO categoryDTO: filteredWithChildren ) {

      // 최상위 카테고리라면 rootCategories 에 추가합니다.
      if (categoryDTO.getParentId() == null ) {
        rootCategories.add(categoryDTO);
      } else {
        // 상위 카테고리를 불러옵니다.
        Long parentId = categoryDTO.getParentId();

        CategoryDTO parentCategoryDTO = null;
        CategoryDTO beforeCategoryDTO = categoryDTO;

        // 상위 카테고리가 없을 때, 즉 최상위 카테고리일때까지 반복합니다.
        while (parentId != null) {
          Category parentCategory = categoryRepository.findById(parentId).orElseThrow(null);
          parentCategoryDTO = new CategoryDTO(parentCategory);
          parentCategoryDTO.addChild(beforeCategoryDTO);
          beforeCategoryDTO = parentCategoryDTO;
          parentId = parentCategoryDTO.getParentId();
        }

        // 반복해서 구한 최상위 카테고리를 rootCategories 에 추가합니다.
        rootCategories.add(parentCategoryDTO);
      }
    }
    return rootCategories;
  }

  @GetMapping("/v3")
  public List<CategoryDTO> getCategoriesV3(
      GetCategoriesDTO getCategoriesDTO
  ){
    // 필터링된 카테고리 ID 리스트를 불러옵니다.
    List<Long> filteredIds = categoryRepository.getFilteredCategoryIds(getCategoriesDTO);

    // ID 리스트에 해당하는 카테고리들을 불러옵니다.
    List<Category> categories = categoryRepository.findCategoriesByIdIn(filteredIds);

    // category 리스트의 하위 카테고리들을 불러옵니다.
    return getChildren(categories);
  }


  @GetMapping("/{categoryId}")
  public CategoryDTO getCategory(
      @PathVariable("categoryId") Long categoryId
  ) {
    // id 로 카테고리를 불러옵니다. ( 존재하지 않은 카테고리의 경우를 고려하지 않았습니다. )
    Category category = categoryRepository.findById(categoryId).orElseThrow(null);

    // 해당 카테고리의 하위 카테고리들을 불러옵니다.
    List<CategoryDTO> categoryWithChildren = getChildren(Arrays.asList(category));



    // 최상위 카테고리를 찾을 때까지 반복하여 상위 카테고리를 불러옵니다.
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



  // 카테고리의 하위 카테고리들을 불러오는 함수입니다.
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

    // 하위 카테고리 리스트를 구하지 않은 카테고리를 queue에 넣고 하나씩 추가해 하위 카테고리 리스트를 구합니다.
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
