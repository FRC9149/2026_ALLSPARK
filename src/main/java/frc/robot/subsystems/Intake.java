/*
notes:

This currently does not have 
a purpose, but is set up for further coding

*/

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{
  
    private final SparkMax m1 = new SparkMax(17, MotorType.kBrushless);//used to be 14

    public Intake() {
        //TODO make a config for m1
    }

    public void intake(double speed) {
        m1.set(-speed);
    }

    public void stop() {
        m1.set(0);
    }


}

