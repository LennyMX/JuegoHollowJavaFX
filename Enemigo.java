package com.example.juegoplataformafxhollow;

public abstract class Enemigo extends Entidad {
    protected double velX = 0;
    public Enemigo(double x, double y, double width, double height, double velX) {
        super(x,y,width,height);
        this.velX = velX;
    }
}
