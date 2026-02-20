
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
  private double speed;
  private int i = 0;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ShootFuel(Shooter subsystem, double speed) {
    this.subsystem = subsystem;
    this.speed = speed;
     // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {//Robot container has the code //huh???
    subsystem.flyWheel(speed);
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(i > 50){
    subsystem.lower(speed);
    subsystem.temp.set(-speed);}
    i++;
  }
  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {return false;}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subsystem.lower(0);
    subsystem.temp.set(0);
    //subsystem.stop();
    i=0;
  }

}