package com.example.mingkhparser.models.facade;

import com.example.mingkhparser.models.IEnum;
import lombok.Getter;

@Getter
public enum FacadeFinishingMaterial implements IEnum {
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

    private final String name;

    FacadeFinishingMaterial(String name) {
        this.name = name;
    }

}
