package su.plo.voice.groups.server;

import org.jetbrains.annotations.NotNull;
import stream.voiceGUI.CommandOverrides.BrowseCommand;
import su.plo.lib.api.server.command.MinecraftCommandSource;
import su.plo.lib.api.server.player.MinecraftServerPlayer;
import su.plo.voice.groups.command.CommandHandler;
import su.plo.voice.groups.group.Group;

import java.util.List;

public class ServerBrowseCommand extends BrowseCommand {
    public ServerBrowseCommand(CommandHandler handler) {
        super(handler);
    }

    @Override
    public void inventoryHook(@NotNull MinecraftCommandSource source, String @NotNull [] arguments, List<List<Group>> chunks) {
        MinecraftServerPlayer player = (MinecraftServerPlayer) source;
        //TODO: PACKETS

    }
}
