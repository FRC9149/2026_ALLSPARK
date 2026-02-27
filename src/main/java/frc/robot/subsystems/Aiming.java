package frc.robot.subsystems;


import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Aiming extends SubsystemBase{

    private final Servo s1OnAiming = new Servo(1); //servo linear actuator
    //private final Servo s2OnAiming = new Servo(1);
    private final Servo s2OnAiming = new Servo(0);


    private static final double MIN_ANGLE = 0;
    private static final double MAX_ANGLE = 1;

    public Aiming() {
    // Calibrate signals for L16-R (1ms to 2ms range)
    s1OnAiming.setBoundsMicroseconds(2000, 1501, 1500, 1499, 1000);
    s2OnAiming.setBoundsMicroseconds(2000, 1501, 1500, 1499, 1000);
}

    public void setHeight(double height){
    height = MathUtil.clamp(height, MIN_ANGLE, MAX_ANGLE);
      s1OnAiming.set(height);
      s2OnAiming.set(height);



    //s1OnAiming.setAngle(height);
    //s1OnAiming.setPosition(height);

    //s2OnAiming.setPosition(height);
    // s1OnAiming.set(height);


    }
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Shooter aiming position", s1OnAiming.get());
    // SmartDashboard.putNumber("hui ovwera9phnu grwphnu ia grwiuohn wa goi huwae", s2OnAiming.get());
  }

    

}
