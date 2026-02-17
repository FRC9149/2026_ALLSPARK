// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.WaypointConstants;
import frc.robot.commands.Aim;
import frc.robot.commands.ClimbToLevel;
import frc.robot.commands.Command_4_intake;
import frc.robot.commands.MoveIntake;
import frc.robot.commands.ShootFuel;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static edu.wpi.first.units.Units.Seconds;

import java.util.Map;

import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.units.Units;


import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.robocats.swerve.SwerveConfig;
import com.robocats.swerve.SwerveSubsystem;
import com.studica.frc.AHRS.NavXComType;

import frc.robot.subsystems.Aiming;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LedStrip;
import frc.robot.subsystems.LowerIntake;
import frc.robot.subsystems.Shooter;

import com.robocats.swerve.gyroscope.AhrsGyro;
import com.robocats.vision.LimelightCamera;
import com.robocats.swerve.ModuleConfig;
import com.robocats.controllers.Ps3;
import com.robocats.controllers.RevGamePad;

/*import frc.robot.Constants.WaypointConstants;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();
  private LimelightCamera limelightCamerafour;

  private final SwerveSubsystem Swerve = new SwerveSubsystem(
    DriveConstants.swerveConfiguration,
    //2.55, 1.5, 0.2
    new PIDController(0.5,0.01,0.01),
    limelightCamerafour,
    true
  );
  
  private final Shooter shooter = new Shooter();
  private final LowerIntake lowerIntake = new LowerIntake();
  private final Intake intake = new Intake();
  private final Climber climber = new Climber(false);
  private final LedStrip leds = new LedStrip(0, 300);
  private final Aiming aimer = new Aiming();
  private int i = 1;
  


  // Replace with CommandPS4Controller or CommandJoystick if needed
  // private final CommandXboxController m_driverController =
      //new CommandXboxController(OperatorConstants.kDriverControllerPort); EXAMPLE
    private RevGamePad RevGamePad = new RevGamePad(0);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
   public RobotContainer() {
    // Configure the trigger bindings
    limelightCamerafour = new LimelightCamera("Limelight-four", Swerve::getHeading, null);
    configureBindings();

     // ================= PATHPLANNER EVENTS =================
    NamedCommands.registerCommand("Shoot", new ShootFuel(shooter, 0.5));
    NamedCommands.registerCommand("Intake", new Command_4_intake(intake));
    NamedCommands.registerCommand("LowerIntake", new MoveIntake(lowerIntake, false));
    NamedCommands.registerCommand("RaiseIntake", new MoveIntake(lowerIntake, true));
    NamedCommands.registerCommand("Climb1", new ClimbToLevel(climber, 1));
    NamedCommands.registerCommand("Climb2", new ClimbToLevel(climber, 2));
    NamedCommands.registerCommand("Climb3", new ClimbToLevel(climber, 3));
    NamedCommands.registerCommand("RetractClimber", new RunCommand(climber::retract, climber));
    // NamedCommands.registerCommand("Wait1", new WaitCommand(1)); There is already a wait command inside pathplanner
    // NamedCommands.registerCommand("Wait2", new WaitCommand(2));
    // NamedCommands.registerCommand("Wait3", new WaitCommand(3));

    // ================= AUTOS =================
    autoChooser.setDefaultOption("Do Nothing", new InstantCommand());

  

    //autoChooser.addOption(
    //    "Shoot And Leave",
    //    AutoBuilder.buildAuto("ShootAndLeave")
    //);
//
    //autoChooser.addOption(
    //    "Two Ball",
    //    AutoBuilder.buildAuto("TwoBallAuto")
    //);

    SmartDashboard.putData("Auto Chooser", autoChooser);


/*
    Swerve.setDefaultCommand(
      new RunCommand(() -> Swerve.drive(
        RevGamePad.getLeftY(),
        RevGamePad.getLeftX(), 
        RevGamePad.getRightX(),
        true
        ), Swerve)

    );*/

    
    leds.setDefaultCommand( 
       // new SequentialCommandGroup( 
          new RunCommand( () -> {
              leds.applyLEDPattern(LEDPattern.solid(Color.fromHSV(i, 255, 80)));
              if (i >= 180) {
              i = 0;
              }
              else {
                i++;
              }
           }, leds)
          //new WaitCommand(0.1)
       // )
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
    // RevGamePad.onSquare().onTrue(new InstantCommand(()->Swerve.swerveConfig.gyroscope().zero(), Swerve));
    // RevGamePad.onLeftBumper().onTrue(Swerve.driveTo(WaypointConstants.leftOfLadderShootingPosition));
    // RevGamePad.onRightBumper().onTrue(Swerve.driveTo(WaypointConstants.rightOfLadderShootingPosition));
    // RevGamePad.onLeftBumper().and(RevGamePad.onRightBumper()).onTrue(Swerve.driveTo(WaypointConstants.middleShootingPosition));
    // RevGamePad.onX().onTrue(Swerve.driveTo(WaypointConstants.leftOfLadderClimbingPosition));
    // RevGamePad.onX().onTrue(Swerve.driveTo(WaypointConstants.middleOfLadderClimbingPostion));
    // RevGamePad.onX().onTrue(Swerve.driveTo(WaypointConstants.rightOfLadderClimbingPosition));
    RevGamePad.onRightTrigger(0.1).whileTrue(new ShootFuel(shooter, 1));
    // RevGamePad.onLeftTrigger(1).onTrue(new Command_4_intake(intake));
    // RevGamePad.onO().onTrue(new MoveIntake(lowerIntake, false));
    // RevGamePad.onTriangle().onTrue(new MoveIntake(lowerIntake, true));
    // RevGamePad.onDPadLeft().onTrue(new RunCommand(climber :: retract, climber));
    //RevGamePad.onSquare().onTrue(new InstantCommand( () -> {
    //  leds.setAll(255, 0, 0);
    //}));
   // RevGamePad.onSquare().onTrue(
   // new SequentialCommandGroup(
//
   //     new InstantCommand(
   //         () -> leds.applyActiveLEDPattern(
   //             LEDPattern.solid(Color.kRed)
   //                 .blink(Units.Seconds.of(2))
   //         ),
   //         leds
   //     ),
//
   //     new WaitCommand(6),
//
   //     new InstantCommand(
   //         () -> leds.applyActiveLEDPattern(
   //             LEDPattern.atRGB8(index -> new Color8Bit(
   //                 (int)(Math.random() * 255),
   //                 (int)(Math.random() * 255),
   //                 (int)(Math.random() * 255)
   //             ))
   //         ),
   //         leds
   //     )
   // )
//);
    // RevGamePad.onDPadDown().onTrue(new ClimbToLevel(climber, 1));
    // RevGamePad.onDPadRight().onTrue(new ClimbToLevel(climber, 2));
    // RevGamePad.onDPadUp().onTrue(new ClimbToLevel(climber, 3));

    RevGamePad.onO().whileTrue(new Aim(aimer, 1.0));
    RevGamePad.onTriangle().whileTrue(new Aim(aimer, 0.5));

//    RevGamePad.onSquare().whileTrue( 
//      new RunCommand(() -> {
        //code to run
//        leds.applyLEDPattern(LEDPattern.solid(Color.kRed));
        // Create an LED pattern that displays the first half of a strip as solid red,
// and the second half of the strip as solid blue.
// leds.sethalf();
//      },leds
//    )
//    
//    );
//    
  }
 


  /* 
   Use this to pass the autonomous command to the main {@link Robot} class.
   
    @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }
}
