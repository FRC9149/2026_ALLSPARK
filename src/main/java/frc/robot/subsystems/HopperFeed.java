package frc.robot.subsystems;
    
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase; 

public class HopperFeed extends SubsystemBase{
    private final SparkMax hopperMotor = new SparkMax(11, MotorType.kBrushless);

    public HopperFeed() {
        SparkMaxConfig config = new SparkMaxConfig();
    }
    public void setSpeed(double speed) { 
        hopperMotor.set(speed);
    }
    public void stop() {
        hopperMotor.set(0);
    }
}