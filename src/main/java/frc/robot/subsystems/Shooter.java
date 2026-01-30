/*
notes:

2. This is NOT usable yet

3. This is currently just frankensteined together as a skeleton for future code

4. YES THIS IS JUST A COPY PASTE FROM INTAKE


*/

package frc.robot.subsystems;


import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{

  private final SparkMax SM1 = new SparkMax(1, MotorType.kBrushless);
  private final SparkMax SM2 = new SparkMax(2, MotorType.kBrushless);

   public Shooter() {
    // Motor configuration
    SparkMaxConfig config = new SparkMaxConfig();
    config
        .idleMode(IdleMode.kCoast)
        .smartCurrentLimit(40)
        .inverted(false);
    SparkMaxConfig config2 = new SparkMaxConfig();
    config2
        .idleMode(IdleMode.kCoast)
        .smartCurrentLimit(40)
        .inverted(true);
    //SparkBaseConfig config2 = new SparkBaseConfig();
    //config3
    //    .IdleMode(int 3)
    //    .smartCurrentLimit(40)
    //    .inverted(true);

    
    SM1.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    SM2.configure(config2, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Invert one of the motors so the wheels spin opposite directions and launch the ball up between them
    // SM1.setInverted(false);
    // SM2.setInverted(true);
    
  }

  /** Run shooter at given speed (0.0 to 1.0) */
  public void shoot(double speed) {
    speed = MathUtil.clamp(speed, 1, 1);
    SM1.set(speed);
    SM2.set(speed);
  }





  /** Stop shooter */
  public void stop() {
    SM1.set(0);
    SM2.set(0);
  }

 

//--------------------------------------------------------------------------------------------------------------

  

//-----------------------------------------------------------------------------------------------

}

//============================================================================================================
