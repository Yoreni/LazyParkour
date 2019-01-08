package yoreni.plugin.main;

import java.security.SecureRandom;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Ladder;

public class Parkour 
{
	double x = 0;
	double y = 0;
	double z = 0;
	World world = null;
	int dir = 0;
	int lastTurn = 0;
	int jumps = 0;
	
	SecureRandom random = new SecureRandom();
	
	public Parkour(World world,double x,double y,double z,int dir,int jumps)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		this.dir = dir;
		this.jumps = jumps;
	}
	
	public void build()
	{
		for(int a = 0 ; a < jumps ; a++)
		{
			makeJump();
			if(pkSensor() < 5)
			{
				int times = 0;
				do
				{
					int turn = (int) (Math.random() * 2);
					if(turn == 0) turnLeft();
					else turnRight();
					times++;
					if(times == 9) break;
				}
				while(pkSensor() < 5);
			}
			else if(lastTurn >= 2)
			{
				int random = (int) (Math.random() * 100);
				if(random < 5) 
				{
					turnLeft();
					lastTurn = 0;
				}
				else if(random > 95) 
				{
					turnRight();
					lastTurn = 0;
				}
			}
			
			lastTurn++;
		}
	}
	
	private void turnLeft()
	{
		if(dir == 0) dir = 3;
		else dir--;
	}
	
	private void turnRight()
	{
		if(dir == 3) dir = 0;
		else dir++;
	}
	
	private void move(int front,int up,int side)
	{
		y += up;
		if(dir == 0)
		{
			x -= front;
			z += side;
		}
		else if(dir == 1)
		{
			z -= front;
			x += side;
		}
		else if(dir == 2)
		{
			x += front;
			z += side;
		}
		else if(dir == 3)
		{
			z += front;
			x += side;
		}
	}
	
	private void makeJump()
	{
		int pick = random.nextInt(259);
		String[] pos = {"left","right"};
		if(pick <= 50) //50
		{
			move(3,0,random.nextInt(7) - 3);
			setBlock(world,x,y,z,Material.STONE);
		}
		else if(pick <= 75) //25
		{
			move(4,0,random.nextInt(5) - 2);
			setBlock(world,x,y,z,Material.STONE);
		}
		else if(pick <= 80) //5
		{
			move(5,0,random.nextInt(3) - 1);
			setBlock(world,x,y,z,Material.STONE);
  	    }
		else if(pick <= 130) //50
		{
            move(2,1,random.nextInt(3) - 1);
			setBlock(world,x,y,z,Material.STONE);
		}
		else if(pick <= 150) //20
		{
			move(3,1,random.nextInt(3) - 1);
			setBlock(world,x,y,z,Material.STONE);
		}
		else if(pick <= 155) //5
		{
			move(4,1,0);
			setBlock(world,x,y,z,Material.STONE);
		}
		else if(pick <= 185) //30
		{
			move(3,-1,random.nextInt(7) - 3);
			setBlock(world,x,y,z,Material.STONE);
		}
		else if(pick <= 205) // 20
		{
			move(4,-1,random.nextInt(7) - 3);
			setBlock(world,x,y,z,Material.STONE);
	    }
		else if(pick <= 215) // 10
		{
			move(5,-1,random.nextInt(7) - 3);
			setBlock(world,x,y,z,Material.STONE);
		}
		else if(pick <= 230) // 15
		{
			move(5,0,0);
			setBlock(world,x,y,z,Material.STONE);
			setLadder(world,x,y,z,"front");
		}
		else if(pick <= 235) // 5
		{
			move(3,2,0);
			setBlock(world,x,y,z,Material.STONE);
			setLadder(world,x,y,z,pos[random.nextInt(2)]);
		}
		else if(pick <= 255) // 20
		{
			move(2,2,0);
			setBlock(world,x,y,z,Material.STONE);
			setLadder(world,x,y,z,pos[random.nextInt(2)]);
		}
		else if(pick <= 257) //2
		{
			move(1,0,0);
			setBlock(world,x,y,z,Material.STONE);
			move(0,1,0);
			setBlock(world,x,y,z,Material.STONE);
			move(0,1,0);
			setBlock(world,x,y,z,Material.STONE);
			move(1,-2,0);
			setBlock(world,x,y,z,Material.STONE);
		}
		else if(pick <= 258) //1
		{
			move(1,0,0);
			setBlock(world,x,y,z,Material.STONE);
			move(0,1,0);
			setBlock(world,x,y,z,Material.STONE);
			move(0,1,0);
			setBlock(world,x,y,z,Material.STONE);
			move(1,-2,0);
			setBlock(world,x,y,z,Material.STONE);
			move(0,1,0);
			setBlock(world,x,y,z,Material.STONE);
			move(0,1,0);
			setBlock(world,x,y,z,Material.STONE);
			move(1,-2,0);
			setBlock(world,x,y,z,Material.STONE);
		}
	}
	
	private void setLadder(World world,double x,double y,double z,String place)
	{
		Ladder ladder = new Ladder();
		BlockState block = null;
		if(place.equals("front"))
		{
			if(dir == 0)
			{
				world.getBlockAt((int) x + 1,(int) y,(int) z).setType(Material.LADDER);
				block = world.getBlockAt((int) x + 1,(int) y,(int) z).getState();
				ladder.setFacingDirection(BlockFace.WEST);
			}
			else if(dir == 1)
			{
				world.getBlockAt((int) x,(int) y,(int) z + 1).setType(Material.LADDER);
				block = world.getBlockAt((int) x,(int) y,(int) z + 1).getState();
				ladder.setFacingDirection(BlockFace.NORTH);
			}
			else if(dir == 2)
			{
				world.getBlockAt((int) x - 1,(int) y,(int) z).setType(Material.LADDER);
				block = world.getBlockAt((int) x - 1,(int) y,(int) z).getState();
				ladder.setFacingDirection(BlockFace.EAST);
			}
			else if(dir == 3)
			{
				world.getBlockAt((int) x,(int) y,(int) z - 1).setType(Material.LADDER);
				block = world.getBlockAt((int) x,(int) y,(int) z - 1).getState();
				ladder.setFacingDirection(BlockFace.SOUTH);
			}
		}
		else if(place.equals("left"))
		{
			if(dir == 0)
			{
				world.getBlockAt((int) x,(int) y,(int) z + 1).setType(Material.LADDER);
				block = world.getBlockAt((int) x,(int) y,(int) z + 1).getState(); // DONE
				ladder.setFacingDirection(BlockFace.NORTH);
			}
			else if(dir == 1)
			{
				world.getBlockAt((int) x - 1,(int) y,(int) z).setType(Material.LADDER);
				block = world.getBlockAt((int) x - 1,(int) y,(int) z).getState();  //done
				ladder.setFacingDirection(BlockFace.EAST);
			}
			else if(dir == 2)
			{
				world.getBlockAt((int) x,(int) y,(int) z - 1).setType(Material.LADDER);
				block = world.getBlockAt((int) x,(int) y,(int) z - 1).getState(); //done
				ladder.setFacingDirection(BlockFace.SOUTH);
			}
			else if(dir == 3)
			{
				world.getBlockAt((int) x + 1,(int) y,(int) z).setType(Material.LADDER);
				block = world.getBlockAt((int) x + 1,(int) y,(int) z).getState();
				ladder.setFacingDirection(BlockFace.WEST);
			}
		}
		else if(place.equals("right"))
		{
			if(dir == 0)
			{
				world.getBlockAt((int) x,(int) y,(int) z - 1).setType(Material.LADDER);
				block = world.getBlockAt((int) x,(int) y,(int) z - 1).getState(); // DONE
				ladder.setFacingDirection(BlockFace.SOUTH);
			}
			else if(dir == 1)
			{
				world.getBlockAt((int) x + 1,(int) y,(int) z).setType(Material.LADDER);
				block = world.getBlockAt((int) x + 1,(int) y,(int) z).getState();  //done
				ladder.setFacingDirection(BlockFace.WEST);
			}
			else if(dir == 2)
			{
				world.getBlockAt((int) x,(int) y,(int) z + 1).setType(Material.LADDER);
				block = world.getBlockAt((int) x,(int) y,(int) z + 1).getState(); //done
				ladder.setFacingDirection(BlockFace.NORTH);
			}
			else if(dir == 3)
			{
				world.getBlockAt((int) x - 1,(int) y,(int) z).setType(Material.LADDER);
				block = world.getBlockAt((int) x - 1,(int) y,(int) z).getState();
				ladder.setFacingDirection(BlockFace.EAST);
			}
		}
		block.setData(ladder);
		block.update();
	}
	
	private int pkSensor()
	{
		int x1 = (int) x;
		int y1 = (int) y;
		int z1 = (int) z;
		
		int x2 = x1;
		int y2 = y1;
		int z2 = z1;
		
		int output = 0;
		
		for(int a = 0 ; a < 6 ; a++)
		{
			boolean quit = false;
			y2 = y1 - 1;
			
			if(dir == 0) x1--;
			if(dir == 1) z1--;
			if(dir == 2) x1++;
			if(dir == 3) z1++;
			
			for(int b = 0 ; b < 6 ; b++)
			{
				if(world.getBlockAt(x1,y2,z1).getType() != Material.AIR) quit = true;
			}
			if(quit) break;
			output++;
		}
		return output;
	}
	
	public void setBlock(World world,double x,double y,double z,Material material)
	{
		world.getBlockAt((int) x,(int) y,(int) z).setType(material);
	}
}
