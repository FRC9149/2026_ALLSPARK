package frc.robot.subsystems;
    
import java.util.HashMap;

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

public class Hopper extends SubsystemBase{

    private final SparkMax HopperMotor = new SparkMax(1, MotorType.kBrushless);

    public Hopper() {

        SparkMaxConfig config = new SparkMaxConfig();
        // Set the limit
        config.smartCurrentLimit(30); 
        // Apply the config to the motor
        HopperMotor.configure(config, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);
    }
    /*
    Sets the hopper speed from -1.0 to 1.0
    */
    public void setSpeed(double speed) {
        HopperMotor.set(speed);
    }

    public void stop() {
        HopperMotor.set(0);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // Good place for SmartDashboard telemetry
    }
}