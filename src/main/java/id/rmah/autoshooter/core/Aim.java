package id.rmah.autoshooter.core;

import id.rmah.autoshooter.SysProp;
import javafx.geometry.Point2D;

public class Aim {

    public static Point2D predict(double gunX, double gunY, double targetX, double targetY, double targetAngle, double targetSpeed, double gunAimTime) {
        var tx = targetX;
        var ty = targetY;
        var vx = Math.cos(targetAngle) * targetSpeed;
        var vy = Math.sin(targetAngle) * targetSpeed;

        var posX = tx;
        var posY = ty;

        for (int i = 0; i < 5; i++) {
            var dx = posX - gunX;
            var dy = posY - gunY;
            var distance = Math.sqrt(dx * dx + dy * dy);
            var flightTime = distance / SysProp.BULLET_SPEED;
            var totalTime = gunAimTime + flightTime;

            posX = tx + vx * totalTime;
            posY = ty + vy * totalTime;
        }

        return new Point2D(posX, posY);
    }

}
