package eu.mikroskeem.orion.fixaac;

import com.google.common.eventbus.Subscribe;
import eu.mikroskeem.orion.api.Orion;
import eu.mikroskeem.orion.api.annotations.OrionMod;
import eu.mikroskeem.orion.api.events.ModConstructEvent;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;


/**
 * AAC plugin Fix for Orion
 *
 * @author Mark Vainomaa
 */
@OrionMod(id = "fixaac")
public final class FixAAC {
    @Inject private Logger logger;
    @Inject private Orion orion;

    @Subscribe
    public void on(ModConstructEvent e) throws Exception {
        logger.info("Patching PluginClassLoader#getPackage(name) to fix AAC plugin");
        orion.registerMixinConfig("mixins.fixaac.json");
    }
}
