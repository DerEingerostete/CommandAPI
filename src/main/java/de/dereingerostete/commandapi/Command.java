package de.dereingerostete.commandapi;

import java.util.Arrays;
import java.util.Objects;

public abstract class Command {
    private final String name;
    private final String[] aliases;

    public Command(String name, String[] aliases) {
        this.aliases = aliases;
        this.name = name;
    }

    public Command(String name) {
        this(name, new String[0]);
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public abstract void execute(CommandSender sender, String[] args);

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", aliases=" + Arrays.toString(aliases) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Command command = (Command) o;

        if (!Objects.equals(name, command.name)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(aliases, command.aliases);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(aliases);
        return result;
    }

}
