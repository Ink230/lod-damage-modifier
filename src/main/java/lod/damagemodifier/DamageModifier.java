package lod.damagemodifier;

import com.github.slugify.Slugify;
import legend.core.GameEngine;
import legend.game.combat.bent.AttackEvent;
import legend.game.combat.bent.PlayerBattleEntity;
import legend.game.combat.types.AttackType;
import legend.game.modding.events.battle.MonsterStatsEvent;
import lod.damagemodifier.config.DamageModifierConfig;
import org.legendofdragoon.modloader.events.EventListener;
import org.legendofdragoon.modloader.registries.RegistryId;
import org.legendofdragoon.modloader.Mod;
import legend.game.modding.events.characters.CharacterStatsEvent;
import legend.game.modding.events.gamestate.GameLoadedEvent;

import java.util.Random;

@Mod(id = DamageModifier.MOD_ID)
public class DamageModifier {
    public static final String MOD_ID = "damage-modifier";
    private static final Slugify slug = Slugify.builder().underscoreSeparator(true).customReplacement("'", "").customReplacement("-", "_").build();
    public static RegistryId id(final String entryId) {
        return new RegistryId(MOD_ID, entryId);
    }

    private final DamageModifierConfig _config = DamageModifierConfig.getInstance();

    public DamageModifier() {
        GameEngine.EVENTS.register(this);
    }

    @EventListener
    public void gameLoaded(final GameLoadedEvent game) { }

    @EventListener
    public void DamageEvent(final AttackEvent event) {
        if (!(event.attacker instanceof PlayerBattleEntity)) return;

        if (_config.itemMagicOnly && event.attackType != AttackType.ITEM_MAGIC) return;

        var randomizer = new Random();
        event.damage = Math.round((event.damage * randomizer.nextInt(_config.damageLower, _config.damageUpper) / 100));
    }
}
