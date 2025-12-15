package id.rmah.autoshooter.controller;

import id.rmah.autoshooter.App;
import id.rmah.autoshooter.model.BulletModel;
import id.rmah.autoshooter.model.EntityModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BulletController {
    
    private final List<BulletModel> bullets = new ArrayList<>();
    
    public void update(double deltaTime) {
        Iterator<BulletModel> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            var bullet = iterator.next();

            var vx = Math.cos(bullet.getAngle()) * bullet.getSpeed();
            var vy = Math.sin(bullet.getAngle()) * bullet.getSpeed();

            bullet.setX(bullet.getX() + vx * deltaTime);
            bullet.setY(bullet.getY() + vy * deltaTime);

            if (isOutOfBound(bullet, App.WIDTH, App.HEIGHT)) {
                iterator.remove();
            }
        }
    }
    
    public void create(double x, double y, double angle, EntityModel target) {
        var bullet = new BulletModel(x, y, angle, target);
        bullets.add(bullet);
    }
    
    private boolean isOutOfBound(BulletModel bullet, double width, double height) {
        return bullet.getX() < 0 || bullet.getX() > width
                || bullet.getY() < 0 || bullet.getY() > height;
    }
    
    public List<BulletModel> getBullets() {
        return this.bullets;
    }
    
}
