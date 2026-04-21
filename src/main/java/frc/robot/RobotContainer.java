// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.WaypointConstants;
import frc.robot.commands.Aim;
//import frc.robot.commands.AimExact;
import frc.robot.commands.ClimbToLevel;
import frc.robot.commands.Command_4_intake;
import frc.robot.commands.LockSwerve;
import frc.robot.commands.FaceTag;
import frc.robot.commands.MoveIntake;
import frc.robot.commands.ShootFuel;
import frc.robot.commands.ShootFuelNoLock;
import frc.robot.commands.ShootWithExtraWait;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.util.ArrayList;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.robocats.swerve.SwerveSubsystem;

import frc.robot.subsystems.Aiming;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LowerIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.TempLed;

import com.robocats.vision.LimelightCamera;
import com.robocats.LED.LedStrip;
import com.robocats.LED.implement_LEDPattern;
import com.robocats.controllers.DancePad;
import com.robocats.controllers.RevGamePad;

import frc.robot.subsystems.HopperFeed;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  //misc setup ================================================================================================
  
  private final SendableChooser<Boolean> shooterChooser = new SendableChooser<>();

  public LimelightCamera limelightCamerafour;
  private LimelightCamera limelightCameraBack;

  private final Field2d m_field = new Field2d();


  //Subsystems ================================================================================================

  private final SwerveSubsystem Swerve = new SwerveSubsystem(
      DriveConstants.swerveConfiguration,
      false,
      m_field
    );
    private final SendableChooser<Command> autoChooser;
    private final SendableChooser<Character> driveChooser = new SendableChooser<Character>();
  private final Shooter shooter = new Shooter();
  private final HopperFeed hopper = new HopperFeed();
  private final Intake intake = new Intake();
  private final Climber climber = new Climber(false);
  private final Aiming aimer = new Aiming();
 private final LedStrip leds = new LedStrip(2, 300);
 private final TempLed newLeds = new TempLed();
 private final LowerIntake lowerIntake = new LowerIntake(leds);
