package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Release extends SubsystemBase{

    private final Servo s1release = new Servo(1);
    private final Servo s2release = new Servo(1);
    
    private static final int MIN_ANGLE = 0;
    private static final int MAX_ANGLE = 180;

    private final static int LOCKED_ANGLE = 0;
    private final static int RELEASED_ANGLE = 0;
 
    //constructor
    public Release() {
        lock();
    }

    public void setServos(double position) {

        position = MathUtil.clamp(position, MIN_ANGLE, MAX_ANGLE);

        s1release.setAngle(position);

        s2release.setAngle(position);

    }

    public void lock() {
        setServos(LOCKED_ANGLE);
    }
    
    public void release() {
        setServos(RELEASED_ANGLE);
    }




}
