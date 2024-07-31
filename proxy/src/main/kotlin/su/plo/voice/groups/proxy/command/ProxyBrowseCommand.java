package su.plo.voice.groups.proxy.command;

import org.jetbrains.annotations.NotNull;
import stream.voiceGUI.CommandOverrides.BrowseCommand;
import su.plo.lib.api.proxy.player.MinecraftProxyPlayer;
import su.plo.lib.api.server.command.MinecraftCommandSource;
import su.plo.voice.groups.command.CommandHandler;

public class ProxyBrowseCommand extends BrowseCommand {
    public ProxyBrowseCommand(CommandHandler handler) {
        super(handler);
    }

    @Override
    public void inventoryHook(@NotNull MinecraftCommandSource source, String @NotNull [] arguments) {
        MinecraftProxyPlayer player = (MinecraftProxyPlayer) source;
        //TODO: PACKETS
    }

}
