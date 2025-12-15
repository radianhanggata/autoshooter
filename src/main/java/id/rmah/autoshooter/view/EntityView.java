package id.rmah.autoshooter.view;

import id.rmah.autoshooter.model.EntityModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class EntityView extends Circle {

    public EntityView(double x, double y) {
        super(x, y, 3, Color.BLACK);
    }

    public void render(EntityModel model) {
        setCenterX(model.getX());
        setCenterY(model.getY());
        
        if (model.isAimed()) {
            setStroke(Color.BLACK);
            setFill(Color.RED);
            setRadius(6);
        }
        
        if (model.isOutOfRange()) {
            setFill(Color.YELLOW);
            setStroke(Color.WHITE);
            setRadius(3);
        }
    }

}
