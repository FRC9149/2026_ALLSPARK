package frc.robot.subsystems;
    
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase; 

public class HopperFeed extends SubsystemBase{

    private final SparkMax hopperMotor = new SparkMax(20, MotorType.kBrushless);

    public HopperFeed() {

        SparkMaxConfig config = new SparkMaxConfig();
        // Set the limit
        config.smartCurrentLimit(30); 
        // Apply the config to the motor
        hopperMotor.configure(config, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);
    }
    /*
    Sets the hopper speed from -1.0 to 1.0
    */
    public void setSpeed(double speed) {
        hopperMotor.set(speed);
    }

    public void stop() {
        hopperMotor.set(0);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // Good place for SmartDashboard telemetry
    }
}