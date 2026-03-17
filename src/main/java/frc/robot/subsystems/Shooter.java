/*
notes:

2. This is NOT usable yet

3. This is currently just frankensteined together as a skeleton for future code

4. YES THIS IS JUST A COPY PASTE FROM INTAKE

5. I am lying, the code works now


*/

package frc.robot.subsystems;


import com.revrobotics.spark.SparkMax;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private final SparkMax lowerMotor = new SparkMax(10, MotorType.kBrushless); //green wheels
  private final SparkMax flyMotor1 = new SparkMax(16, MotorType.kBrushless); //left Flywheel
  private final SparkMax flyMotor2 = new SparkMax(15, MotorType.kBrushless); //right Flywheel
  
   public Shooter() {
    SparkMaxConfig config = new SparkMaxConfig();
    config
        .idleMode(IdleMode.kCoast)
        .smartCurrentLimit(40)
        .inverted(true);
    SparkMaxConfig config2 = new SparkMaxConfig();
    config2
        .idleMode(IdleMode.kCoast)
        .smartCurrentLimit(40)
        .inverted(true);
        SparkMaxConfig config3 = new SparkMaxConfig();
    config3
        .idleMode(IdleMode.kCoast)
        .smartCurrentLimit(40)
        // .follow(flyMotor1)
        .inverted(false);

    
    lowerMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    flyMotor1.configure(config2, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    flyMotor2.configure(config3, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  /** Run shooter at given speed (0.0 to 1.0) */
  public void lower(double speed) {
    speed = MathUtil.clamp(speed, -1, 1); //WHY THE FLIP IS IT NAMED LOWER?!?!!?!?!?!?!?!
    lowerMotor.set(speed);                     //Because it's the lower part of the shooter, idk what else to call it
  }

  public void flyWheel(double speed) {
    speed = MathUtil.clamp(speed, -1, 1);
    flyMotor1.set(speed);
    flyMotor2.set(speed);
  }

  public double getSpeed() {
    return flyMotor1.get();
  }

//4EST NOTE: Perfection incarnate:

//public void spool_down(double speed, SparkMax motor) {
//  double last_speed = motor.get();
//
//  while (motor.get() > speed ) {
//    motor.set(last_speed - 0.25);
//    last_speed = motor.get();
//
//  }
//  motor.set(speed);
//  
//
//}


  /** Stop shooter */
  public void stop() {
    lowerMotor.set(0);
    flyMotor1.set(0.2);
    flyMotor2.set(0.2);

    
  }
  public void actually_stop_or_it_will_cut_your_fingers_off() {
   lowerMotor.set(0);
    flyMotor1.set(0);
    flyMotor2.set(0);
  }

  

 

//--------------------------------------------------------------------------------------------------------------

  

//-----------------------------------------------------------------------------------------------

}

//============================================================================================================
