
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.robocats.swerve.SwerveSubsystem;
import com.robocats.vision.LimelightCamera;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HopperFeed;

/** An example command that uses an example subsystem. */
public class FaceTag extends Command {
  @SuppressWarnings("PMD.UnusedPrivateField")
  
  private final LimelightCamera camera;
  private final SwerveSubsystem swerve;
 
  /**
   * Creates a new ExampleCommand.
   *
   * @param shooterSub The subsystem used by this command.
   */
  public FaceTag(SwerveSubsystem swerve, LimelightCamera camera) {
    this.swerve = swerve;
    this.camera = camera;
     // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(swerve, camera);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     if (camera.hasTargets()) {
      // Use your existing faceTag() method to get the PID calculation
      double rotationSpeed = camera.faceTag();
      
      // Apply to drive (assuming a standard drive method: forward, strafe, rotation)
      swerve.drive(0, 0, rotationSpeed, true); 
    } else{
        swerve.drive(0, 0, 0, true);
    }
  }
  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {return false;}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerve.drive(0, 0, 0, true);
  }

}