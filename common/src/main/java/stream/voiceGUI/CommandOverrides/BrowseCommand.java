package stream.voiceGUI.CommandOverrides;

import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import su.plo.lib.api.chat.MinecraftTextClickEvent;
import su.plo.lib.api.chat.MinecraftTextComponent;
import su.plo.lib.api.chat.MinecraftTextHoverEvent;
import su.plo.lib.api.server.command.MinecraftCommandSource;
import su.plo.lib.api.server.permission.PermissionDefault;
import su.plo.voice.groups.command.CommandHandler;
import su.plo.voice.groups.command.SubCommand;
import su.plo.voice.groups.group.Group;
import su.plo.voice.groups.utils.extend.*;

import java.util.ArrayList;
import java.util.List;

public abstract class BrowseCommand extends SubCommand {

    private final List<Pair<String, PermissionDefault>> permission = new ArrayList<>(){{
        add(new Pair<>("browse", PermissionDefault.TRUE));
        add(new Pair<>("browse.all", PermissionDefault.OP));
        add(new Pair<>("browse.*", PermissionDefault.OP));
    }};

    private final CommandHandler handler;

    protected BrowseCommand(CommandHandler handler){
        super(handler);
        this.handler = handler;
    }

    @Override
    public @NotNull List<String> suggest(@NotNull MinecraftCommandSource source, String[] arguments) {
        if (arguments.length == 2) {
            return List.of(handler.getTranslationByKey("pv.addon.groups.arg.page", source));
        }

        return new ArrayList<>();
    }

    @Override
    public void execute(@NotNull MinecraftCommandSource source, String @NotNull [] arguments) {
        if (MinecraftCommandSourceKt.hasAddonPermission(source, "browse")) return;

        var page = Integer.parseInt(arguments[1]);

        List<Group> raw = handler.getGroupManager().getGroups().values().stream()
                .filter(group -> {
                    if (MinecraftCommandSourceKt.hasAddonPermission(source, "browse.all")) {
                        return true;
                    } else if (MinecraftCommandSourceKt.hasAddonPermission(source, "browse.*")) {
                        return true;
                    } else {
                        return group.hasPermission(source);
                    }
                })
                .sorted((g1, g2) -> Integer.compare(g2.getOnlinePlayerCount(), g1.getOnlinePlayerCount()))
                .toList();

        if (raw.isEmpty()) {
            MinecraftCommandSourceKt.printDivider(source);
            MinecraftCommandSourceKt.sendTranslatable(source, "pv.addon.groups.command.browse.error.no_groups");
            MinecraftCommandSourceKt.printDivider(source);
            return;
        }

        List<List<Group>> chunks = new ArrayList<>();
        int perPage = 3;
        for (int i = 0; i < raw.size(); i += perPage) {
            chunks.add(raw.subList(i, Math.min(i + perPage, raw.size())));
        }

        List<Group> chunk = chunks.size() > (page - 1) ? chunks.get(page - 1) : null;
        if (chunk == null) {
            MinecraftCommandSourceKt.sendTranslatable(source, "pv.addon.groups.command.browse.error.page_not_found");
            return;
        }

        MinecraftCommandSourceKt.printDivider(source);
        for (Group group : chunk) {
            MinecraftCommandSourceKt.sendMessage(source, group.asTextComponents(handler, source));
            MinecraftCommandSourceKt.printDivider(source);
        }
        source.sendMessage(getPagesInfo(page, chunks.size()));
        inventoryHook(source, arguments, chunks);
    }

    private MinecraftTextComponent getPagesInfo(
        Integer page,
        Integer chunksSize
    ) {

        MinecraftTextComponent prevButton;
        if (page > 1) {
            var command = "/groups browse ${page - 1}";
            prevButton = MinecraftTextComponent.translatable("pv.addon.groups.button.prev")
                .append(MinecraftTextComponent.literal(" "))
                .hoverEvent(MinecraftTextHoverEvent.showText(MinecraftTextComponent.literal(command)))
                .clickEvent(MinecraftTextClickEvent.runCommand(command));
        } else {
            prevButton = MinecraftTextComponent.empty();
        }

        var pageInfo = MinecraftTextComponent.translatable("pv.addon.groups.format.page", page, chunksSize);

        MinecraftTextComponent nextButton;
        if (page < chunksSize) {
            var command = "/groups browse ${page + 1}";
            nextButton = MinecraftTextComponent.literal(" ")
                .append(MinecraftTextComponent.translatable("pv.addon.groups.button.next"))
                .hoverEvent(MinecraftTextHoverEvent.showText(MinecraftTextComponent.literal(command)))
                .clickEvent(MinecraftTextClickEvent.runCommand(command));
        } else {
            nextButton = MinecraftTextComponent.empty();
        }

        return prevButton
            .append(pageInfo)
            .append(nextButton);
    }

    @Override
    public boolean checkCanExecute(@NotNull MinecraftCommandSource source) {
        return MinecraftCommandSourceKt.hasAddonPermission(source, "browse");
    }

    @NotNull
    @Override
    public String getName() {
        return "browse";
    }

    @NotNull
    @Override
    public List<Pair<String, PermissionDefault>> getPermissions() {
        return permission;
    }

    public abstract void inventoryHook(@NotNull MinecraftCommandSource source, String @NotNull [] arguments, List<List<Group>> chunked);
}