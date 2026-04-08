// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.BooleanSupplier;

import com.robocats.swerve.ModuleConfig;
import com.robocats.swerve.SwerveConfig;
import com.robocats.swerve.gyroscope.AhrsGyro;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.DriverStation;
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
    private static final double fieldLengthMeters = 16.54;
    private static final double fieldWidthMeters = 8.070;
    private static final Boolean isRedAlliance = DriverStation.getAlliance().get() == DriverStation.Alliance.Red;

    public static final Pose2d middleShootingPosition;
    public static final Pose2d leftOfLadderShootingPosition;
    public static final Pose2d rightOfLadderShootingPosition;
    public static final Pose2d leftOfLadderClimbingPosition;
    public static final Pose2d middleOfLadderClimbingPostion;
    public static final Pose2d rightOfLadderClimbingPosition;

    static {
      middleShootingPosition =        new Pose2d(Math.abs((isRedAlliance ? fieldLengthMeters : 0) - 2.5), Math.abs((isRedAlliance ? fieldWidthMeters : 0) - 4   ), new Rotation2d((isRedAlliance ? Math.PI : 0) + (0 * Math.PI / 180  ) )); 
      leftOfLadderShootingPosition =  new Pose2d(Math.abs((isRedAlliance ? fieldLengthMeters : 0) - 2.5), Math.abs((isRedAlliance ? fieldWidthMeters : 0) - 5.3 ), new Rotation2d((isRedAlliance ? Math.PI : 0) + (-30 * Math.PI / 180) ));
      rightOfLadderShootingPosition = new Pose2d(Math.abs((isRedAlliance ? fieldLengthMeters : 0) - 2.5), Math.abs((isRedAlliance ? fieldWidthMeters : 0) - 2.65), new Rotation2d((isRedAlliance ? Math.PI : 0) + (30 * Math.PI / 180 ) ));
      leftOfLadderClimbingPosition =  new Pose2d(Math.abs((isRedAlliance ? fieldLengthMeters : 0) - 1.6), Math.abs((isRedAlliance ? fieldWidthMeters : 0) - 4.3 ), new Rotation2d((isRedAlliance ? Math.PI : 0) + (0 * Math.PI / 180  ) ));
      middleOfLadderClimbingPostion = new Pose2d(Math.abs((isRedAlliance ? fieldLengthMeters : 0) - 1.6), Math.abs((isRedAlliance ? fieldWidthMeters : 0) - 4   ), new Rotation2d((isRedAlliance ? Math.PI : 0) + (0 * Math.PI / 180  ) ));
      rightOfLadderClimbingPosition = new Pose2d(Math.abs((isRedAlliance ? fieldLengthMeters : 0) - 1.6), Math.abs((isRedAlliance ? fieldWidthMeters : 0) - 3.55), new Rotation2d((isRedAlliance ? Math.PI : 0) + (0 * Math.PI / 180  ) ));
      
    }
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

    public static final int kFrontLeftEncoderPort = 14; //14
    public static final int kRearLeftEncoderPort = 15;//17
    public static final int kFrontRightEncoderPort = 16;//16
    public static final int kRearRightEncoderPort = 17;//15

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
      //is drive motor reversed FL, BL, FR, BR
      true, true, true, true);

      public static final double kTrackWidth = 0.56515; // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = 0.56515 ; // Distance between front and back wheels on robot

    public static final SwerveDriveKinematics kDriveKinematics =
        new SwerveDriveKinematics(
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2));

    public static final SwerveConfig swerveConfiguration = new SwerveConfig(
      4,
      Math.PI , // max angular velocity was 1.4414...then 2*Math.PI
      .1016, //wheel diameter
      TimedRobot.kDefaultPeriod, 
      DriveConstants.kDriveKinematics, 
      DriveConstants.moduleConfiguration, 
      new AhrsGyro(NavXComType.kUSB1, Math.PI / 2, true),
      false //is field symmetric
    );
      

    // If you call DriveSubsystem.drive() with a different period make sure to update this.
    public static final double kDrivePeriod = TimedRobot.kDefaultPeriod;
  }
}
