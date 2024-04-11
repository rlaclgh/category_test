package com.rlaclgh.hanteo.repository;

import com.rlaclgh.hanteo.dto.GetCategoriesDTO;
import java.util.List;

public interface CategoryRepositoryCustom {

  List<Long> getRootCategoryIds(GetCategoriesDTO getCategoriesDTO);

  List<Long> getFilteredCategoryIds(GetCategoriesDTO getCategoriesDTO);

}
