package com.seventeam.algoritmgameproject.domain.repository;

import com.seventeam.algoritmgameproject.domain.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long>{
}
