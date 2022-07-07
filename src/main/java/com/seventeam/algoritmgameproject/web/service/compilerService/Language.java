package com.seventeam.algoritmgameproject.web.service.compilerService;

public enum Language {
    //JAVA("java"),Nod
    JAVA("java"),NODEJS("nodejs"),PYTHON3("python3");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
