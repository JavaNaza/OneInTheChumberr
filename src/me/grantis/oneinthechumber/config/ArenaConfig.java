package me.grantis.oneinthechumber.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ArenaConfig {

    private YamlConfiguration config;
    private File file;
    private Plugin plugin;

    public ArenaConfig(Plugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "arenas.yml");
        this.config = new YamlConfiguration();
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public File getFile() {
        return this.file;
    }

    public YamlConfiguration getConfig() {
        return this.config;
    }

    public void load() {

        try {
            if (!this.file.exists()) {
                this.file.getParentFile().mkdirs();
                this.writeToFile(this.file.getName(), this.file);
            }

            this.config.load(this.file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        this.file.delete();
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeToFile(String source, File destination) {
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = this.getResource(source);
            os = new FileOutputStream(destination);

            byte[] buffer = new byte[1024];
            int i = 0;
            while((i = is.read(buffer)) != -1) {
                os.write(buffer, 0, i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {}
        }
    }

    public InputStream getResource(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        } else {
            try {
                URL url = this.getClass().getClassLoader().getResource(filename);
                if (url == null) {
                    return null;
                } else {
                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(false);
                    return connection.getInputStream();
                }
            } catch (IOException var4) {
                return null;
            }
        }
    }

}