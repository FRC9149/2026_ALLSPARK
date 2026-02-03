// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.WaypointConstants;
import frc.robot.commands.Command_4_intake;
import frc.robot.commands.MoveIntake;
import frc.robot.commands.ReleaseThenRetract;
import frc.robot.commands.ShootFuel;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import com.robocats.swerve.SwerveConfig;
import com.robocats.swerve.SwerveSubsystem;
import com.studica.frc.AHRS.NavXComType;

import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LowerIntake;
import frc.robot.subsystems.Release;
import frc.robot.subsystems.Shooter;

import com.robocats.swerve.gyroscope.AhrsGyro;
import com.robocats.swerve.ModuleConfig;
import com.robocats.controllers.Ps3;
import com.robocats.controllers.RevGamePad;

import frc.robot.Constants.WaypointConstants;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private final SwerveSubsystem Swerve = new SwerveSubsystem(
    DriveConstants.swerveConfiguration,
    new PIDController(0.5,0.01,0.01),
    null,
    true
  );
  //A changing kp so if oscilation occurs, it can be corrected
  @SuppressWarnings("unused")
  private final Shooter shooter = new Shooter();
  private final LowerIntake lowerIntake = new LowerIntake();
  private final Intake intake = new Intake();
  private final Release release = new Release();
  private final Climber climber = new Climber(false);


  // Replace with CommandPS4Controller or CommandJoystick if needed
  // private final CommandXboxController m_driverController =
      //new CommandXboxController(OperatorConstants.kDriverControllerPort); EXAMPLE
    private RevGamePad RevGamePad = new RevGamePad(0);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
   public RobotContainer() {
    // Configure the trigger bindings
    
    configureBindings();

    Swerve.setDefaultCommand(
      new RunCommand(() -> Swerve.drive(
        RevGamePad.getLeftY(),
        RevGamePad.getLeftX(), 
        RevGamePad.getRightX(),
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
    //Reset Gyro
    RevGamePad.onSquare().onTrue(new InstantCommand(()->Swerve.swerveConfig.gyroscope().zero(), Swerve));
    RevGamePad.onLeftBumper().onTrue(Swerve.driveTo(WaypointConstants.leftOfLadderShootingPosition));
    RevGamePad.onRightBumper().onTrue(Swerve.driveTo(WaypointConstants.rightOfLadderShootingPosition));
    RevGamePad.onLeftBumper().and(RevGamePad.onRightBumper()).onTrue(Swerve.driveTo(WaypointConstants.middleShootingPosition));
    // RevGamePad.onX().onTrue(Swerve.driveTo(WaypointConstants.leftOfLadderClimbingPosition));
    // RevGamePad.onX().onTrue(Swerve.driveTo(WaypointConstants.middleOfLadderClimbingPostion));
    // RevGamePad.onX().onTrue(Swerve.driveTo(WaypointConstants.rightOfLadderClimbingPosition));
    RevGamePad.onRightTrigger(1).onTrue(new ShootFuel(shooter));
    RevGamePad.onLeftTrigger(1).onTrue(new Command_4_intake(intake));
    RevGamePad.onO().onTrue(new MoveIntake(lowerIntake, false));
    RevGamePad.onTriangle().onTrue(new MoveIntake(lowerIntake, true));
    RevGamePad.onDPadLeft().onTrue(new ReleaseThenRetract(release, climber));
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
