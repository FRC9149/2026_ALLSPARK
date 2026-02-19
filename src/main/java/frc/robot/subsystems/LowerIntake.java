package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LowerIntake extends SubsystemBase{

    private final SparkMax intake2m = new SparkMax(50, MotorType.kBrushless);

    

    private final RelativeEncoder encoder = intake2m.getEncoder();
    //TODO explain to me how the closed loop controller works. I have never used this before
    private final SparkClosedLoopController controller = intake2m.getClosedLoopController();
    // Closed loop adjusts number to find the correct spot for PIDs.
    // Ex. You want to throw a baseball at 30 mph. First throw is 36, so you soften your throw to 28. 
    // You strengthen it again back to 30 mph
    // - Hugo

    // Need position changed after a while
    private static final double IN_POS = 0.0;
    private static final double OUT_POS = 20.0;

    public LowerIntake() {

         SparkMaxConfig config = new SparkMaxConfig();
         // Set the limit
         config.smartCurrentLimit(30); //TODO Current Limit again. read HopperFeed.java
         // Apply the config to the motor

         // PID tuning (start small)
         config.closedLoop.pid(0.1, 0, 0); 

         // Soft limits
          config.softLimit.forwardSoftLimit(22);  // max down
          config.softLimit.forwardSoftLimitEnabled(true);
         
          config.softLimit.reverseSoftLimit(0);   // fully up
          config.softLimit.reverseSoftLimitEnabled(true);

         intake2m.configure(config, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);

        

         encoder.setPosition(0); // define "in" as zero
    }
   
    public void intakeDown() {
       controller.setReference(OUT_POS, ControlType.kPosition);
    }

     // Move intake up
    public void intakeUp() {
        controller.setReference(IN_POS, ControlType.kPosition);
    }

    public void stop() {
            intake2m.stopMotor();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Lower intake encoder", encoder.getPosition());
    }
}