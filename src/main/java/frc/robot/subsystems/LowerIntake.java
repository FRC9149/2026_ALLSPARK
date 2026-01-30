package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LowerIntake extends SubsystemBase{

    private final SparkMax intake2m = new SparkMax(1, MotorType.kBrushless);

    

    private final RelativeEncoder encoder = intake2m.getEncoder();
    private final SparkClosedLoopController controller = intake2m.getClosedLoopController();

    // Need position changed after a while
    private static final double IN_POS = 0.0;
    private static final double OUT_POS = 20.0;

    public LowerIntake() {

        SparkMaxConfig config = new SparkMaxConfig();
        // Set the limit
        config.smartCurrentLimit(30); 
        // Apply the config to the motor
        intake2m.configure(config, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);

         // PID tuning (start small)
        config.closedLoop.pid(0.1, 0, 0);

         encoder.setPosition(0); // define "in" as zero
    }
    /*
    Sets the hopper speed from -1.0 to 1.0
    */
    public void intakeDown(boolean currently_out) {
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
        // This method will be called once per scheduler run
        // Good place for SmartDashboard telemetry
    }
}