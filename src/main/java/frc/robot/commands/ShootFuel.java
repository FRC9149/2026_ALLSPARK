
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class ShootFuel extends Command {
  @SuppressWarnings("PMD.UnusedPrivateField")
  private final Shooter subsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ShootFuel(Shooter subsystem) {
    this.subsystem = subsystem;
     // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {//Input for when George presses the shoot button on the controller 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {subsystem.shoot(0.5);}
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {subsystem.shoot(0);}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}