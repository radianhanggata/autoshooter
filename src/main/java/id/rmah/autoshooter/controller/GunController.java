package id.rmah.autoshooter.controller;

import id.rmah.autoshooter.SysProp;
import id.rmah.autoshooter.Sound;
import id.rmah.autoshooter.core.Aim;
import id.rmah.autoshooter.error.GunException;
import id.rmah.autoshooter.model.GunModel;
import id.rmah.autoshooter.model.GunState;
import id.rmah.autoshooter.model.EntityModel;
import javafx.geometry.Point2D;

public class GunController {

    private static final double ANGLE_EPSILON = Math.toRadians(0.5); // 0.5 derajat

    private final GunModel gunModel;

    private EntityModel targetModel;
    private int fireQuota;

    private double deltaTime;

    public GunController(GunModel gunModel) {
        this.gunModel = gunModel;
    }

    public void update(double deltaTime) {
        this.deltaTime = deltaTime;

        updateBarrel();
        updateRotation();
        updateState();
    }

    private void updateBarrel() {
        if (targetModel == null && gunModel.getRecoilOffset() == 0) {
            return;
        }

        updateRecoil();
        updateBarrelTip();
    }

    private void updateRecoil() {
        if (gunModel.getRecoilOffset() == 0) {
            return;
        }

        var recoil = gunModel.getRecoilOffset() - gunModel.getSpec().getRecoilReturnSpeed() * deltaTime;
        gunModel.setRecoilOffset(Math.max(0, recoil));
    }

    private void updateBarrelTip() {
        var barrelLength = gunModel.getSpec().getBarrelLength() - gunModel.getRecoilOffset();

        var barrelTipX = Math.cos(gunModel.getAngle()) * barrelLength;
        var barrelTipY = Math.sin(gunModel.getAngle()) * barrelLength;

        gunModel.setBarrelTipX(barrelTipX);
        gunModel.setBarrelTipY(barrelTipY);
    }

    private void updateRotation() {
        if (targetModel == null) {
            return;
        }

        var predictedTargetPos2D = Aim.predict(
                gunModel.getX(),
                gunModel.getY(),
                targetModel.getX(),
                targetModel.getY(),
                targetModel.getAngle(),
                targetModel.getSpeed(),
                gunModel.getAimTime()
        );

        setGunAngle(predictedTargetPos2D);
    }

    private void setTargetAngle(Point2D point) {
        var dx = point.getX() - gunModel.getX();
        var dy = point.getY() - gunModel.getY();

        var angle = Math.atan2(dy, dx);
        gunModel.setTargetAngle(angle);
    }

    private void setGunAngle(Point2D point) {
        setTargetAngle(point);

        var maxStep = gunModel.getSpec().getRotationSpeed() * deltaTime;
        var delta = getDeltaAngle();

        if (Math.abs(delta) <= maxStep) {
            gunModel.setAngle(gunModel.getTargetAngle());
        } else {
            var angle = gunModel.getAngle() + Math.signum(delta) * maxStep;
            gunModel.setAngle(angle);
        }
    }

    private double getDeltaAngle() {
        var delta = gunModel.getTargetAngle() - gunModel.getAngle();
        return Math.atan2(Math.sin(delta), Math.cos(delta));
    }

    public void setTarget(EntityModel target) {
        if (targetModel != null) {
            return;
        }
        
        if (target == null) {
            return;
        }
        
        targetModel = target;
        targetModel.setAimed(true);
        fireQuota = targetModel.getHitPoint();
    }

    private void updateState() {
        if (targetModel == null) {
            gunModel.setState(GunState.IDLE);
            return;
        }

        if (fireQuota <= 0) {
            targetModel.setPunished(true);
            reset();
            return;
        }

        var gunPosition2D = new Point2D(gunModel.getX(), gunModel.getY());
        var distance = new Point2D(targetModel.getX(), targetModel.getY()).distance(gunPosition2D);
        if (distance > (SysProp.SHOOT_RADIUS)) {
            targetModel.setOutOfRange(true);
            reset();
            return;
        }

        if (isAimedAtTarget() && !gunModel.getState().equals(GunState.AIMING)) {
            gunModel.setState(GunState.AIMING);
        }

    }

    private boolean isAimedAtTarget() {
        return Math.abs(getDeltaAngle()) < ANGLE_EPSILON;
    }

    private void reset() {
        gunModel.setState(GunState.IDLE);
        targetModel = null;
        fireQuota = 0;
    }

    public EntityModel getTarget() {
        return targetModel;
    }

    public void fire() throws GunException {
        if (fireQuota <= 0) {
            throw new GunException("No fire quota!");
        }

        if (gunModel.getRecoilOffset() != 0) {
            throw new GunException("After recoil!");
        }

        if (!isAimedAtTarget()) {
            throw new GunException("Rotating!");
        }

        Sound.SHOOT.play();
        gunModel.setRecoilOffset(gunModel.getSpec().getRecoilDistance());
        fireQuota--;

    }

    public boolean isReadyToFire() {
        return isAimedAtTarget()
                && gunModel.getState().equals(GunState.AIMING)
                && gunModel.getAimTime() <= 0
                && fireQuota > 0;
    }

}
