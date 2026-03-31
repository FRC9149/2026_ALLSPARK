
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.robocats.swerve.SwerveSubsystem;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HopperFeed;

/** An example command that uses an example subsystem. */
public class ShootFuel extends Command {
  @SuppressWarnings("PMD.UnusedPrivateField")
  private final Shooter shooterSub;
  private final HopperFeed hopperSub;
  private final SwerveSubsystem swerve;
  private double flyspeed;
  private int i = 0;
  private boolean Reverse = false;
  /**
   * Creates a new ExampleCommand.
   *
   * @param shooterSub The subsystem used by this command.
   */
  public ShootFuel(Shooter shooterSub, HopperFeed hopperSub, SwerveSubsystem swerve, double flyspeed,  boolean Reverse) {
    this.shooterSub = shooterSub;
    this.hopperSub = hopperSub;
    this.swerve = swerve;
    this.flyspeed = flyspeed;
    this.Reverse = Reverse; 
     // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooterSub, hopperSub, swerve);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {//Robot container has the code //huh???
    if(!Reverse){shooterSub.flyWheel(flyspeed);}
    swerve.lock();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Reverse == true) {
      shooterSub.lower(-1);
      hopperSub.setSpeed(-1);
    } else if(i >50) {
      shooterSub.lower(1);
      hopperSub.setSpeed(1);
    }
    i++;
  }
  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {return false;}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterSub.lower(0);
    hopperSub.stop();
    //subsystem.stop();
    i = 0 ;
  }

}