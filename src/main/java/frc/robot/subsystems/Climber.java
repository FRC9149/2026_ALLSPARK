package frc.robot.subsystems;

import java.util.HashMap;

import static com.robocats.YallKnowWhatThisIs.THE_NULL.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private final SparkMax Climbm1 = new SparkMax(62, MotorType.kBrushless);
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

  private final HashMap<Integer, Double> climbHeights = new HashMap<>();

  //TODO
  //If this value is 0, then the motors will oscolate until they hit the target exactly. 
  //(they will never hit the target exactlt because of momentum) [damn Newton]
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
    
  }
  

  private void initHeights() {
    climbHeights.put(0, 0.0);
    climbHeights.put(1, 0.0);
    climbHeights.put(2, 0.0);
  }

  public void moveToHeight(int level, boolean hold) {
    if (!climbHeights.containsKey(level)) return; // safety check

    double targetHeight = climbHeights.get(level);

    double error = targetHeight - getHeight();
    double holdSpeed = hold ? HOLD_SPEED : 0;

    setSpeed( //TODO you set the motor speed twice There is alao a getHeight function that takes the average of the encoder positions
        Math.abs(error) > encoderTolerance
            ? (error > 0 ? 0.6 : -0.4)
            : holdSpeed);
        double kP = 0.5;
        double kI = 0.1;
        double kD = 0.1;
        PIDController pid = new PIDController(kP, kI, kD);
        Climbm1.set(pid.calculate(e1.getPosition(), targetHeight));
        // Sets the error tolerance to 5, and the error derivative tolerance to 10 per second
        // pid.setTolerance(5, 10);
        // Returns true if the error is less than 5 units, and the
        // error derivative is less than 10 units
        // pid.atSetpoint();
        // The integral gain term will never add or subtract more than 0.5 from
        // the total loop output
        // pid.setIntegratorRange(-0.5, 0.5);
    //TODO
    //if we change this to a pid controller then we don't need the tollerance
    //It will be harder to tune however, but it would be more accurate
  }

    public void setSpeed(double speed) {
        if (!withinLimits(speed)) {
        stop();
        return;
    }
        Climbm1.set(speed);
        Climbm2.set(speed);

  }


  public boolean atMinHeight() {
  return getHeight() <= MIN_HEIGHT + encoderTolerance;
}

  public void retract() {
    //TODO
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

  }

  public void hold() {
    Climbm1.set(HOLD_SPEED);
    Climbm2.set(HOLD_SPEED);
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
    return Math.abs(getHeight() - climbHeights.get(level)) <= encoderTolerance;
  }

  public boolean atMaxHeight() {
    //TODO
    //This doesn't make since to me since we already have the within limits funciton which does this but more
    return getHeight() >= MAX_HEIGHT;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Climber Encoders", getHeight());
    SmartDashboard.putNumber("Climber one", e1.getPosition());
    SmartDashboard.putNumber("Climber two", e2.getPosition());
  }
}
