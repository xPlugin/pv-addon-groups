package su.plo.voice.groups.server

import su.plo.lib.api.server.event.command.ServerCommandsRegisterEvent
import su.plo.voice.api.addon.AddonLoaderScope
import su.plo.voice.api.addon.annotation.Addon
import su.plo.voice.api.event.EventSubscribe
import su.plo.voice.api.server.event.config.VoiceServerConfigReloadedEvent
import su.plo.voice.groups.GroupsAddon
import su.plo.voice.groups.command.CommandHandler

@Addon(id = "pv-addon-groups", scope = AddonLoaderScope.SERVER, version = "1.0.3", authors = ["KPidS"])
class ServerGroupsAddon : GroupsAddon() {

    init {
        ServerCommandsRegisterEvent.registerListener { commandManager, minecraftServer ->
            commandManager.register(
                "groups",
                createCommandHandler(minecraftServer)
                    .also { addSubcommandsToCommandHandler(it) }
            )
        }
    }

    @EventSubscribe
    fun onConfigReloaded(event: VoiceServerConfigReloadedEvent) {
        logger.warn("/vreload not supported")
    }

    override fun hook(commandHandler: CommandHandler) {
        commandHandler.addSubCommand(::ServerBrowseCommand)
    }
}
