package frc.robot.subsystems;


import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Aiming extends SubsystemBase{

    private final Servo s1OnAiming = new Servo(0); //servo linear actuator
    //private final Servo s2OnAiming = new Servo(1);

    private static final double MIN_ANGLE = 0;
private static final double MAX_ANGLE = 180.0;

    public void setHeight(double height){
    height = MathUtil.clamp(height, MIN_ANGLE, MAX_ANGLE);
    s1OnAiming.setAngle(height);
    //s2OnAiming.setAngle(height);


    }
  


    

}
