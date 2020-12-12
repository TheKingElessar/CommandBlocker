package com.thekingelessar.commandblocker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

// I'm so sorry for the class names. I should have thought about the consequences of the plugin's name.
public class BlockerCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args == null || args.length == 0) return false;
        
        switch (args[0])
        {
            case "reload":
                if (sender.hasPermission("blocker.reload"))
                {
                    CommandBlocker.loadConfig();
                    sender.sendMessage(CommandBlocker.commandBlockerPrefix + "Reloaded config!");
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                }
                return true;
            
            case "add":
                if (sender.hasPermission("blocker.add"))
                {
                    String addedCommand = args[1];
                    String addedPermission = args[2];
                    if(addedPermission.equals("none")) addedPermission = null;
                    String addedGroup = args[3];
    
                    sender.sendMessage(CommandBlocker.commandBlockerPrefix + "Added command " + ChatColor.YELLOW + addedCommand + ChatColor.RESET + "! Will be stored in newest version of config.");
    
                    CommandBlocker.loadConfig();
                    CommandBlocker.blockedCommandList.addCommand(addedCommand, addedPermission, addedGroup);
                    CommandBlocker.storeConfig();
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                }
                return true;
            
            default:
                return false;
        }
    }
}