package id.rmah.autoshooter.view;

import id.rmah.autoshooter.model.GunModel;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class GunView extends Group {

    private final Line barrel;

    public GunView() {
        barrel = new Line(0, 0, 0, -16);
        barrel.setStrokeWidth(6);
        var body = new Circle(0, 0, 10, Color.DARKBLUE);
        getChildren().addAll(barrel, body);
    }

    public void render(GunModel model) {
        setLayoutX(model.getX());
        setLayoutY(model.getY());

        barrel.setStartX(0);
        barrel.setStartY(0);

        barrel.setEndX(model.getBarrelTipX());
        barrel.setEndY(model.getBarrelTipY());
    }

    public Line getBarrel() {
        return barrel;
    }

}
