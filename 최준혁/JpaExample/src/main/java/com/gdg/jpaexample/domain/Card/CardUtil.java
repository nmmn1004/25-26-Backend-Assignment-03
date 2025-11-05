package com.gdg.jpaexample.domain.Card;

import java.util.List;

public class CardUtil {
//  카드를 할당할 때 사용합니다.
    public int generateRandomCards() {
        return (int)(Math.random() * 10) + 1;
    }
//  할당된 카드의 합을 계산할 때 에이스를 1 혹은 11으로 계산하기 위해 사용합니다.
    public int calculateHandCard(List<Integer> cards) {
        int sum = 0;
        int aceCount = 0;
        for (int card : cards) {
            if (card == 1) { // 에이스
                aceCount++;
                sum += 1;
            } else if (card >= 10) {
                sum += 10;
            } else {
                sum += card;
            }
        }

        while (aceCount > 0 && sum + 10 <= 21) {
            sum += 10;
            aceCount--;
        }
        return sum;
    }

}
