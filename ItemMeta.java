package com.example.juegoplataformafxhollow;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.geometry.Rectangle2D;

public class ItemMeta extends Entidad {
    private Image sprite;

    public ItemMeta(double x, double y, double w, double h) {
        super(x, y, w, h);
        try {
            sprite = new Image("file:assets/images/final.png");
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
            gc.fillText("META", x + width / 4 - 8, y + height / 1.2);
        }
    }
}
