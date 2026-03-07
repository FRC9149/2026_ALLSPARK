package frc.robot.subsystems;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;

import static com.robocats.YallKnowWhatThisIs.THE_NULL.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private final SparkMax Climbm1 = new SparkMax(13, MotorType.kBrushless);//front left
  private final SparkMax Climbm2 = new SparkMax(41, MotorType.kBrushless);

  private final RelativeEncoder e1 = Climbm1.getEncoder();
  private final RelativeEncoder e2 = Climbm2.getEncoder();

/* NULL memorial
   private final double NULL = 0;

private final double nulL = NULL;
private final double nuLl = NULL;
private final double nuLL = NULL;
private final double nUll = NULL;
private final double nUlL = NULL;
private final double nULl = NULL;
private final double nULL = NULL;
private final double Null = NULL;
private final double NulL = NULL;
private final double NuLl = NULL;
private final double NuLL = NULL;
private final double NUll = NULL;
private final double NUlL = NULL;
private final double NULl = NULL;

I found a really good youtube tutorial for making subsystems and commands
https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=RDIUrBEMSCW0M&index=10
*/

  private final double MAX_HEIGHT = 0;
  private final double MIN_HEIGHT = 0;//TODO
  private final double HOLD_SPEED = 0; // This will be the same as last year
  //It is just the speed that the motors should run so that they don't go anywhere but also don't move via outside force
  //We actually don't need this because we have the release
  double kP = 0.5;
  double kI = 0.1;
  double kD = 0.1;
  PIDController pid = new PIDController(kP, kI, kD);
  
  private final Servo s1release = new Servo(18);
  private final Servo s2release = new Servo(19);

  private static final int MIN_ANGLE = 0;
  private static final int MAX_ANGLE = 180;

  private final static int LOCKED_ANGLE = 180; //TODO wouldn't this be the same as min and max?
  private final static int RELEASED_ANGLE = 0; // Doesn't this just set positions to lock the release? -Hugo
  //4EST NOTE they miighghhtttt mmaayyybbeee be the min and max, and you maaayyyyy be right, but I like LOCK and RELEASE better - 4est

  //lets just leave hold speed, take up a few more bytes, and pretend we need it

  private final HashMap<Integer, Double> climbHeights = new HashMap<>();

  //TODO
  //If this value is 0, then the motors will oscolate until they hit the target exactly. 
  //(they will never hit the target exactly because of overshoot) [Darn Isaac Newton]
  private final double encoderTolerance = .1;

  private final SparkMaxConfig config = new SparkMaxConfig();

  public Climber(boolean inverted) {
    config
        .inverted(inverted) //TODO
        .idleMode(IdleMode.kBrake); //This should do the same thing as hold_speed but it didn't last year (maybe this year it will work)

    Climbm1.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    Climbm2.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

    zeroEncoders();
    initHeights();
    pid.setTolerance(encoderTolerance);
    
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

  private void initHeights() {
    climbHeights.put(0, 0.0);
    climbHeights.put(1, 0.0);

    climbHeights.put(2, 0.0);
    climbHeights.put(4, 0.0);
  }

  public void moveToHeight(int level) { //pretty self explanitory
    if (!climbHeights.containsKey(level)) return; // safety check

    double targetHeight = climbHeights.get(level);
    pid.setSetpoint(targetHeight);

    setSpeed(pid.calculate(getHeight()));
    if (pid.atSetpoint()) {
      stop();
    }

  }

    public void setSpeed(double speed) {
        if (!withinLimits(speed)) {
        stop();
        return;
    }
        if (speed != 0) {
          release();
        }
        if (speed == 0) {
          lock();
        }
      Climbm1.set(speed);
      Climbm2.set(speed);

  }


  public boolean atMinHeight() {
  return getHeight() <= MIN_HEIGHT + encoderTolerance;
}

  public void retract() {
    //NOTTODO
    //should use a pid controller. make the setpoint the min height and the current value to the encoders
    //we won't need to use the if statement since the pid controller will slow down as we reach the target
  if (!atMinHeight()) {
    setSpeed(-0.5);   //Goes down
  } else {
    stop();
  }
}

  public void stop() {
    Climbm1.set(0);
    Climbm2.set(0);
    lock();
  }

  
  public void zeroEncoders() {
    e1.setPosition(0);
    e2.setPosition(0);
  }

  /**
   * @return false if the speed would cause the climber to extend beyond its limits
   */
  private boolean withinLimits(double speed) {
    double height = getHeight();

    if (height >= MAX_HEIGHT && speed > 0)
      return false;
    if (height <= MIN_HEIGHT && speed < 0)
      return false;

    return true;
  }

  public double getHeight() {
    return (Math.abs(e1.getPosition()) + Math.abs(e2.getPosition())) / 2;
  }

  public double getTargetHeight(int level) {
  return climbHeights.getOrDefault(level, getHeight());
}

  public boolean atHeight(int level) {
    //TODO 
    //This is a good use of encoder tolerance since we can't use the pid for this

    //4EST NOTE: Thank you?
    return Math.abs(getHeight() - climbHeights.get(level)) <= encoderTolerance;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Climber Encoders", getHeight());
    SmartDashboard.putNumber("Climber one", e1.getPosition());
    SmartDashboard.putNumber("Climber two", e2.getPosition());
    SmartDashboard.putNumber("Released status s1", s1release.getAngle());
    SmartDashboard.putNumber("Released status s2", s2release.getAngle());
  }
}
