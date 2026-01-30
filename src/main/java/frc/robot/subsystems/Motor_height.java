package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase; 

public class Motor_height extends SubsystemBase{
    
    @SuppressWarnings("unused")
    private final SparkMax RaiseMotor = new SparkMax(1, MotorType.kBrushless);
    @SuppressWarnings("unused")
    private final SparkMax LowerMotor = new SparkMax(2, MotorType.kBrushless);

}