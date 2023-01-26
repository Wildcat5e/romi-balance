package wildcat5e.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import wildcat5e.subsystems.Drivetrain;

public class DriveTime extends CommandBase {
    private final double     duration;
    private final double     speed;
    private final Drivetrain drive;
    private       long       startTime;

    /**
     * Creates a new DriveTime. This command will drive your robot for a desired speed and time.
     *
     * @param speed The speed which the robot will drive. Negative is in reverse.
     * @param time  How much time to drive in seconds
     * @param drive The drivetrain subsystem on which this command will run
     */
    public DriveTime(double speed, double time, Drivetrain drive) {
        this.speed = speed;
        this.duration = time * 1000;
        this.drive = drive;
        addRequirements(drive);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
        drive.arcadeDrive(0, 0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        drive.arcadeDrive(speed, 0);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drive.arcadeDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return (System.currentTimeMillis() - startTime) >= duration;
    }
}
