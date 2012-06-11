package net.betterverse.RoomEffects;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class WeatherRoom extends Scanner implements Listener {
    
    public WeatherRoom() {
        this.blocks.put(Material.IRON_BLOCK, 4);
        this.blocks.put(Material.GOLD_BLOCK, 8);
        this.blocks.put(Material.DIAMOND_BLOCK, 8);
        this.limit = 12;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getState() instanceof Sign) {
                //This room's location must be at least Y 80
                if (e.getClickedBlock().getY() < 80) return;
                Sign sign = (Sign) e.getClickedBlock().getState();
                if (sign.getLine(0).equalsIgnoreCase("[WeatherRoom]")) {
                    if (this.checkRoom(e.getClickedBlock())) {
                        World w = e.getClickedBlock().getWorld();
                        if (sign.getLine(1).equalsIgnoreCase("[Night]")) {
                            w.setTime(14000);
                        } else if (sign.getLine(1).equalsIgnoreCase("[Day]")) {
                            w.setTime(0);
                        } else if (sign.getLine(1).equalsIgnoreCase("[Rain]")) {
                            w.setStorm(true);
                        } else if (sign.getLine(1).equalsIgnoreCase("[NoRain]")) {
                            w.setStorm(false);
                        } else if (sign.getLine(1).equalsIgnoreCase("[Thunder]")) {
                            w.setThundering(true);
                            w.setStorm(true);
                        } else {
                            e.getPlayer().sendMessage(Main.colorize(Main.weatherRoomOpts.get("sign_invalid_message")));
                            return;
                        }

                        e.getPlayer().sendMessage(Main.colorize(Main.weatherRoomOpts.get("success_message")));
                    } else {
                        e.getPlayer().sendMessage(Main.colorize(Main.weatherRoomOpts.get("room_invalid_message")));
                    }
                }
            }
        }
    }
    
}
