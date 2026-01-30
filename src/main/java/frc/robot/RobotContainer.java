// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriveConstants;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import com.robocats.swerve.SwerveSubsystem;
import com.studica.frc.AHRS.NavXComType;

import com.robocats.swerve.gyroscope.AhrsGyro;
import com.robocats.swerve.ModuleConfig;
import com.robocats.controllers.Ps3;
import com.robocats.controllers.RevGamePad;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem Swerve = new SwerveSubsystem(
    DriveConstants.swerveConfiguration, 
    new PIDController(0.5,0.01,0.01), 
    null,
     true
  );


    private Ps3 ps3Controller = new Ps3(0);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    
    configureBindings();

    Swerve.setDefaultCommand(
      new RunCommand(() -> Swerve.drive(
        ps3Controller.getLeftY(),
        ps3Controller.getLeftX(), 
        ps3Controller.getRightX(),
        true
        ), Swerve)
    );
  }
  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {


  }

  /* 
   Use this to pass the autonomous command to the main {@link Robot} class.
   
    @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
