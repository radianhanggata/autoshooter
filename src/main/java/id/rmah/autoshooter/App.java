package id.rmah.autoshooter;

import id.rmah.autoshooter.controller.BulletController;
import id.rmah.autoshooter.controller.CollisionController;
import id.rmah.autoshooter.controller.EntityController;
import id.rmah.autoshooter.controller.GunController;
import id.rmah.autoshooter.error.GunException;
import id.rmah.autoshooter.model.EntityModel;
import id.rmah.autoshooter.model.GunModel;
import id.rmah.autoshooter.model.GunSpec;
import id.rmah.autoshooter.view.BulletView;
import id.rmah.autoshooter.view.GunView;
import id.rmah.autoshooter.view.EntityView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class App extends Application {

    public static final int WIDTH = 900;
    public static final int HEIGHT = 900;

    public static Group mainLayer;

    private static Scene scene;
    private static final int CENTER_X = WIDTH / 2;
    private static final int CENTER_Y = HEIGHT / 2;

    private GunController gunController;
    private EntityController entityController;
    private BulletController bulletController;
    private CollisionController collisionController;

    private GunModel gunModel;

    private GunView gunView;
    private Map<String, EntityView> entityViews;
    private Map<String, BulletView> bulletViews;

    private long lastTime = 0;

    @Override
    public void start(Stage stage) throws IOException {
        var barrelLength = 16;
        var rateOfFire = 8; // number of bullets per second
        var rotationSpeed = Math.toRadians(SysProp.GUN_ROTATION_SPEED_DEGREE);
        var gun = new GunSpec(barrelLength, rateOfFire, rotationSpeed);
        gunModel = new GunModel(CENTER_X, CENTER_Y, gun);
        gunController = new GunController(gunModel);
        gunView = new GunView();

        entityController = new EntityController();
        entityViews = new HashMap();

        bulletController = new BulletController();
        bulletViews = new HashMap();

        collisionController = new CollisionController();

        var staticLayer = new Group(createShootRadiusVisual());
        mainLayer = new Group();

        Group root = new Group(staticLayer, mainLayer);

        mainLayer.getChildren().add(gunView);

        mainLayer.toFront();
        scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
            }
        }.start();

        Platform.runLater(() -> Sound.warmUp());
    }

    private void update(long now) {
        if (lastTime == 0) {
            lastTime = now;
            return;
        }

        double deltaTime = (now - lastTime) / 1_000_000_000.0;

        gunController.update(deltaTime);
        gunView.render(gunModel);

        entityController.update(deltaTime);
        renderEntities();

        bulletController.update(deltaTime);
        renderBullets();

        collisionController.update(bulletController.getBullets(), entityController.getEntities());

        var target = getTarget();
        gunController.setTarget(target);
        if (gunController.isReadyToFire()) {
            fire(target);
        }

        lastTime = now;
    }

    private void renderEntities() {
        var emIterator = entityController.getEntities().iterator();
        while (emIterator.hasNext()) {
            var entityModel = emIterator.next();
            var entityView = entityViews.get(entityModel.getId());
            if (entityView == null) {
                entityView = new EntityView(entityModel.getX(), entityModel.getY());
                entityView.render(entityModel);
                entityViews.put(entityModel.getId(), entityView);
                mainLayer.getChildren().add(entityView);
            }

            entityView.render(entityModel);
        }

        entityViews.entrySet().removeIf(entry -> {
            boolean alive = entityController.getEntities().stream()
                    .anyMatch(e -> e.getId().equals(entry.getKey()));

            if (!alive) {
                mainLayer.getChildren().remove(entry.getValue());
                return true;
            }
            return false;
        });
    }

    private void renderBullets() {
        var bullets = bulletController.getBullets().iterator();
        while (bullets.hasNext()) {
            var bulletModel = bullets.next();
            var bulletView = bulletViews.get(bulletModel.getId());
            if (bulletView == null) {
                bulletView = new BulletView(bulletModel.getX(), bulletModel.getY());
                bulletView.render(bulletModel);
                bulletViews.put(bulletModel.getId(), bulletView);
                mainLayer.getChildren().add(bulletView);
            }

            bulletView.render(bulletModel);
        }

        bulletViews.entrySet().removeIf(entry -> {
            boolean alive = bulletController.getBullets().stream()
                    .anyMatch(e -> e.getId().equals(entry.getKey()));

            if (!alive) {
                mainLayer.getChildren().remove(entry.getValue());
                return true;
            }
            return false;
        });
    }

    private EntityModel getTarget() {
        if (gunController.getTarget() == null) {
            for (EntityModel e : entityController.getEntities()) {
                var gunPosition2D = new Point2D(gunModel.getX(), gunModel.getY());
                var distance = new Point2D(e.getX(), e.getY()).distance(gunPosition2D);

                if (distance <= SysProp.SHOOT_RADIUS && !e.isPunished()) {
                    return e;
                }
            }
        }

        return gunController.getTarget();
    }

    private void fire(EntityModel target) {
        try {
            gunController.fire();
            bulletController.create(gunModel.getX(), gunModel.getY(), gunModel.getAngle(), target);
        } catch (GunException ex) {
        }
    }

    private Circle createShootRadiusVisual() {
        Circle c = new Circle(CENTER_X, CENTER_Y, SysProp.SHOOT_RADIUS);
        c.setFill(Color.TRANSPARENT);
        c.setStroke(Color.LIGHTSEAGREEN);
        c.setStrokeWidth(2);
        c.setMouseTransparent(true);

        return c;
    }

    public static void main(String[] args) {
        launch();
    }

}
