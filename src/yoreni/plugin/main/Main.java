package yoreni.plugin.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener
{
	
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable()
	{

	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player) sender;
		if(label.equalsIgnoreCase("buildpk"))
		{
			if(!player.hasPermission("lazyparkour.buildpk")) return true;
			if(args.length > 0)
			{	
				int jumps = Integer.parseInt(args[0]);
				
				if(jumps > 200)
				{
					player.sendMessage("the cap of parkour length is at 200");
					return true;
				}
				
				double x = player.getLocation().getX();
				double y = player.getLocation().getY() - 1;
				double z = player.getLocation().getZ();
				World world = player.getLocation().getWorld();
				
				int dir = 0;
				
				float yaw = player.getLocation().getYaw();
				if(yaw >= 45 && yaw < 135) dir = 0;          //0 = -X
				else if(yaw >= 135 && yaw < 225) dir = 1;    //1 = -Z
				else if(yaw >= 225 && yaw < 315) dir = 2;    //2 = +X
				else dir = 3;                                //3 = +Z
				
				Parkour parkour = new Parkour(world,x,y,z,dir,jumps);
				parkour.setBlock(world,x,y,z,Material.STONE);
				parkour.build();
				
				return true;
			}
		}
		return false;
	}
}