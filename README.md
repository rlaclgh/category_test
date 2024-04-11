## 1. 카테고리

### DB 구조
category table
|컬럼명|타입|설명|
|------|---|---|
|id|BigInt|고유 ID|
|name|VarChar|카테고리명|
|parent_id|BigInt|상위 카테고리 ID|
|root_id|BigInt|최상위 카테고리 ID(이름 필터링시 최상위 카테고리 정보를 가져오기 위해)|
|board_id|BigInt|Board ID|


board table

|컬럼명|타입|설명|
|------|---|---|
|id|BigInt|고유 ID|


### MOCK 데이터
![mockdata](https://github.com/rlaclgh/category_test/assets/46914232/26866e4c-c269-434d-b2f1-40dd9d18f3a1)




### API 설명

#### /category
카테고리 리스트를 불러오는 API 
응답값의 형태에 따라 3가지 version으로 나눴습니다. 


v1. 필터링된 카테고리의 최상위 카테고리의 모든 하위 카테고리를 제공해야한다.   
ex) 엑소 검색시   
|카테고리|카테고리|카테고리|
|------|---|---|
|남자|엑소|공지사항|
|||첸|
|||백현|
|||시우민|
||방탄소년단|공지사항|
|||익명게시판|
|||뷔|

v2. 필터링된 카테고리의 상위는 부모관계의 카테고리만 제공해야한다.    
ex) 엑소 검색시   
|카테고리|카테고리|카테고리|
|------|---|---|
|남자|엑소|공지사항|
|||첸|
|||백현|
|||시우민|

v3. 필터링된 카테고리의 하위 카테고리만 제공해야한다.    
ex) 엑소 검색시

|카테고리|카테고리|
|---|---|
|엑소|공지사항|
||첸|
||백현|
||시우민|



v1 응답 결과
```
[
  {
    "id": 1,
    "parentId": null,
    "name": "남자",
    "children": [
      {
        "id": 2,
        "parentId": 1,
        "name": "엑소",
        "children": [
          {
            "id": 4,
            "parentId": 2,
            "name": "공지사항",
            "children": [
              
            ]
          },
          {
            "id": 5,
            "parentId": 2,
            "name": "챈",
            "children": [
              
            ]
          },
          {
            "id": 6,
            "parentId": 2,
            "name": "백현",
            "children": [
              
            ]
          },
          {
            "id": 7,
            "parentId": 2,
            "name": "시우민",
            "children": [
              
            ]
          }
        ]
      },
      {
        "id": 3,
        "parentId": 1,
        "name": "방탄소년단",
        "children": [
          {
            "id": 8,
            "parentId": 3,
            "name": "공지사항",
            "children": [
              
            ]
          },
          {
            "id": 9,
            "parentId": 3,
            "name": "익명게시판",
            "children": [
              
            ]
          },
          {
            "id": 10,
            "parentId": 3,
            "name": "뷔",
            "children": [
              
            ]
          }
        ]
      }
    ]
  }
]
```


v2 응답결과
```
[
  {
    "id": 1,
    "parentId": null,
    "name": "남자",
    "children": [
      {
        "id": 2,
        "parentId": 1,
        "name": "엑소",
        "children": [
          {
            "id": 4,
            "parentId": 2,
            "name": "공지사항",
            "children": [
              
            ]
          },
          {
            "id": 5,
            "parentId": 2,
            "name": "챈",
            "children": [
              
            ]
          },
          {
            "id": 6,
            "parentId": 2,
            "name": "백현",
            "children": [
              
            ]
          },
          {
            "id": 7,
            "parentId": 2,
            "name": "시우민",
            "children": [
              
            ]
          }
        ]
      }
    ]
  }
]
```

v3 응답결과
```
[
  {
    "id": 2,
    "parentId": 1,
    "name": "엑소",
    "children": [
      {
        "id": 4,
        "parentId": 2,
        "name": "공지사항",
        "children": [
          
        ]
      },
      {
        "id": 5,
        "parentId": 2,
        "name": "챈",
        "children": [
          
        ]
      },
      {
        "id": 6,
        "parentId": 2,
        "name": "백현",
        "children": [
          
        ]
      },
      {
        "id": 7,
        "parentId": 2,
        "name": "시우민",
        "children": [
          
        ]
      }
    ]
  }
]
```





#### /category/{categoryId}






## 2. 코인 경우의 수 
