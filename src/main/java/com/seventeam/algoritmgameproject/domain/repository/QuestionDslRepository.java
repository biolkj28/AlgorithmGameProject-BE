package com.seventeam.algoritmgameproject.domain.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeam.algoritmgameproject.domain.model.QQuestion;
import com.seventeam.algoritmgameproject.domain.model.QTestCase;
import com.seventeam.algoritmgameproject.domain.model.TestCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.seventeam.algoritmgameproject.domain.model.QQuestion.question1;


@Repository
@RequiredArgsConstructor
public class QuestionDslRepository {
    private final JPAQueryFactory query;



}
