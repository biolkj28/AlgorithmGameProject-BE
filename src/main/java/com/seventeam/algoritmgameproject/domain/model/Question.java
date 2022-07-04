package com.seventeam.algoritmgameproject.domain.model;

import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String question;

    @Column(nullable = false, length = 1000)
    private String limitation;

    //입출력 예 header
    @Column(nullable = false)
    private String inOutExHead;
    //입출력 예 Body
    @Column(nullable = false)
    private String inOutEx;

    //입출력 예 설명(null 허용)
    @Column(length = 1000)
    private String inOutExDescription;
    //참고 사항(null 허용)
    @Column(length = 1000)
    private String reference;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionLevel level;
    // 문제
    @ElementCollection
    @CollectionTable(
            name = "code_template",
            joinColumns = @JoinColumn(name = "question_id")
    )
    @MapKeyColumn
    @Column(name = "template",length = 1000)
    private Map<String, String> templates = new HashMap<>();

    @OneToMany(mappedBy = "question")
    private List<TestCase> cases = new ArrayList<>();

    @Builder
    public Question(String title, String question, String limitation, String inOutExHead, String inOutEx, String inOutExDescription, String reference, QuestionLevel level, Map<String, String> templates) {
        this.title = title;
        this.question = question;
        this.limitation = limitation;
        this.inOutExHead = inOutExHead;
        this.inOutEx = inOutEx;
        this.inOutExDescription = inOutExDescription;
        this.reference = reference;
        this.level = level;
        this.templates = templates;
    }

    public void add(TestCase testCase){
        TestCase.builder().question(this).build();
        this.add(testCase);
    }
}
