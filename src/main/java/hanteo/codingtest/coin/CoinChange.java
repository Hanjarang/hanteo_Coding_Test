package hanteo.codingtest.coin;

public class CoinChange {

  /**
   * 특정 금액(sum)을 만들 수 있는 방법의 개수를 계산하는 메서드
   * @param sum 먹표금액
   * @param coins 사용할 통화의 종류 배열
   * @return 주어진 통화로 sum을 만들 수 있는 방법의 개수
   */
  public static int countWays(int sum, int[] coins){
    // 동적 계획법 (DP) 을 사용하여 해당 문제를 풀었습니다.
    // 이유 : 해당 문제는 최적 부분 구조와 중복 부분 문제 속성을 가지므로 DP를 이용하면 효율적으로 해결할 수 있겠다고 판단하였습니다.

    // dp[i]는 i원을 만들 수 있는 경우의 수를 저장하기때문에 배열 크기는 sum + 1으로 설정
    int[] dp = new int[sum + 1];

    // 초기화
    // 0원을 만드는, 아무것도 사용하지않는 방법 1가지
    dp[0] = 1;

    // 각 통화에 대해 가능한 모든 금액을 계산한다.
    // 모든 통화를 순회하도록 한다. 통화의 개수가 많을 수록 여러 조합이 나올 수 있기때문에
    for(int coin : coins) {

      // 중첩루프 사용
      // dp[j]는 "j원을 만들 수 있는 방법의 개수"를 저장한다.
      // coin을 추가하여 j원을 만들 수 있는 방법을 계산
      for(int j = coin; j <= sum; j++) {

        // 점화식
        // dp[j - coin] : 현재 coin을 추가하기 전 j-coin원을 만드는 방법의 수
        // dp[j] += dp[j - coin] : 기존 방법에 새로운 통화를 추가하여 j원을 만드는 경우의 수 추가
        dp[j] += dp[j - coin];
      }
    }

    // 최종 출력
    // sum 원을 만들 수 있는 모든 경우의 수를 반환
    return dp[sum];
  }

  public static void main(String[] args) {

    // 결과 예시

    // 합계
    int sum = 10;

    // coins
    int[] coins = {2, 3, 5, 6};

    // 함수 호출
    // countWays 호출하여 sum(합계)를 만들 수 있는 경우의 수 계산
    int result = countWays(sum, coins);

    // 결과 출력
    System.out.println("출력 = " + result);
  }

}
