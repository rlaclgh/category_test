package com.rlaclgh.hanteo.dto;


import lombok.Data;

@Data
public class GetCategoriesDTO {

  private String search;

  public GetCategoriesDTO(String search) {
    this.search = search;
  }
}
