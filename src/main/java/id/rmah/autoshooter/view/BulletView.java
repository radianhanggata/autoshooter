package id.rmah.autoshooter.view;

import id.rmah.autoshooter.model.BulletModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BulletView extends Circle {
    
    public BulletView(double x, double y) {
        super(x, y, 3, Color.BLACK);
    }

    public void render(BulletModel model) {
        setCenterX(model.getX());
        setCenterY(model.getY());
    }
    
}
