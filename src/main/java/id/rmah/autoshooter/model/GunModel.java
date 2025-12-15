package id.rmah.autoshooter.model;

public class GunModel {

    private GunSpec spec;
    private GunState state = GunState.IDLE;
    private double angle = 0;
    private double targetAngle = 0;
    private double recoilOffset = 0;
    private double x;
    private double y;
    private double barrelTipX;
    private double barrelTipY;
    private double aimTime = 0;

    public GunModel(double x, double y, GunSpec spec) {
        this.x = x;
        this.y = y;
        this.barrelTipX = Math.cos(angle) * spec.getBarrelLength();
        this.barrelTipY = Math.sin(angle) * spec.getBarrelLength();
        this.spec = spec;
    }

    public GunState getState() {
        return state;
    }

    public void setState(GunState state) {
        this.state = state;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getTargetAngle() {
        return targetAngle;
    }

    public void setTargetAngle(double targetAngle) {
        this.targetAngle = targetAngle;
    }

    public double getRecoilOffset() {
        return recoilOffset;
    }

    public void setRecoilOffset(double recoilOffset) {
        this.recoilOffset = recoilOffset;
    }

    public GunSpec getSpec() {
        return spec;
    }

    public void setSpec(GunSpec spec) {
        this.spec = spec;
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

    public double getBarrelTipX() {
        return barrelTipX;
    }

    public void setBarrelTipX(double barrelTipX) {
        this.barrelTipX = barrelTipX;
    }

    public double getBarrelTipY() {
        return barrelTipY;
    }

    public void setBarrelTipY(double barrelTipY) {
        this.barrelTipY = barrelTipY;
    }

    public double getAimTime() {
        return aimTime;
    }

    public void setAimTime(double aimTime) {
        this.aimTime = aimTime;
    }

}
