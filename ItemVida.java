package com.example.juegoplataformafxhollow;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ItemVida extends Entidad {
    private Image sprite;
    public ItemVida(double x, double y, double w, double h) {
        super(x, y, w, h);

        try {
            sprite = new Image("file:assets/images/vida.png");
        } catch (Exception e) {
            sprite = null;
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (sprite != null) {
            gc.drawImage(sprite, x, y, width, height);
        } else {
            gc.setFill(Color.GOLD);
            gc.fillOval(x, y, width, height);

            gc.setFill(Color.WHITE);
            gc.fillText("vida", x + width / 4 - 8, y + height / 1.2);
        }
    }
}
