package de.dereingerostete.commandapi;

import net.labymod.main.LabyMod;

import java.util.Objects;
import java.util.UUID;

public class CommandSender {
    private final String name;
    private final UUID uuid;
    private final String id;

    public CommandSender(String name, UUID uuid, String id) {
        this.name = name;
        this.uuid = uuid;
        this.id = id;
    }

    public void sendMessage(String message) {
        LabyMod labyMod = LabyMod.getInstance();
        labyMod.displayMessageInChat(message);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public static CommandSender getDefault() {
        LabyMod labyMod = LabyMod.getInstance();
        String name = labyMod.getPlayerName();
        UUID uuid = labyMod.getPlayerUUID();
        String id = labyMod.getPlayerId();
        return new CommandSender(name, uuid, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommandSender sender = (CommandSender) o;

        if (!Objects.equals(name, sender.name)) return false;
        if (!Objects.equals(uuid, sender.uuid)) return false;
        return Objects.equals(id, sender.id);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandSender{" +
                "name='" + name + '\'' +
                ", uuid=" + uuid +
                ", id='" + id + '\'' +
                '}';
    }

}
