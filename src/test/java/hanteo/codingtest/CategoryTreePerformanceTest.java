package hanteo.codingtest.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTreePerformanceTest {

  @Test
  public void testLargeCategoryTreePerformance() throws JsonProcessingException {
    // 테스트 설정값
    int topLevelCategoryCount = 100;
    int subCategoryPerTop = 100;
    int boardIdCounter = 1;

    CategoryTree categoryTree = new CategoryTree();

    long startTime = System.currentTimeMillis();

    // 대량 카테고리 및 게시판 추가
    for (int i = 1; i <= topLevelCategoryCount; i++) {
      int topCategoryId = i;
      categoryTree.addCategory(topCategoryId, "카테고리-" + topCategoryId, 0);

      for (int j = 1; j <= subCategoryPerTop; j++) {
        int subCategoryId = topLevelCategoryCount + ((i - 1) * subCategoryPerTop) + j;
        categoryTree.addCategory(subCategoryId, "서브-" + i + "-" + j, topCategoryId);
        categoryTree.addBoardToCategory(subCategoryId, boardIdCounter++);
      }
    }

    long dataSetupTime = System.currentTimeMillis();

    // JSON 변환
    String json = categoryTree.toJson();

    long endTime = System.currentTimeMillis();

    // 출력
    System.out.println("데이터 생성 시간(ms): " + (dataSetupTime - startTime));
    System.out.println("전체 처리 시간(ms): " + (endTime - startTime));
    System.out.println("생성된 JSON 길이: " + json.length());

    // 검증
    assertNotNull(json);
    assertTrue(json.length() > 0, "JSON 데이터가 비어있지 않아야 함");
  }
}