//  private final TempLed newLeds = new TempLed();
  private int i = 1;

  //Controllers ================================================================================================

  private RevGamePad revGamePad = new RevGamePad(0);
  private DancePad dancePad = new DancePad(2);
  private Joystick ButtonBoard = new Joystick(1);
  JoystickButton A1 = new JoystickButton(ButtonBoard, 1);
  JoystickButton A2 = new JoystickButton(ButtonBoard, 2);
  JoystickButton A3 = new JoystickButton(ButtonBoard, 3);
  JoystickButton A4 = new JoystickButton(ButtonBoard, 4);
  JoystickButton A5 = new JoystickButton(ButtonBoard, 5);
  JoystickButton A6 = new JoystickButton(ButtonBoard, 6);
  JoystickButton A7 = new JoystickButton(ButtonBoard, 7);
  JoystickButton A8 = new JoystickButton(ButtonBoard, 8);

  JoystickButton A9 = new JoystickButton(ButtonBoard, 9);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // ================= PATHPLANNER EVENTS ======================================

    PathPlannerLogging.setLogTargetPoseCallback((pose) -> {
      m_field.getObject("target pose").setPose(pose);
    });
    PathPlannerLogging.setLogActivePathCallback((poses) -> {
      m_field.getObject("path").setPoses(poses);
    });

    SmartDashboard.putData("Field", m_field);

    //misc ==================================================================================================================

     limelightCamerafour = new LimelightCamera("limelight-four", Swerve::getRotation, null);
     limelightCameraBack = new LimelightCamera("limelight-back", Swerve::getRotation, null);

    //  Swerve.addCamera(0, limelightCamerafour);
    //  Swerve.addCamera(1, limelightCameraBack);

    // ================= Choosers ==================================================================================================================================
    // autoChooser.setDefaultOption("Do Nothing", new InstantCommand());

    shooterChooser.setDefaultOption("true", true);
    shooterChooser.addOption("false", false);
    driveChooser.setDefaultOption("Robot to angle", 'R');
    driveChooser.addOption("Infinite rotation", 'I');
    driveChooser.addOption("DancePad", 'D');

    SmartDashboard.putData("Drive Mode", driveChooser);
    SmartDashboard.putData("Should use flywheel", shooterChooser);
    

    //Default Commands ==================================================================================================================
    Swerve.setDefaultCommand(
      new RunCommand(() -> Swerve.drive(
        revGamePad.getLeftX(),
        revGamePad.getLeftY(), 
        revGamePad.getRightX(),
        revGamePad.getRightY()
        ), Swerve)
    ); 
    driveChooser.onChange((Character driveMode) -> {
      switch(driveMode) {
        case 'R': 
          Swerve.setDefaultCommand(
            new RunCommand(()->Swerve.drive(
              revGamePad.getLeftX(),
              revGamePad.getLeftY(), 
              revGamePad.getRightX(),
              revGamePad.getRightY()
            ), Swerve)
          ); 
          break;

        case 'I': 
          Swerve.setDefaultCommand(
            new RunCommand(()->Swerve.drive(
              revGamePad.getLeftX(),
              revGamePad.getLeftY(), 
              revGamePad.getRightX(),
              revGamePad.getRightY()
            ), Swerve)
          ); 
          break;

        case 'D': 
          double speed = 0.75;
          double rotationSpeed = 0.75;
          Swerve.setDefaultCommand(
            new RunCommand(()->Swerve.drive(
              dancePad.getLeft() ? -speed : dancePad.getRight() ? speed : 0,
              dancePad.getDown() ? -speed : dancePad.getUp() ? speed : 0,
              dancePad.getX() ? -rotationSpeed : dancePad.getO() ? rotationSpeed : 0,
              true
            ), Swerve)
          ); 
          break;
      }
    });

    shooter.setDefaultCommand( 
      new RunCommand( () -> {
        if (shooter.getSpeed() > 0.2){
          shooter.flyWheel(shooter.getSpeed()-0.05);
        } else {
          shooter.flyWheel(shooterChooser.getSelected() ? 0.2 : 0);
        }
      }, shooter)
    );
    
  //  leds.setDefaultCommand(new RunCommand( () -> {
    // leds.setAll(0, 0, 255);
      //  leds.setAllianeColor(leds.rgbToColor(255, 0, 0)[0]);
    //  }, leds
  //  )); 
  newLeds.setup();
   newLeds.setBlue();                                                                                                 
                                                                                       
    Swerve.setupPathPlanner();                                                    
                                                                                                                     
    NamedCommands.registerCommand("Shoot", new ShootFuelNoLock(shooter, hopper,  0.535, false));
    NamedCommands.registerCommand("Intake", new Command_4_intake(intake, 0.7));                           
    NamedCommands.registerCommand("LowerIntake", new MoveIntake(lowerIntake, false));                                            
    NamedCommands.registerCommand("RaiseIntake", new MoveIntake(lowerIntake, true));                                                    
    NamedCommands.registerCommand("Climb1", new ClimbToLevel(climber, 1));                                           
    NamedCommands.registerCommand("Climb2", new ClimbToLevel(climber, 2));                                                     
    NamedCommands.registerCommand("Climb3", new ClimbToLevel(climber, 3));                                                                   
    //NamedCommands.registerCommand("Aim1", new AimExact(aimer, .35));                                                                         
    NamedCommands.registerCommand("RetractClimber", new RunCommand(climber::retract, climber));                                 
                                                                                                                                                 
                                                                                                                                                            
    autoChooser = AutoBuilder.buildAutoChooser();                                                                                
    SmartDashboard.putData("Auto Chooser", autoChooser);

    configureBindings();
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

    //misc Commands ==================================================================================================================

    revGamePad.onSquare().onTrue(new InstantCommand(Swerve.swerveConfig.gyroscope()::zero, Swerve));

    revGamePad.onTriangle().whileTrue(new Aim(aimer, true));
    // revGamePad.onO().whileTrue(new Aim(aimer, false));
    revGamePad.onX().whileTrue(new Aim(aimer, false));

    //Waypoints ==================================================================================================================
    
    revGamePad.onDPadDown().whileTrue(Swerve.driveTo(WaypointConstants.middleShootingPosition) );
    revGamePad.onDPadRight().whileTrue(Swerve.driveTo(new Pose2d(14.4, 4.4, new Rotation2d(3*Math.PI))));

    A5.toggleOnTrue(Swerve.driveTo(WaypointConstants.middleShootingPosition));
    A6.toggleOnTrue(Swerve.driveTo(WaypointConstants.leftOfLadderShootingPosition));
    A4.toggleOnTrue(Swerve.driveTo(WaypointConstants.rightOfLadderShootingPosition));

    //Intake/Outake ==================================================================================================================

    revGamePad.onDPadLeft().whileTrue(new Command_4_intake(intake, -0.35));

    revGamePad.onRightTrigger(0.1).whileTrue(new ShootFuelNoLock(shooter, hopper,  0.535, false));
    revGamePad.onRightBumper().whileTrue(new ShootFuelNoLock(shooter, hopper,  1, true));

    revGamePad.onLeftBumper().whileTrue(new MoveIntake(lowerIntake, false));
    revGamePad.onOptions().whileTrue(new MoveIntake(lowerIntake, true));
    revGamePad.onLeftTrigger(0.1).whileTrue(new Command_4_intake(intake, 0.7));

    revGamePad.onDPadUp().whileTrue(new FaceTag(Swerve, limelightCamerafour));
    // revGamePad.onRightStickIn().or(revGamePad.onLeftStickIn()).onTrue(new RunCommand(()->{int a = 1/0;}));

    //DANCE PAD ==========================================================================================================
    //Ex:
    //FOR STANDING ON CENTER WHILE PRESSING:
    //dancePad.onCenter().and(dancePad::getO);
    //----------------
    //FOR NOT STANDING ON CENTER WHILE PRESSING:
    //dancePad.onCenter().negate().and(dancePad::getO)

    //gyro
    dancePad.onCenter().and(()->driveChooser.getSelected() != 'D').and(dancePad::getUp).onTrue(new InstantCommand(Swerve.swerveConfig.gyroscope()::zero, Swerve));
    //aimer
    dancePad.onCenter().and(()->driveChooser.getSelected() != 'D').and(dancePad::getX).whileTrue(new ShootFuelNoLock(shooter, hopper, 1, false));
    dancePad.onCenter().and(()->driveChooser.getSelected() != 'D').and(dancePad::getO).whileTrue(new LockSwerve(Swerve));
   

    dancePad.onCenter().and(()->driveChooser.getSelected() != 'D').and(dancePad::getLeft).whileTrue(new ShootFuelNoLock(shooter, hopper, 0.55, false));
    //shooter/hopper/intake
    dancePad.onCenter().and(()->driveChooser.getSelected() != 'D').and(dancePad::getRight).whileTrue(new ShootFuelNoLock(shooter, hopper,  1, true));
    dancePad.onCenter().and(()->driveChooser.getSelected() != 'D').and(dancePad::getDown).whileTrue(new MoveIntake(lowerIntake, false));




    dancePad.onCenter().and(()->driveChooser.getSelected() == 'D').and(dancePad::getStart).onTrue(new InstantCommand(Swerve.swerveConfig.gyroscope()::zero, Swerve));
    dancePad.onCenter().and(()->driveChooser.getSelected() == 'D').and(dancePad::getSelect).whileTrue(new MoveIntake(lowerIntake, false));
    dancePad.onCenter().and(()->driveChooser.getSelected() == 'D').and(dancePad::getTriangle).whileTrue(new ShootFuelNoLock(shooter, hopper, 1, false));

    dancePad.onSquare().and(()->driveChooser.getSelected() == 'D').whileTrue(new Command_4_intake(intake, 0.8));
  }

  /* 
   Use this to pass the autonomous command to the main {@link Robot} class.

    @return the command to run in autonomous
   */
 // private final Led ledd = new Led();

  //public Command Leds() {
  //Optional<Alliance> alliance = DriverStation.getAlliance();
  //
  //if (alliance.isPresent()) {
  //  if (alliance.get() == Alliance.Red) {
  //    // Make LEDs red
  //    return new Leds(ledd, 255, 0, 0);
  //  } if (alliance.get() == Alliance.Blue) {
  //    // Make LEDs blue
  //    return new Leds(ledd, 0, 0, 139);
  //  } else { 
  //    // Gold when not on an alliance
  //    return new Leds(ledd, 255, 215, 0);
  //  }
  //}
  //  return null;

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }
}