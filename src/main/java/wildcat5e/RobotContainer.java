package wildcat5e;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import wildcat5e.commands.ArcadeDrive;
import wildcat5e.commands.AutonomousBalance;
import wildcat5e.commands.AutonomousDistance;
import wildcat5e.commands.AutonomousTime;
import wildcat5e.subsystems.Drivetrain;
import wildcat5e.subsystems.OnBoardIO;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final Drivetrain drivetrain = new Drivetrain();

    // Assumes a gamepad plugged into channel 0
    private final Joystick controller = new Joystick(0);

    // Create SmartDashboard chooser for autonomous routines
    private final SendableChooser<Command> chooser = new SendableChooser<>();

    // NOTE: The I/O pin functionality of the 5 exposed I/O pins depends on the hardware "overlay"
    // that is specified when launching the wpilib-ws server on the Romi raspberry pi.
    // By default, the following are available (listed in order from inside the board to outside):
    // - DIO 8 (mapped to Arduino pin 11, closest to the inside of the board)
    // - Analog In 0 (mapped to Analog Channel 6 / Arduino Pin 4)
    // - Analog In 1 (mapped to Analog Channel 2 / Arduino Pin 20)
    // - PWM 2 (mapped to Arduino Pin 21)
    // - PWM 3 (mapped to Arduino Pin 22)
    //
    // Your subsystem configuration should take the overlays into account

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Default command is arcade drive. This will run unless another command is scheduled over it.
        drivetrain.setDefaultCommand(getArcadeDriveCommand());

        // Setup SmartDashboard options
        chooser.setDefaultOption("Auto Balance", new AutonomousBalance(drivetrain));
        chooser.addOption("Auto Distance", new AutonomousDistance(drivetrain));
        chooser.addOption("Auto Time", new AutonomousTime(drivetrain));
        SmartDashboard.putData(chooser);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return chooser.getSelected();
    }

    /**
     * Use this to pass the teleop command to the main {@link Robot} class.
     *
     * @return the command to run in teleop
     */
    public Command getArcadeDriveCommand() {
        return new ArcadeDrive(drivetrain, () -> -controller.getRawAxis(1), () -> -controller.getRawAxis(2));
    }
}
