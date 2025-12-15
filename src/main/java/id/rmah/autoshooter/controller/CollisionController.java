package id.rmah.autoshooter.controller;

import id.rmah.autoshooter.Sound;
import id.rmah.autoshooter.model.BulletModel;
import id.rmah.autoshooter.model.EntityModel;
import java.util.List;

public class CollisionController {

    public void update(List<BulletModel> bullets, List<EntityModel> entities) {
        var bi = bullets.iterator();
        while (bi.hasNext()) {
            var bullet = bi.next();

            var ei = entities.iterator();
            while (ei.hasNext()) {
                var enemy = ei.next();

                if (!enemy.equals(bullet.getTarget())) {
                    continue;
                }

                if (isHit(bullet, enemy)) {
                    bi.remove();

                    enemy.setHitPoint(enemy.getHitPoint() - 1);
                    if (enemy.getHitPoint() <= 0) {
                        ei.remove();
                    }

                    Sound.HIT.play();

                    break;
                }
            }

        }
    }

    public static boolean isHit(BulletModel b, EntityModel e) {
        double dx = b.getX() - e.getX();
        double dy = b.getY() - e.getY();

//        double radiusSum = b.getRadius() + e.getRadius() + 2;
//        return dx * dx + dy * dy <= radiusSum * radiusSum;
        return dx * dx + dy * dy <= 100;
    }

}
