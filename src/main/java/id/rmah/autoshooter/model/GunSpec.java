package id.rmah.autoshooter.model;

public class GunSpec {
    private final double barrelLength;
    private final double recoilReturnSpeed; // pixel per detik
    private final double rotationSpeed;
    private final double recoilDistance;
    private final double rateOfFire; // number of bullets per second
    
    public GunSpec(double barrelLength, double recoilReturnSpeed, double rotationSpeed, double recoilDistance) {
        this.barrelLength = barrelLength;
        this.recoilReturnSpeed = recoilReturnSpeed;
        this.rotationSpeed = rotationSpeed;
        this.recoilDistance = recoilDistance;
        this.rateOfFire = recoilReturnSpeed / recoilDistance;
    }
    
    public GunSpec(double barrelLength, double rateOfFire, double rotationSpeed) {
        this.barrelLength = barrelLength;
        this.rateOfFire = rateOfFire;
        this.rotationSpeed = rotationSpeed;
        
        this.recoilDistance = barrelLength * 0.5;
        this.recoilReturnSpeed = recoilDistance * rateOfFire;
    }

    public double getRecoilReturnSpeed() {
        return recoilReturnSpeed;
    }

    public double getBarrelLength() {
        return barrelLength;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public double getRecoilDistance() {
        return recoilDistance;
    }

    public double getRateOfFire() {
        return rateOfFire;
    }
    
}
