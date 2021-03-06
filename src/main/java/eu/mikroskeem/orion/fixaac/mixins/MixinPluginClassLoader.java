package eu.mikroskeem.orion.fixaac.mixins;

import eu.mikroskeem.shuriken.common.SneakyThrow;
import eu.mikroskeem.shuriken.reflect.Reflect;
import eu.mikroskeem.shuriken.reflect.wrappers.TypeWrapper;
import org.bukkit.plugin.java.PluginClassLoader;
import org.spongepowered.asm.mixin.Mixin;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;
import java.util.zip.ZipFile;


/**
 * @author Mark Vainomaa
 */
@Mixin(value = PluginClassLoader.class, remap = false)
public abstract class MixinPluginClassLoader extends URLClassLoader {
    public MixinPluginClassLoader(URL[] urls) { super(urls); }

    /* Store generated package objects here */
    private final Map<String, Package> fixaac$generatedPackages = new HashMap<>();

    @Override
    protected Package getPackage(String name) {
        /* Uhh, let's work around LaunchWrapper quirk */
        switch (name){
            case "me.konsolas.aac":
                return fixaac$generatedPackages.computeIfAbsent(name, key -> {
                    try {
                        URL jarUrl = getURLs()[0];
                        File pluginFile = new File(jarUrl.getFile());
                        try(
                                ZipFile zf = new ZipFile(pluginFile);
                                InputStream mfIs = zf.getInputStream(zf.getEntry("META-INF/MANIFEST.MF"))
                        ) {
                            Manifest manifest = new Manifest(mfIs);
                            return Reflect
                                    .wrapClass(Package.class)
                                    .construct(
                                            TypeWrapper.of(name),
                                            TypeWrapper.of(manifest),
                                            TypeWrapper.of(jarUrl),
                                            TypeWrapper.of(ClassLoader.class, this)
                                    )
                                    .getClassInstance();
                        }
                    } catch (Exception e){
                        SneakyThrow.throwException(e);
                    }
                    return null;
                });
            default:
                return super.getPackage(name);
        }
    }
}
