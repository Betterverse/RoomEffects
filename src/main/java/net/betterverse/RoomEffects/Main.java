package net.betterverse.RoomEffects;

import java.util.HashMap;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    public static Configuration config;
    public static HashMap<String, String> weatherRoomOpts = new HashMap<>();
    private boolean weatherRoomEnabled;
    
    @Override
    public void onEnable() {
        config = this.getConfig();
        loadConfig();
        if (weatherRoomEnabled) {
            this.getServer().getPluginManager().registerEvents(new WeatherRoom(), this);
        }
    }
    
    public void loadConfig() {
        //Weather room options
        weatherRoomOpts.put("sign_invalid_message", config.getString("weatherRoom.sign_invalid_message", "&cSign is invalid."));
        weatherRoomOpts.put("room_invalid_message", config.getString("weatherRoom.room_invalid_message", "&cRoom doesn't meet required specs"));
        weatherRoomOpts.put("success_message", config.getString("weatherRoom.success_message", "&eSign function executed"));
        config.set("weatherRoom.enabled",  weatherRoomEnabled = config.getBoolean("weatherRoom.enabled", true));
        config.set("weatherRoom.sign_invalid_message", weatherRoomOpts.get("sign_invalid_message"));
        config.set("weatherRoom.room_invalid_message", weatherRoomOpts.get("room_invalid_message"));
        config.set("weatherRoom.success_message", weatherRoomOpts.get("success_message"));
        this.saveConfig();
    }
    
    public static String colorize(String str) {
        return str.replaceAll("(?i)&([a-fk-or0-9])", "\u00A7$1");
    }
    
}
