package id.rmah.autoshooter.model;

import id.rmah.autoshooter.SysProp;
import java.util.UUID;

public class BulletModel {
    
    private final String id;
    private double x;
    private double y;
    private double angle;
    private double speed;
    private final EntityModel target;
    
    public BulletModel(double x, double y, double angle, EntityModel target) {
        this.id = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.target = target;
        this.speed = SysProp.BULLET_SPEED;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public EntityModel getTarget() {
        return target;
    }
    
}
