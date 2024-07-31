package su.plo.voice.groups.server;

import org.jetbrains.annotations.NotNull;
import stream.voiceGUI.CommandOverrides.BrowseCommand;
import su.plo.lib.api.server.command.MinecraftCommandSource;
import su.plo.voice.groups.command.CommandHandler;

public class ServerBrowseCommand extends BrowseCommand {
    public ServerBrowseCommand(CommandHandler handler) {
        super(handler);
    }

    @Override
    public void inventoryHook(@NotNull MinecraftCommandSource source, String @NotNull [] arguments) {
        System.out.println("Server Hooked");
    }
}
