package net.torocraft.flighthud.client.event.handler;

import net.minecraft.commands.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.torocraft.flighthud.client.key.KeyBindings;
import net.torocraft.flighthud.common.config.SettingsConfig;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ForgeClientEventHandler {
    @SubscribeEvent
    public static void onKey(InputEvent.Key event) {
        if (KeyBindings.toggleMode.consumeClick()) SettingsConfig.toggle();
    }

    @SubscribeEvent
    public static void registerClientCmd(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(Commands
                .literal("flighthud")
                .then(Commands.literal("toggle")
                        .executes(ctx -> {
                            SettingsConfig.toggle();
                            return 0;
                        })
                )
        );
    }
}
