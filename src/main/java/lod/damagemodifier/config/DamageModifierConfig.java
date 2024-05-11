package lod.damagemodifier.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class DamageModifierConfig {
    private static final DamageModifierConfig INSTANCE = new DamageModifierConfig();
    public static DamageModifierConfig getInstance() {
        return INSTANCE;
    }


    public final String externalConfigLoadPath = "./mods/damage-modifier/config.yaml";
    public final int damageBase;
    public final int damageUpper;
    public final int damageLower;
    public final boolean itemMagicOnly;

    private DamageModifierConfig() {
        File configFile = new File(externalConfigLoadPath);

        Map<String, Object> yamlConfig;
        try (InputStream inputStream = new FileInputStream(configFile)) {
            Yaml yaml = new Yaml();
            yamlConfig = yaml.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            yamlConfig = Map.of();
        }

        this.damageBase = (int) yamlConfig.getOrDefault("damageBase", 100);
        this.damageUpper = (int) yamlConfig.getOrDefault("damageUpper", 100);
        this.damageLower = (int) yamlConfig.getOrDefault("damageLower", 100);
        this.itemMagicOnly = (boolean) yamlConfig.getOrDefault("itemMagicOnly", true);
    }
}
