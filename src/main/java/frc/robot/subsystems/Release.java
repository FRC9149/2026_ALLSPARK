package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Release extends SubsystemBase{
    //TODO we should move this to the climber subsystem
    //If the Release is locked, then we don't want to run the climber
    //You can add that functionality pretty easily if they are in the same subsystem
    private final Servo s1release = new Servo(18);
    private final Servo s2release = new Servo(19);
    
    private static final int MIN_ANGLE = 0;
    private static final int MAX_ANGLE = 180;

    private final static int LOCKED_ANGLE = 0; //TODO wouldn't this be the same as min and max?
    private final static int RELEASED_ANGLE = 0; // Doesn't this just set positions to lock the release? -Hugo
    //NOTE they miighghhtttt mmaayyybbeee be the min and max, and you maaayyyyy be right, but I like LOCK and RELEASE better - 4est
 
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

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Released status s1", s1release.getAngle());
        SmartDashboard.putNumber("Released status s2", s2release.getAngle());
    }


}
