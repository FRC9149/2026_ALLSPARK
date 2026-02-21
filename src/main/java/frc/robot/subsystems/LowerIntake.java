package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LowerIntake extends SubsystemBase{

    private final SparkMax intake2m = new SparkMax(12, MotorType.kBrushless); // or 11

    private final SparkMax intake3m = new SparkMax(19, MotorType.kBrushless);

    

    

    private final RelativeEncoder encoder = intake2m.getEncoder();

    private final RelativeEncoder encoder2 = intake2m.getEncoder();
    
    private final SparkClosedLoopController controller = intake2m.getClosedLoopController();
    private final SparkClosedLoopController controller2 = intake2m.getClosedLoopController();
    // Closed loop adjusts number to find the correct spot for PIDs.
    // Ex. You want to throw a baseball at 30 mph. First throw is 36, so you soften your throw to 28. 
    // You strengthen it again back to 30 mph
    // - Hugo

    // Need position changed after a while
    private static final double IN_POS = 0.0;
    private static final double OUT_POS = 0.8;

    public LowerIntake() {
        
         SparkMaxConfig config = new SparkMaxConfig();
         // Set the limit
         config.smartCurrentLimit(30); //TODO Current Limit again. read HopperFeed.java
         // Apply the config to the motor

         // PID tuning (start small)
         config.closedLoop.pid(0.1, 0, 0); 

         // Soft limits
          config.softLimit.forwardSoftLimit(1);  // max down
          config.softLimit.forwardSoftLimitEnabled(true);
         
          config.softLimit.reverseSoftLimit(0);   // fully up
          config.softLimit.reverseSoftLimitEnabled(true);

          SparkMaxConfig config2 = new SparkMaxConfig();

          config2.inverted(true);

         // Set the limit
         config2.smartCurrentLimit(30); //TODO Current Limit again. read HopperFeed.java
         // Apply the config to the motor

         // PID tuning (start small)
         config2.closedLoop.pid(0.1, 0, 0); 

         // Soft limits
          config2.softLimit.forwardSoftLimit(1);  // max down
          config2.softLimit.forwardSoftLimitEnabled(true);
         
          config2.softLimit.reverseSoftLimit(0);   // fully up
          config2.softLimit.reverseSoftLimitEnabled(true);

         intake2m.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
         intake3m.configure(config2, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        

         encoder.setPosition(IN_POS); // define "in" as zero
         encoder2.setPosition(IN_POS);
    }
   
    public void intakeDown() {
       controller.setSetpoint(OUT_POS, ControlType.kPosition);
       controller2.setSetpoint(OUT_POS, ControlType.kPosition);
    }

     // Move intake up
    public void intakeUp() {
        controller.setSetpoint(IN_POS, ControlType.kPosition);
        controller2.setSetpoint(IN_POS, ControlType.kPosition);
    }

    public void stop() {
            intake2m.stopMotor();
            intake2m.stopMotor();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Lower intake encoder", encoder.getPosition());
        SmartDashboard.putNumber("Lower intake encoder", encoder2.getPosition());
    }
}