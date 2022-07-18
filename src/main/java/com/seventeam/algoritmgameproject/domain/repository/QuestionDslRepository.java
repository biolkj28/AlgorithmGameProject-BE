package com.seventeam.algoritmgameproject.domain.repository;


import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import com.seventeam.algoritmgameproject.domain.model.QQuestion;
import com.seventeam.algoritmgameproject.domain.model.QTestCase;
import com.seventeam.algoritmgameproject.domain.model.Question;
import com.seventeam.algoritmgameproject.domain.model.TestCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.seventeam.algoritmgameproject.domain.model.QQuestion.question1;


@Repository
@RequiredArgsConstructor

public class QuestionDslRepository {
    private final JPAQueryFactory query;
    private final JPAQuery<Question>jpaCustomQuery;
    @Transactional
    public List<Question> findAllQuestions() {
        List<Question> questions = query.selectFrom(question1)
                .leftJoin(question1.templates)
                .fetchJoin()
                .distinct().fetch();
        query.selectFrom(question1).leftJoin(question1.cases).fetchJoin().fetch();
        return questions;
    }
    @Transactional
    public List<Question>findAllByQuestionLevel(QuestionLevel level){
        List<Question> questions = query
                .selectFrom(question1)
                .where(question1.level.eq(level))
                .leftJoin(question1.templates)
                .fetchJoin()
                .distinct().fetch();

        query.selectFrom(question1)
                .where(question1.level.eq(level))
                .leftJoin(question1.cases)
                .fetchJoin()
                .fetch();
        return questions;
    }
    @Transactional
    //추후 redis 저장 데이터로 셔플
    public Question findRandomQuestionLevel(QuestionLevel level){
        Question questions = jpaCustomQuery
                .from(question1)
                .where(question1.level.eq(level))
                .orderBy(NumberExpression.random().asc())
                .limit(1)
                .leftJoin(question1.templates)
                .fetchJoin()
                .fetchOne();

        jpaCustomQuery.from(question1)
                .leftJoin(question1.cases)
                .fetchJoin()
                .fetchOne();
        return questions;
    }



}
