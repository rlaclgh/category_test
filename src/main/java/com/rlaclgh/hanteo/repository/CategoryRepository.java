package com.rlaclgh.hanteo.repository;

import com.rlaclgh.hanteo.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

  List<Category> findByParentId(Long parentId);

  List<Category> findCategoriesByIdIn(List<Long> categoryIds);

}
