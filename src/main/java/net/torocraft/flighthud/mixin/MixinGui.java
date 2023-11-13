package net.torocraft.flighthud.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.torocraft.flighthud.common.HudRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeGui.class)
public abstract class MixinGui {
    @Shadow(remap = false)
    public abstract Minecraft getMinecraft();

    @Unique
    private final HudRenderer flighthud$hud = new HudRenderer();

    @Inject(method = "render", at = @At("RETURN"))
    private void render(GuiGraphics context, float tickDelta, CallbackInfo ci) {
        flighthud$hud.render(context, tickDelta, getMinecraft());
    }
}