// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.robocats.swerve.ModuleConfig;
import com.robocats.swerve.SwerveConfig;
import com.robocats.swerve.gyroscope.AhrsGyro;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class WaypointConstants {
    public static final Pose2d middleShootingPosition = new Pose2d(2.5, 4, new Rotation2d(0 * Math.PI / 180));
    public static final Pose2d leftOfLadderShootingPosition = new Pose2d(2.5, 5.3, new Rotation2d(-30 * Math.PI / 180));
    public static final Pose2d rightOfLadderShootingPosition = new Pose2d(2.5, 2.65, new Rotation2d(30 * Math.PI / 180));
    public static final Pose2d leftOfLadderClimbingPosition = new Pose2d(1.6, 4.3, new Rotation2d(0 * Math.PI / 180));
    public static final Pose2d middleOfLadderClimbingPostion = new Pose2d(1.6, 4, new Rotation2d(0 * Math.PI / 180));
    public static final Pose2d rightOfLadderClimbingPosition = new Pose2d(1.6, 3.55, new Rotation2d(0 * Math.PI / 180));
    // x-2.5, y-4 Directly in front of scoring
  }

  public static final class DriveConstants {
    public static final int kFrontLeftDriveMotorPort = 1;
    public static final int kRearLeftDriveMotorPort = 3;
    public static final int kFrontRightDriveMotorPort = 5;
    public static final int kRearRightDriveMotorPort = 7;

    public static final int kFrontLeftTurningMotorPort = 2;
    public static final int kRearLeftTurningMotorPort = 4;
    public static final int kFrontRightTurningMotorPort = 6;
    public static final int kRearRightTurningMotorPort = 8;

    public static final int kFrontLeftEncoderPort = 14;
    public static final int kRearLeftEncoderPort = 17;
    public static final int kFrontRightEncoderPort = 16;
    public static final int kRearRightEncoderPort = 15;

    public static final double kFrontLeftAbsoluteEncoderOffset = 0.195801; //1.215576;
    public static final double kRearLeftAbsoluteEncoderOffset = 0.040039; //0.029785;
    public static final double kFrontRightAbsoluteEncoderOffset = 0.455322; //1.461426;
    public static final double kRearRightAbsoluteEncoderOffset = 0.715332; //0.221924;

    public static final ModuleConfig moduleConfiguration = new ModuleConfig(
      kFrontLeftDriveMotorPort, //FL Drive port
      kRearLeftDriveMotorPort, //BL Drive port
      kFrontRightDriveMotorPort, //FR Drive port
      kRearRightDriveMotorPort, //BR Drive port
      kFrontLeftTurningMotorPort, //FL Turn port
      kRearLeftTurningMotorPort, //BL Turn port
      kFrontRightTurningMotorPort, //FR Turn port
      kRearRightTurningMotorPort, //BR Turn port 
      kFrontLeftEncoderPort, //FL Encoder port
      kRearLeftEncoderPort, //BR Encoder port
      kFrontRightEncoderPort, //FR Encoder port
      kRearRightEncoderPort, //BR Encoder port
      kFrontLeftAbsoluteEncoderOffset, //FL Encoder offset
      kRearLeftAbsoluteEncoderOffset, //BL Encoder offset
      kFrontRightAbsoluteEncoderOffset, //FR Encoder offset
      kRearRightAbsoluteEncoderOffset, //BR Encoder offset
      //is drive motor reversed FL, BL, FR, BR
      false, false, true, true);

    public static final SwerveConfig swerveConfiguration = new SwerveConfig(
      4 / 20,  // max linear velocity
      3 * Math.PI / 10, // max angular velocity
      .1016, //wheel diameter
      TimedRobot.kDefaultPeriod, 
      DriveConstants.kDriveKinematics, 
      DriveConstants.moduleConfiguration, 
      new AhrsGyro(NavXComType.kUSB1, Math.PI/2, false),
      true //is field symmetric
    );
      

    // If you call DriveSubsystem.drive() with a different period make sure to update this.
    public static final double kDrivePeriod = TimedRobot.kDefaultPeriod;

    public static final double kTrackWidth = 0.56515; // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = 0.56515 ; // Distance between front and back wheels on robot

    public static final SwerveDriveKinematics kDriveKinematics =
        new SwerveDriveKinematics(
            new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));
  }
}
