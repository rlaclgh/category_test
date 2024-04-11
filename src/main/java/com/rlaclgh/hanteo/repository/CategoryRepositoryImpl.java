package com.rlaclgh.hanteo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rlaclgh.hanteo.dto.GetCategoriesDTO;
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


    // 검색어가 있다면 검색어로 필터링
    if (search != null) {
      builder.and(category.name.contains(search));
    } else {
      // 검색어가 없다면 최상위 카테고리 즉 parent_id 가 null 인 카테고리를 불러옵니다.
      builder.and(category.parent.id.isNull());
    }





    return queryFactory
        // 필터링된 카테고리의 unique 한 rootId 리스트를 불러옵니다.
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


    // 검색어가 있다면 검색어로 필터링
    if (search != null) {
      builder.and(category.name.contains(search));
    } else {
      // 검색어가 없다면 최상위 카테고리 즉 parent_id 가 null 인 카테고리를 불러옵니다.
      builder.and(category.parent.id.isNull());
    }


    return queryFactory
        // 필터링된 카테고리의 id 리스트를 불러옵니다.
        .select(category.id)
        .from(category)
        .where(builder)
        .fetch();
  }


}
