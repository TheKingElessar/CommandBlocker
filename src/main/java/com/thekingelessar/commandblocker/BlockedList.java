package com.thekingelessar.commandblocker;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockedList
{
    List<BlockedCommand> blockedCommands = new ArrayList<>();
    
    public BlockedList(List<Map<String, String>> validBlockedCommands)
    {
        for (Map<String, String> commandData : validBlockedCommands)
        {
            BlockedCommand command = new BlockedCommand(commandData);
            blockedCommands.add(command);
        }
    }
    
    public void addCommand(String commandName, String permission, String group) {
        Map<String, String> commandMap = new HashMap<>();
        commandMap.put("command", commandName);
        commandMap.put("permission", permission);
        commandMap.put("group", group);
        
        BlockedCommand command = new BlockedCommand(commandMap);
        this.blockedCommands.add(command);
    }
    
    public static boolean isPlayerInGroup(Player player, String group)
    {
        return player.hasPermission("group." + group);
    }
    
    public boolean playerHasPerms(Player player, String command)
    {
        command = command.toLowerCase();
        for (BlockedCommand currentCommand : blockedCommands)
        {
            if (currentCommand.command.equals(command))
            {
   
                boolean failedPerm = false;
                boolean failedGroup = false;
                
                if (currentCommand.permission != null)
                {
                    if (player.hasPermission(currentCommand.permission))
                    {
                        return true;
                    } else {
                        failedPerm = true;
                    }
                } else {
                    failedPerm = true;
                }
                
                if (currentCommand.group != null)
                {
                    if (isPlayerInGroup(player, currentCommand.group))
                    {
                        return true;
                    } else {
                        failedGroup = true;
                    }
                } else {
                    failedGroup = true;
                }
                
                if (failedGroup && failedPerm) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public BlockedCommand getCommand(String commandQuery)
    {
        for (BlockedCommand command : blockedCommands)
        {
            if (command.command.equals(commandQuery))
            {
                return command;
            }
        }
        
        return null;
    }
    
    public void printList()
    {
        for (BlockedCommand command : blockedCommands)
        {
            String output = "";
            output += command.command + ":";
            if (command.permission != null) output += "\n    " + command.permission;
            if (command.group != null) output += "\n    " + command.group;
            
            CommandBlocker.console.sendMessage(CommandBlocker.commandBlockerPrefix + "\n" + output);
        }
    }
    
    class BlockedCommand
    {
        String command;
        String permission;
        String group;
        
        private BlockedCommand(Map<String, String> commandData)
        {
            command = commandData.get("command").toLowerCase();
            CommandBlocker.console.sendMessage(CommandBlocker.commandBlockerPrefix + "Registering blocked command: " + command);
            if (commandData.containsKey("permission"))
            {
                permission = commandData.get("permission").toLowerCase();
            }
            if (commandData.containsKey("group"))
            {
                group = commandData.get("group").toLowerCase();
            }
        }
    }
}