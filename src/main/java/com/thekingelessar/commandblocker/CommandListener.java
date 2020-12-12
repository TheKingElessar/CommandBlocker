package com.thekingelessar.commandblocker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener
{
    
    private static String deleteCharAt(String strValue, int index)
    {
        return strValue.substring(0, index) + strValue.substring(index + 1);
    }
    
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event)
    {
        String cleanedCommand = deleteCharAt(event.getMessage(), 0);
        String[] splitCommand = cleanedCommand.split(" ");
        String baseCommand = splitCommand[0];
        
        boolean playerHasPerms = CommandBlocker.blockedCommandList.playerHasPerms(event.getPlayer(), baseCommand);
    
        if(!playerHasPerms) {
            event.setCancelled(true);
            CommandBlocker.console.sendMessage(CommandBlocker.commandBlockerPrefix + "Blocked command: " + splitCommand[0]);
        }
    }
}
