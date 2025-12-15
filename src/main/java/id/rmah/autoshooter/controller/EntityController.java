package id.rmah.autoshooter.controller;

import id.rmah.autoshooter.App;
import id.rmah.autoshooter.SysProp;
import id.rmah.autoshooter.model.EntityModel;
import java.util.ArrayList;
import java.util.List;

public class EntityController {

    private static final int SPAWNED_RIGHT = 0;
    private static final int SPAWNED_BOTTOM = 1;
    private static final int SPAWNED_LEFT = 2;
    private static final int SPAWNED_TOP = 3;

    private final List<EntityModel> entities = new ArrayList<>();

    private double accumulator = 0;

    public void update(double deltaTime) {
        accumulator += deltaTime;

        if (accumulator >= 0.2) {
            if (entities.size() < SysProp.MAX_ENEMIES_ON_SCREEN) {
                create();
            }
            accumulator = 0;
        }

        handleSpawn(deltaTime);
    }

    private void handleSpawn(double deltaTime) {
        var iterator = entities.iterator();
        while (iterator.hasNext()) {
            var target = iterator.next();

            var vx = Math.cos(target.getAngle()) * target.getSpeed();
            var vy = Math.sin(target.getAngle()) * target.getSpeed();

            target.setX(target.getX() + vx * deltaTime);
            target.setY(target.getY() + vy * deltaTime);

            if (isOutOfBound(target, App.WIDTH, App.HEIGHT)) {
                iterator.remove();
            }
        }
    }

    public void create() {
        double x, y, angle;
        // 0 rad → ke kanan
        // π/2 → ke bawah
        // π → ke kiri
        // 3π/2 → ke atas
        int side = (int) (Math.random() * 4);
        switch (side) {
            case SPAWNED_RIGHT -> {
                x = App.WIDTH - 1;
                y = Math.random() * App.HEIGHT;
                angle = Math.PI / 2 + Math.random() * Math.PI;
            }
            case SPAWNED_BOTTOM -> {
                x = Math.random() * App.WIDTH;
                y = App.HEIGHT - 1;
                angle = Math.PI + Math.random() * Math.PI;
            }
            case SPAWNED_LEFT -> {
                x = App.WIDTH + 1;
                y = Math.random() * App.HEIGHT;
                angle = -Math.PI / 2 + Math.random() * Math.PI;
            }
            case SPAWNED_TOP -> {
                x = Math.random() * App.HEIGHT;
                y = 1;
                angle = Math.random() * Math.PI;
            }
            default -> {
                throw new RuntimeException("Invalid");
            }
        }

        var entity = new EntityModel(x, y, angle);

        entities.add(entity);
    }

    public List<EntityModel> getEntities() {
        return entities;
    }

    private boolean isOutOfBound(EntityModel entity, double width, double height) {
        return entity.getX() < 0 || entity.getX() > width
                || entity.getY() < 0 || entity.getY() > height;
    }

}
