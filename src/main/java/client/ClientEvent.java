package client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ClientEvent {

    @SubscribeEvent
    public void clientStartReadingPackage(FMLNetworkEvent.ClientConnectedToServerEvent event){
        ClientSocketReader.startReading();

    }
    public static void playerSoundUpSphere(EntityPlayer player){
        player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
    }

}
