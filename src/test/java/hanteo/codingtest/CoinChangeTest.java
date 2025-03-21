package hanteo.codingtest;

import hanteo.codingtest.coin.CoinChange;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoinChangeTest {

  @Test
  void testExampleCase() {
    int sum = 10;
    int[] coins = {2, 5, 3, 6};
    int expected = 5; // 정답: 5가지 방법
    assertEquals(expected, CoinChange.countWays(sum, coins));
  }

  @Test
  void testZeroSum() {
    int sum = 0;
    int[] coins = {1, 2, 3};
    int expected = 1; // 0원을 만드는 방법은 아무것도 선택하지 않는 1가지
    assertEquals(expected, CoinChange.countWays(sum, coins));
  }

  @Test
  void testNoSolution() {
    int sum = 7;
    int[] coins = {2, 4};
    int expected = 0; // 2, 4만으로는 7을 만들 수 없음
    assertEquals(expected, CoinChange.countWays(sum, coins));
  }

  @Test
  void testSingleCoinType() {
    int sum = 8;
    int[] coins = {2};
    int expected = 1; // 2+2+2+2 한 가지 방법만 존재
    assertEquals(expected, CoinChange.countWays(sum, coins));
  }

  @Test
  void testMultipleCoinTypes() {
    int sum = 5;
    int[] coins = {1, 2};
    int expected = 3; // (1x5), (1x3 + 2x1), (1x1 + 2x2)
    assertEquals(expected, CoinChange.countWays(sum, coins));
  }

  @Test
  void testLargeSum() {
    int sum = 100;
    int[] coins = {1, 5, 10, 25, 50};
    int result = CoinChange.countWays(sum, coins);
    assertTrue(result > 0); // 정확한 값은 생략, 단지 0보다 큰지만 확인
  }
}
