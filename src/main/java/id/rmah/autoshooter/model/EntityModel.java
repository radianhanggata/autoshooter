package id.rmah.autoshooter.model;

import java.util.Objects;
import java.util.UUID;

public class EntityModel {
    
    private static final int MIN_SPEED = 50;
    private static final int MAX_SPEED = 150;
    
    private final String id;
    
    private double x;
    private double y;
    private double angle;
    private double speed;
    
    private int hitPoint;
    
    private boolean isAimed;
    private boolean isOutOfRange;
    private boolean isPunished;
    
    public EntityModel(double x, double y, double angle) {
        this.id = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = MIN_SPEED + Math.random() * MAX_SPEED;
        this.hitPoint = (int) (1 + Math.random() * 8);
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

    public String getId() {
        return id;
    }
    
    public void setAimed(boolean value) {
        this.isAimed = value;
    }
    
    public boolean isAimed() {
        return this.isAimed;
    }

    public void setOutOfRange(boolean value) {
        this.isOutOfRange = value;
    }

    public boolean isOutOfRange() {
        return isOutOfRange;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public boolean isPunished() {
        return isPunished;
    }

    public void setPunished(boolean value) {
        this.isPunished = value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntityModel other = (EntityModel) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "EntityModel{" + "id=" + id + '}';
    }
    
    
    
    
    
}
