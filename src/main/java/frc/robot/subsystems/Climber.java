// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.ClimberConstants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

  private CANSparkMax rightMotor, leftMotor;
  private RelativeEncoder leftEncoder, rightEncoder;

  public Climber() {
    rightMotor = new CANSparkMax(ClimberConstants.kRightClimbMotorPort, MotorType.kBrushless);
    leftMotor = new CANSparkMax(ClimberConstants.kLeftClimbMotorPort, MotorType.kBrushless);

    rightMotor.setIdleMode(IdleMode.kBrake);
    leftMotor.setIdleMode(IdleMode.kBrake);

    rightMotor.setSoftLimit(SoftLimitDirection.kReverse, ClimberConstants.kMaxHeight);
    leftMotor.setSoftLimit(SoftLimitDirection.kReverse, ClimberConstants.kMaxHeight);

    rightMotor.setSmartCurrentLimit(40);
    leftMotor.setSmartCurrentLimit(40);

    leftEncoder = leftMotor.getEncoder();
    rightEncoder = rightMotor.getEncoder();

    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }

  public void climberUp() {
    leftMotor.set(-1);
    rightMotor.set(1);
  }

  public void climberDown() {
    leftMotor.set(1);
    rightMotor.set(-1);
  }

  public Command up() {
    return new InstantCommand(
        () -> {
          climberUp();
        });
  }

  public Command down() {
    return new InstantCommand(
        () -> {
          climberDown();
        });
  }


  @Override
  public void periodic() {
    SmartDashboard.getNumber("Current Output", rightMotor.get());
  }
}
