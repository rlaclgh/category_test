package com.rlaclgh.hanteo.dto;


import com.rlaclgh.hanteo.entity.Category;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {
  private Long id;
  private Long parentId;
  private String name;

  public CategoryDTO(Long id, Long parentId, String name) {
    this.id = id;
    this.parentId = parentId;
    this.name = name;
  }

  public CategoryDTO(Category category) {
    this.id = category.getId();

    if (category.getParent() != null) {
      this.parentId = category.getParent().getId();
    } else {
      this.parentId = null;
    }


    this.name = category.getName();
  }

  private List<CategoryDTO> children = new ArrayList<>();

  public void addChild(CategoryDTO child) {
    children.add(child);
  }


}
