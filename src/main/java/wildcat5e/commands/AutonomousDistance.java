package wildcat5e.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import wildcat5e.subsystems.Drivetrain;

public class AutonomousDistance extends SequentialCommandGroup {
    /**
     * Creates a new Autonomous Drive based on distance. This will drive out for a specified distance, turn around and
     * drive back.
     *
     * @param drivetrain The drivetrain subsystem on which this command will run
     */
    public AutonomousDistance(Drivetrain drivetrain) {
        addCommands(
                new DriveDistance(-0.5, 10, drivetrain),
                new TurnDegrees(-0.5, 180, drivetrain),
                new DriveDistance(-0.5, 10, drivetrain),
                new TurnDegrees(0.5, 180, drivetrain));
    }
}
