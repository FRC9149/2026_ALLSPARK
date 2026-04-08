
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.robocats.swerve.SwerveSubsystem;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HopperFeed;

/** An example command that uses an example subsystem. */
public class LockSwerve extends Command {
  @SuppressWarnings("PMD.UnusedPrivateField")
  
  private final SwerveSubsystem swerve;
 
  /**
   * Creates a new ExampleCommand.
   *
   * @param shooterSub The subsystem used by this command.
   */
  public LockSwerve(SwerveSubsystem swerve) {
    
    this.swerve = swerve;
     // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(swerve);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    swerve.lock();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }
  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {return false;}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

}