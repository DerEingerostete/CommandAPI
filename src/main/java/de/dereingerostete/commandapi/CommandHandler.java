/*
  Copyright 2021 DerEingerostete

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package de.dereingerostete.commandapi;

import net.labymod.api.EventManager;
import net.labymod.api.LabyModAPI;
import net.labymod.api.events.MessageSendEvent;

import java.util.*;

public class CommandHandler implements MessageSendEvent {
    private final String prefix;
    private final List<Command> commands;

    public CommandHandler(LabyModAPI api) {
        this(api, "/");
    }

    public CommandHandler(LabyModAPI api, String prefix) {
        Objects.requireNonNull(api, "API cannot be null");
        this.prefix = prefix;

        EventManager manager = api.getEventManager();
        manager.register(this);

        commands = new ArrayList<>();
    }

    public void registerCommand(Command command) {
        Objects.requireNonNull(command, "Command cannot be null");
        if (!commands.contains(command))
            commands.add(command);
    }

    public void unregisterCommand(Command command) {
        commands.remove(command);
    }

    public void unregisterAll(Command command) {
        commands.clear();
    }

    public List<Command> getCommands() {
        return Collections.unmodifiableList(commands);
    }

    private Command getCommand(String name) {
        return commands.stream()
                .filter(command -> {
                    String[] aliases = command.getAliases();
                    List<String> names = Arrays.asList(aliases);
                    names.add(command.getName());
                    return names.contains(name);
                }).findFirst().orElse(null);
    }

    private boolean callCommand(String message) {
        try {
            String name;
            String[] args;

            int index = message.indexOf(" ");
            int prefixLength = prefix.length();
            if (index > 0) {
                name = message.substring(prefixLength, index);

                String argumente = message.substring(index + prefixLength);
                args = argumente.split(" ");
            } else {
                name = message.substring(prefixLength);
                args = new String[0];
            }

            Command command = getCommand(name);
            if (command == null) return false;

            CommandSender sender = CommandSender.getDefault();
            command.execute(sender, args);

            return true;
        } catch (Exception exception) {
            throw new CommandException("Failed to execute command", exception);
        }
    }

    @Override
    public boolean onSend(String message) {
        if (!message.startsWith(prefix)) return false;
        return callCommand(message);
    }

    @Override
    public String toString() {
        return "CommandHandler{" +
                "prefix='" + prefix + '\'' +
                ", commands=" + commands +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommandHandler that = (CommandHandler) o;

        if (!Objects.equals(prefix, that.prefix)) return false;
        return Objects.equals(commands, that.commands);
    }

    @Override
    public int hashCode() {
        int result = prefix != null ? prefix.hashCode() : 0;
        result = 31 * result + (commands != null ? commands.hashCode() : 0);
        return result;
    }

}
