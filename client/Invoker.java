package client;

import commands.Command;

public class Invoker {
    Command command;

    void setCommand(Command command) {
        this.command = command;
    }

    void executeCommand() {
        command.execute();
    }
}
