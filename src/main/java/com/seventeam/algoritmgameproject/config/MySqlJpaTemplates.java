package com.seventeam.algoritmgameproject.config;

import com.querydsl.core.types.Ops;
import com.querydsl.jpa.JPQLTemplates;
import org.springframework.stereotype.Component;

@Component
public class MySqlJpaTemplates extends JPQLTemplates {
    public static final MySqlJpaTemplates DEFAULT = new MySqlJpaTemplates();

    protected MySqlJpaTemplates() {
        this(DEFAULT_ESCAPE);
        add(Ops.MathOps.RANDOM, "rand()");
        add(Ops.MathOps.RANDOM2, "rand({0})");
    }

    public MySqlJpaTemplates(char escape) {
        super(escape);
    }
}
