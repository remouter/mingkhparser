package com.example.mingkhparser.models.facade;

public enum FacadeFinishingMaterial {
    WITHOUTFINISHING("без отделки"),
    PAINTING("окраска"),
    OTHER("Иной"),
    PAINTINGONPLASTER("окраска по штукатурке"),
    PLASTER("Штукатурка"),
    PANELING("Обшивка тёсом"),
    PANELINGPAINTING("обшивочная доска окрашенная"),
    PANELINGUNPAINTING("обшивочная доска не окрашенная"),
    WOOD("Дерево"),
    FACTORYFINISHED("панель с заводской отделкой"),
    SIDING("Сайдинг"),
    BRICK("наружная облицовка кирпичом"),
    CERAMICTILE("облицовка керамической плиткой");

    private String name;

    FacadeFinishingMaterial(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return name;
    }
}
