package su.plo.voice.groups.proxy.command;

import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.chat.ChatElement;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.inventory.InventoryType;
import dev.simplix.protocolize.data.packets.OpenWindow;
import dev.simplix.protocolize.data.packets.WindowItems;
import org.jetbrains.annotations.NotNull;
import stream.voiceGUI.CommandOverrides.BrowseCommand;
import su.plo.lib.api.proxy.player.MinecraftProxyPlayer;
import su.plo.lib.api.server.command.MinecraftCommandSource;
import su.plo.voice.groups.command.CommandHandler;
import su.plo.voice.groups.group.Group;

import java.util.ArrayList;
import java.util.List;

public class ProxyBrowseCommand extends BrowseCommand {
    public ProxyBrowseCommand(CommandHandler handler) {
        super(handler);
    }

    @Override
    public void inventoryHook(@NotNull MinecraftCommandSource source, String @NotNull [] arguments, List<List<Group>> chunks) {
        ProtocolizePlayer player = Protocolize.playerProvider().player(((MinecraftProxyPlayer) source).getUUID());
        player.sendPacket(new OpenWindow(10, InventoryType.GENERIC_9X6, ChatElement.of("test")));
        player.sendPacket(new WindowItems((short) 10, new ArrayList<>(), 1));
        //TODO: LAYOUT
    }

}
