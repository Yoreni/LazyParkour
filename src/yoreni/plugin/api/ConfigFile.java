package yoreni.plugin.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import yoreni.plugin.main.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigFile 
{
	FileConfiguration config = null;
	File file = null;

	Main Main;

	public ConfigFile(Main main) 
	{
		this.Main = main;
	}

	public void setup(String path)
	{
		if (!Main.getDataFolder().exists()) 
		{
			Main.getDataFolder().mkdirs();
		}
		file = new File(Main.getDataFolder(),path + ".yml");
		if(!file.exists())
		{
			try 
			{
				file.createNewFile();
				InputStream stream =  getClass().getResourceAsStream("/" + path + ".yml");
				copyFile(stream,file);
			} 
			catch (Exception Exception) 
			{
				Exception.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
	}

	private void save()
	{
		try 
		{
			config.save(file);
		} 
		catch (IOException IOException) 
		{
			IOException.printStackTrace();
		}
	}

	public void addSetting(String x,Object v)
	{
		if(!config.isSet(x))
		{
			config.set(x,v);
			save();
		}
	}

	public void set(String s,Object x)
	{
		config.set(s,x);
		save();
	}

	public void changeDouble(String s,double x)
	{
		config.set(s,config.getDouble(s) + x);
		save();
	}

	public void changeInt(String s,int x)
	{
		config.set(s,config.getInt(s) + x);
		save();
	}

	public void changeLong(String s,long x)
	{
		config.set(s,config.getLong(s) + x);
		save();
	}

	public double getDouble(String s)
	{
		return config.getDouble(s);
	}

	public int getInt(String s)
	{
		return config.getInt(s);
	}

	public long getLong(String s)
	{
		return config.getLong(s);
	}

	public String getString(String s)
	{
		return config.getString(s);
	}
	
	public boolean getBoolean(String s)
	{
		return config.getBoolean(s);
	}

	public boolean isSet(String s)
	{
		return config.isSet(s);
	}

	public static void copyFile(InputStream in, File out) throws Exception 
	{ 
		// https://bukkit.org/threads/extracting-file-from-jar.16962/
		InputStream fis = in;
		FileOutputStream fos = new FileOutputStream(out);
		try {
			byte[] buf = new byte[1024];
			int i = 0;
			while((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		}catch(Exception e) {
			throw e;
		}finally {
			if(fis != null) {
				fis.close();
			}
			if(fos != null) {
				fos.close();
			}
		}
	}
}
