package wildcat5e;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import wildcat5e.commands.ArcadeDrive;
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
    private final OnBoardIO  onboardIO  = new OnBoardIO(OnBoardIO.ChannelMode.INPUT, OnBoardIO.ChannelMode.INPUT);

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
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by instantiating a
     * {@link GenericHID} or one of its subclasses ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}),
     * and then passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // Default command is arcade drive. This will run unless another command
        // is scheduled over it.
        drivetrain.setDefaultCommand(getArcadeDriveCommand());

        // Example of how to use the onboard IO
        Trigger onboardButtonA = new Trigger(onboardIO::getButtonAPressed);
        onboardButtonA
                .onTrue(new PrintCommand("Button A Pressed"))
                .onFalse(new PrintCommand("Button A Released"));

        // Setup SmartDashboard options
        chooser.setDefaultOption("Auto Routine Distance", new AutonomousDistance(drivetrain));
        chooser.addOption("Auto Routine Time", new AutonomousTime(drivetrain));
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
        return new ArcadeDrive(
                drivetrain, () -> -controller.getRawAxis(1), () -> -controller.getRawAxis(2));
    }
}
