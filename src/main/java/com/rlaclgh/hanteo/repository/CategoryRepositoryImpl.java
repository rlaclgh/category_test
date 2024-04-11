package com.rlaclgh.hanteo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rlaclgh.hanteo.dto.GetCategoriesDTO;
import com.rlaclgh.hanteo.dto.GetRootCategoriesDTO;
import com.rlaclgh.hanteo.entity.QCategory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom{

  @Autowired
  private JPAQueryFactory queryFactory;

  @Override
  public List<Long> getRootCategoryIds(GetCategoriesDTO getCategoriesDTO) {

    QCategory category = QCategory.category;

    BooleanBuilder builder = new BooleanBuilder();

    String search = getCategoriesDTO.getSearch();


    if (search != null) {
      builder.and(category.name.contains(search));
    }



    return queryFactory
        .select(category.rootId).distinct()
        .from(category)
        .where(builder)
        .fetch();
  }

  @Override
  public List<Long> getFilteredCategoryIds(GetCategoriesDTO getCategoriesDTO) {

    QCategory category = QCategory.category;

    BooleanBuilder builder = new BooleanBuilder();

    String search = getCategoriesDTO.getSearch();



    if (search != null) {
      builder.and(category.name.contains(search));
    } else {
      builder.and(category.parent.id.isNull());
    }

    return queryFactory
        .select(category.id)
        .from(category)
        .where(builder)
        .fetch();
  }


}
