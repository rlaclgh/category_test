package com.rlaclgh.hanteo.dto;


import lombok.Data;

@Data
public class GetRootCategoriesDTO {

  private Long categoryId;

  private String search;

  public GetRootCategoriesDTO(Long categoryId, String search) {
    this.categoryId = categoryId;
    this.search = search;
  }
}
