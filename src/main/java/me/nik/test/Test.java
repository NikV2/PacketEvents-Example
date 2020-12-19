package me.nik.test;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.event.PacketListenerDynamic;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityteleport.WrappedPacketOutEntityTeleport;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Test extends JavaPlugin {

    @Override
    public void onLoad() {
        PacketEvents.create(this).getSettings().backupServerVersion(ServerVersion.v_1_8_8).checkForUpdates(true);

        PacketEvents.get().load();
    }

    @Override
    public void onEnable() {
        PacketEvents.get().init(this);

        PacketEvents.get().registerListener(new PacketListenerDynamic() {
            @Override
            public void onPacketPlayReceive(PacketPlayReceiveEvent e) {
                Player p = e.getPlayer();

                switch (e.getPacketId()) {
                    case PacketType.Play.Client.ARM_ANIMATION:
                        e.getPlayer().sendMessage("YOU HAVE SWUNG YOUR ARM!");
                        break;
                    case PacketType.Play.Client.CHAT:
                        WrappedPacketOutEntityTeleport teleport = new WrappedPacketOutEntityTeleport(p.getEntityId(), p.getLocation().add(0, 30, 0), false);
                        PacketEvents.get().getPlayerUtils().sendPacket(p, teleport);
                        break;
                }
            }
        });
    }

    @Override
    public void onDisable() {
        PacketEvents.get().stop();
    }
}