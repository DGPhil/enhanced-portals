package uk.co.shadeddimensions.ep3.lib;

import java.io.InputStream;
import java.util.Properties;

import net.minecraft.creativetab.CreativeTabs;
import uk.co.shadeddimensions.ep3.creativetab.CreativeTabEP3;

public class Reference
{
    static
    {
        Properties properties = new Properties();

        try
        {
            InputStream stream = Reference.class.getClassLoader().getResourceAsStream("version.properties");
            properties.load(stream);
            stream.close();
        }
        catch (Exception e)
        {

        }

        VERSION = properties.containsKey("ep3.version") ? properties.getProperty("ep3.version") : "";
    }

    public static final String ID = "ep3";
    public static final String NAME = "EnhancedPortals";
    public static final String VERSION;
    public static final String DEPENDENCIES = "after:ThermalExpansion";
    public static final String MC_VERSION = "[1.7.2,)";

    public static final String CLIENT_PROXY = "uk.co.shadeddimensions.ep3.network.ClientProxy";
    public static final String COMMON_PROXY = "uk.co.shadeddimensions.ep3.network.CommonProxy";

    public static final String GUI_FACTORY = "uk.co.shadeddimensions.ep3.client.GuiFactory";

    public static CreativeTabs creativeTab = new CreativeTabEP3();
}
