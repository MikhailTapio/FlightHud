package net.torocraft.flighthud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.torocraft.flighthud.components.*;
import net.torocraft.flighthud.config.SettingsConfig.DisplayMode;

public class HudRenderer extends HudComponent {

  private final Dimensions dim = new Dimensions();
  private final FlightComputer computer = new FlightComputer();
  private static final String FULL = DisplayMode.FULL.toString();
  private static final String MIN = DisplayMode.MIN.toString();

  private final HudComponent[] components =
      new HudComponent[] {new FlightPathIndicator(computer, dim), new LocationIndicator(dim),
          new HeadingIndicator(computer, dim), new SpeedIndicator(computer, dim),
          new AltitudeIndicator(computer, dim), new PitchIndicator(computer, dim),
          new ElytraHealthIndicator(computer, dim)};

  private void setupConfig(MinecraftClient client) {
    HudComponent.CONFIG = null;
    if (client.player != null && client.player.isFallFlying()) {
      if (FlightHud.CONFIG_SETTINGS.displayModeWhenFlying.equals(FULL)) {
        HudComponent.CONFIG = FlightHud.CONFIG_FULL;
      } else if (FlightHud.CONFIG_SETTINGS.displayModeWhenFlying.equals(MIN)) {
        HudComponent.CONFIG = FlightHud.CONFIG_MIN;
      }
    } else {
      if (FlightHud.CONFIG_SETTINGS.displayModeWhenNotFlying.equals(FULL)) {
        HudComponent.CONFIG = FlightHud.CONFIG_FULL;
      } else if (FlightHud.CONFIG_SETTINGS.displayModeWhenNotFlying.equals(MIN)) {
        HudComponent.CONFIG = FlightHud.CONFIG_MIN;
      }
    }
  }

  @Override
  public void render(DrawContext ctx, float partial, MinecraftClient client) {
    setupConfig(client);

    if (HudComponent.CONFIG == null) {
      return;
    }

    final MatrixStack m = ctx.getMatrices();
    try {
      m.push();

      if (HudComponent.CONFIG.scale != 1d) {
        float scale = 1 / HudComponent.CONFIG.scale;
        m.scale(scale, scale, scale);
      }

      computer.update(client, partial);
      dim.update(client);

      for (HudComponent component : components) {
        component.render(ctx, partial, client);
      }
      m.pop();
    } catch (Exception e) {
      FlightHud.LOGGER.error("Failed to render FlightHud", e);
    }
  }
}
