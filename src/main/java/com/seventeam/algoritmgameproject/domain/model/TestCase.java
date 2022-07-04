package com.seventeam.algoritmgameproject.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestCase {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="QUESTION_ID")
    Question question;

    @Column(nullable = false, length = 1000)
    private String answer;

    @Column(nullable = false, length = 1000)
    private String params;public long solution(int price, int money, int count) {
        long answer = money;

        for(int i=1; i<=count; i++){
            answer -= (price*i);
        }

        return answer<0?(answer*-1):0;
    }

    @Builder
    public TestCase(Question question, String answer, String params) {
        this.question = question;
        this.answer = answer;
        this.params = params;
    }
}
