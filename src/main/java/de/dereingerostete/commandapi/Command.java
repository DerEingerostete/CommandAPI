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
