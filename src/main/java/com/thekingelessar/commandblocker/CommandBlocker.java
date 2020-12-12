package com.thekingelessar.commandblocker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandBlocker extends JavaPlugin
{
    
    // Todo: Reload config, commands?
    
    static public ConsoleCommandSender console = null;
    static public FileConfiguration config = null;
    static public final String commandBlockerPrefix = ChatColor.DARK_PURPLE.toString() + "[CommandBlocker] " + ChatColor.RESET.toString();
    static public BlockedList blockedCommandList;
    static public CommandBlocker instance;
    
    @Override
    public void onEnable()
    {
        instance = this;
        console = Bukkit.getServer().getConsoleSender();
        
        this.getCommand("blocker").setExecutor(new BlockerCommand());
        
        getServer().getPluginManager().registerEvents(new com.thekingelessar.commandblocker.CommandListener(), this);
        this.saveDefaultConfig();
        
        loadConfig();
        super.onEnable();
    }
    
    public static void loadConfig()
    {
        CommandBlocker.instance.reloadConfig();
        config = CommandBlocker.instance.getConfig();
        
        List<?> blockedCommandsSection = config.getList("blocked_commands");
        if (blockedCommandsSection != null)
        {
            List<Map<String, String>> validBlockedCommands = new ArrayList<>();
            for (Object blockedCommandObject : blockedCommandsSection)
            {
                if (blockedCommandObject instanceof Map)
                {
                    Map blockedCommandMap = (Map) blockedCommandObject;
                    for (Object key : blockedCommandMap.keySet()) // Check if each part of the map is valid
                    {
                        Object value = blockedCommandMap.get(key);
                        if (!(key instanceof String && value instanceof String))
                        {
                            console.sendMessage(commandBlockerPrefix + "Error in config file (will ignore this part). Command key: " + key.toString());
                            blockedCommandMap.remove(key);
                        }
                    }
                    
                    validBlockedCommands.add(blockedCommandMap);
                }
            }
            
            blockedCommandList = new BlockedList(validBlockedCommands);
            console.sendMessage(commandBlockerPrefix + "Finished loading config.");
        }
    }
    
    public static void storeConfig()
    {
        List<Map<String, String>> blockedCommandMapList = new ArrayList<>();
        for (BlockedList.BlockedCommand blockedCommand : blockedCommandList.blockedCommands)
        {
            LinkedHashMap<String, String> blockedCommandMap = new LinkedHashMap<>();
            
            blockedCommandMap.put("command", blockedCommand.command);
            blockedCommandMap.put("permission", blockedCommand.permission);
            blockedCommandMap.put("group", blockedCommand.group);
            
            blockedCommandMapList.add(blockedCommandMap);
        }
        
        CommandBlocker.instance.getConfig().set("blocked_commands", blockedCommandMapList);
        CommandBlocker.instance.saveConfig();
    }
    
    @Override
    public void onDisable()
    {
        super.onDisable();
    }
    
}