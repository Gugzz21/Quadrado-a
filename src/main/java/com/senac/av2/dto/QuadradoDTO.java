package com.senac.av2.dto;

import java.io.Serializable;

public class QuadradoDTO implements Serializable {
    private double lado;
    private double area;

    public QuadradoDTO() {}

    public QuadradoDTO(double lado) {
        this.lado = lado;
    }

    public double getLado() {
        return lado;
    }

    public void setLado(double lado) {
        this.lado = lado;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}