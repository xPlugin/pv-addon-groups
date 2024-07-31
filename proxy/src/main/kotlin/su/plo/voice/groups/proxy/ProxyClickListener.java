package su.plo.voice.groups.proxy;

import dev.simplix.protocolize.api.Direction;
import dev.simplix.protocolize.api.listener.AbstractPacketListener;
import dev.simplix.protocolize.api.listener.PacketReceiveEvent;
import dev.simplix.protocolize.api.listener.PacketSendEvent;
import dev.simplix.protocolize.data.packets.ClickWindow;

public class ProxyClickListener extends AbstractPacketListener<ClickWindow> {
    public ProxyClickListener() {
        super(ClickWindow.class, Direction.UPSTREAM, 0);
    }

    @Override
    public void packetReceive(PacketReceiveEvent<ClickWindow> packetReceiveEvent) {
        System.out.println("got upstream Click");
    }

    @Override
    public void packetSend(PacketSendEvent<ClickWindow> packetSendEvent) {

    }
}
