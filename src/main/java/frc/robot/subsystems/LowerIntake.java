package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LowerIntake extends SubsystemBase{
    private final SparkMax intake2m = new SparkMax(12, MotorType.kBrushless);
    private final RelativeEncoder encoder = intake2m.getEncoder();
    // private final SparkClosedLoopController controller = intake2m.getClosedLoopController();
    // private static final double IN_POS = 0.0;
    // private static final double OUT_POS = 0.8;
  
    // Closed loop adjusts number to find the correct spot for PIDs.
    // Ex. You want to throw a baseball at 30 mph. First throw is 36, so you soften your throw to 28. 
    // You strengthen it again back to 30 mph
    // - Hugo
    // no but close enough - El

    public LowerIntake() {
        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(IdleMode.kBrake);
        // config.closedLoop.pid(0.1, 0, 0); 

        intake2m.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);         
    }
   
    public void intakeDown() {
    //    controller.setSetpoint(OUT_POS, ControlType.kPosition);
    }
    public void intakeUp() {
        // controller.setSetpoint(IN_POS, ControlType.kPosition);    
    }

    public void just_run_the_motor_man(double speed) {
        intake2m.set(speed);
    }

    public void stop() {
        intake2m.set(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Lower intake encoder", encoder.getPosition());
      
    }
}