package hanteo.codingtest.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 카테고리 정보를 저장하는 클래스
 * 각 카테고리는 ID 를 가지고, 하위 카테고리 목록과 게시판 ID 목록을 포함
 * 트리 구조를 형성할 수 있도록 하위 카테고리를 추가하는 기능 제공
 */
public class Category {
  private int id; // 카테고리 식별자
  private String name; //카테고리명
  private List<Category> subCategories; // 하위 카테고리 목록
  private List<Integer> boardIds; // 해당 카테고리가 포함하는 게시판 ID 리스트

  // 생성자
  // 새로운 카테고리를 만들 때 ID와 이름을 설정
  public Category(int id, String name) {
    this.id = id;
    this.name = name;
    this.subCategories = new ArrayList<>();
    this.boardIds = new ArrayList<>();
  }

  // 하위 카테고리 추가
  // parent_idx -> child_id 관계 설정
  public void addSubCategory(Category category) {
    this.subCategories.add(category);
  }

  // 특정 카테고리에 게시판 추가
  // 공지사항, 익명게시판을 구분
  public void addBoardId(int boardId) {
    this.boardIds.add(boardId);
  }

  // getter 메서드, 데이터 캡슐화 유지
  public int getId(){
    return id;
  }

  public String getName() {
    return name;
  }
  public List<Category> getSubCategories() {
    return subCategories;
  }
  public List<Integer> getBoardIds() {
    return boardIds;
  }

}

/**
 * 카테고리 트리를 관리하는 클래스
 * 카테고리 계층 구조 유지에 초점
 */
class CategoryTree{
  private Map<Integer, Category> categoryMap; // 카테고리 ID를 기준으로 빠르게 검색하기위한 Map
  private Category root; // 최상위 카테고리 ( 전체 카테고리 관리 )

  public CategoryTree(){
    categoryMap = new HashMap<>();
    root = new Category(0, "Root"); // 모든 카테고리의 부모 (최상위 루트 노드)
    categoryMap.put(0, root);
  }

  /**
   * 카테고리 추가
   * @param parenId 를 이용하여 계층 구조 유지
   * HashMap을 이용하여 O(1) 시간 복잡도로 검색 가능하도록 구성함.
   */
  public void addCategory(int id, String name, int parenId){
    Category category = new Category(id, name);
    categoryMap.put(id, category);

    // 부모 카테고리를 찾아 하위 카테고리로 추가
    // 트리 구조 유지
    Category parentCategory = categoryMap.get(parenId);
    if(parentCategory != null){
      parentCategory.addSubCategory(category);
    }
  }

  /**
   * 게시판 추가
   * 공지사항은 이름이 같지만 각 다른 게시판 ID를 가지고있고
   * 익명게시판은 동일한 게시판 ID(6)를 공유하지만 각각 다른 카테고리에 소속되어 있음.
   */
  public void addBoardToCategory(int categoryId, int boardId){
    Category category = categoryMap.get(categoryId);
    if(category != null){
      category.addBoardId(boardId);
    }
  }

  /**
   * 카테고리 검색
   * @param categoryId 카테고리 ID를 이용해 특정 카테고리와 하위 카테고리를 검색
   */
  public Category getCategory(int categoryId){
    return categoryMap.get(categoryId);
  }

  /**
   * JSON 변환
   */
  public String toJson()throws JsonProcessingException{
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root.getSubCategories());
  }

}

class Main {
  public static void main(String[] args) throws JsonProcessingException{
    CategoryTree categoryTree = new CategoryTree();

    // 상위 카테고리 (남자, 여자)
    categoryTree.addCategory(1, "남자", 0);
    categoryTree.addCategory(2, "엑소", 1);
    categoryTree.addCategory(3, "방탄소년단", 1);
    categoryTree.addCategory(4, "여자", 0);
    categoryTree.addCategory(5, "블랙핑크", 4);

    // 서브 카테고리 추가 (parent_idx -> child_id 관계 설정)
    categoryTree.addCategory(6, "공지사항", 2); // 엑소의 공지사항
    categoryTree.addCategory(7, "첸", 2);
    categoryTree.addCategory(8, "백현", 2);
    categoryTree.addCategory(9, "시우민", 2);

    categoryTree.addCategory(10, "공지사항", 3); // 방탄소년단의 공지사항
    categoryTree.addCategory(11, "익명게시판", 3); // 익명 게시판, 6번과 공유됨
    categoryTree.addCategory(12, "뷔", 3);

    categoryTree.addCategory(13, "공지사항", 5); // 블랙핑크의 공지사항
    categoryTree.addCategory(14, "익명게시판", 5); // 익명 게시판, 6번과 공유됨
    categoryTree.addCategory(15, "로제", 5);

    // 공지사항은 이름은 같지만 각각 다른 게시판 ID 부여
    categoryTree.addBoardToCategory(6,1); // 엑소-공지사항
    categoryTree.addBoardToCategory(10,5); // 방탄소년단-공지사항
    categoryTree.addBoardToCategory(13,8); // 블랙핑크-공지사항

    // 익명게시판, 같은 게시판 ID (6) 을 공유
    categoryTree.addBoardToCategory(11,6); // 방탄소년단-익명게시판
    categoryTree.addBoardToCategory(14,6); // 블랙핑크-익명게시판

    // 일반 카테고리 - 게시판 연결
    categoryTree.addBoardToCategory(7,2); // 엑소-첸
    categoryTree.addBoardToCategory(8,3); // 엑소-백현
    categoryTree.addBoardToCategory(9,4); // 엑소-시우민
    categoryTree.addBoardToCategory(12,7); // 방탄소년단-뷔
    categoryTree.addBoardToCategory(15,9); // 블랙핑크-로제

    // 자료구조가 JSON으로 변환 출력
    System.out.println(categoryTree.toJson());
  }

}
