package seedu.taskforge.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HelpCommandTest {

    @Test
    public void execute_showHelp() {
        HelpCommand helpCommand = new HelpCommand();

        CommandResult commandResult = helpCommand.execute(null);

        assertEquals(HelpCommand.SHOWING_HELP_MESSAGE, commandResult.getFeedbackToUser());
        assertTrue(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void messageUsage_correct() {
        assertEquals("help: Shows in-app help instructions.\nExample: help",
                HelpCommand.MESSAGE_USAGE);
    }
}
