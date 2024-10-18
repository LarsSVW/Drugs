package de.lars.drugs;

import de.lars.drugs.api.DrugsAPI;
import de.lars.drugs.commands.*;
import de.lars.drugs.config.Configuration;
import de.lars.drugs.crafting.*;
import de.lars.drugs.crafting.Ecstasy.EcstasyCrafting;
import de.lars.drugs.crafting.Ecstasy.IsoSafroleCrafting;
import de.lars.drugs.crafting.Ecstasy.PiperonalCrafting;
import de.lars.drugs.crafting.Ecstasy.SafroleCrafting;
import de.lars.drugs.crafting.Heroin.HeroinCrafting;
import de.lars.drugs.crafting.Heroin.HeroinLiquidCrafting;
import de.lars.drugs.crafting.Heroin.SyringeCrafting;
import de.lars.drugs.crafting.LSD.*;
import de.lars.drugs.crafting.Speed.PhenylCrafting;
import de.lars.drugs.crafting.Speed.SpeedCrafting;
import de.lars.drugs.drugshop.commands.BuyCommand;
import de.lars.drugs.handler.MessageHandler;
import de.lars.drugs.handler.languagehandler.*;
import de.lars.drugs.handler.languagehandler.infohandler.InfoHandler;
import de.lars.drugs.listener.*;
import de.lars.drugs.listener.DrugEffectListener.*;
import de.lars.drugs.listener.DrugListener.CocaineListener;
import de.lars.drugs.listener.DrugListener.TobaccoListener;
import de.lars.drugs.listener.DrugListener.WeedListener;
import de.lars.drugs.listener.resourcepack.ResourcePackListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public class Drugs extends JavaPlugin implements Listener {
    private Configuration config;
    private static Economy econ = null;
    private JointCrafting jointCrafting;
    private GlueRecipeCrafting glueRecipeCrafting;
    private PapeCrafting papeCrafting;
    private LongPapeCrafting longPapeCrafting;
    private CigaretteCrafting cigaretteCrafting;
    private ZippoCrafting zippoCrafting;
    private NatriumCrafting natriumCrafting;
    private PropanolamineCrafting propanolamineCrafting;
    private LysergicAcidCrafting lysergicAcidCrafting;
    private HydrogenCrafting hydrogenCrafting;
    private LSDCrafting lsdCrafting;
    private EcstasyCrafting ecstasyCrafting;
    private IsoSafroleCrafting isoSafroleCrafting;
    private PiperonalCrafting piperonalCrafting;
    private SafroleCrafting safroleCrafting;
    private PhenylCrafting phenylCrafting;
    private SpeedCrafting speedCrafting;
    private MessageHandler messageHandler;
    private CNHandler cnHandler;
    private DEHandler deHandler;
    private ENHandler enHandler;
    private ESHandler esHandler;
    private FRHandler frHandler;
    private RUHandler ruHandler;
    private TRHandler trHandler;
    private InfoHandler infoHandler;
    private HeroinCrafting heroinCrafting;
    private HeroinLiquidCrafting heroinLiquidCrafting;
    private SyringeCrafting syringeCrafting;


    @Override
    public void onEnable() {
        this.config = new Configuration(new File(getDataFolder(), "config.yml"));
        this.config.setTemplateName("/config.yml");
        this.config.load();
        if (!setupEconomy()) {
            getLogger().warning("Vault Economy not found. Some features may not work.");
        } else {
            getLogger().info("Vault Economy found and enabled.");
        }

        messageHandler = new MessageHandler(this);
        registerlanguages();
        CocaineEffectListener cocaineEffectListener = new CocaineEffectListener(this,config);
        EcstasyEffectListener ecstasyEffectListener = new EcstasyEffectListener(this, config);
        HeroinEffectListener heroinEffectListener = new HeroinEffectListener(this, config);
        LSDEffectListener lsdEffectListener = new LSDEffectListener(this, config);
        ShroomsEffectListener shroomsEffectListener = new ShroomsEffectListener(this,config);
        SpeedEffectListener speedEffectListener = new SpeedEffectListener(this, config);
        TobaccoEffectListener tobaccoEffectListener = new TobaccoEffectListener(this,config);
        WeedEffectListener weedEffectListener = new WeedEffectListener(this,config);
        getServer().getPluginManager().registerEvents(new RenameBlockListener(this, config), this);
        getServer().getPluginManager().registerEvents(new GrindstoneBlockListener(this, config), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new WeedListener(this,config),this);
        getServer().getPluginManager().registerEvents(new CocaineListener(this,config), this);
        getServer().getPluginManager().registerEvents(weedEffectListener, this);
        getServer().getPluginManager().registerEvents(new GrassBreakListener(this, config), this);
        getServer().getPluginManager().registerEvents(new ShroomBreakListener(this, config), this);
        getServer().getPluginManager().registerEvents(new CocaineListener(this,config), this);
        getServer().getPluginManager().registerEvents(cocaineEffectListener, this);
        getServer().getPluginManager().registerEvents(tobaccoEffectListener,this);
        getServer().getPluginManager().registerEvents(new TobaccoListener(this, config), this);
        getServer().getPluginManager().registerEvents(shroomsEffectListener,this);
        getServer().getPluginManager().registerEvents(lsdEffectListener, this);
        getServer().getPluginManager().registerEvents(ecstasyEffectListener, this);
        getServer().getPluginManager().registerEvents(speedEffectListener, this);
        getServer().getPluginManager().registerEvents(heroinEffectListener, this);
        getServer().getPluginManager().registerEvents(new SleepBlockListener(this), this);
        getServer().getPluginManager().registerEvents(new ResourcePackListener(this), this);
        getServer().getPluginManager().registerEvents(new DrugTestListener(this,lsdEffectListener,new WeedEffectListener(this, config), new CocaineEffectListener(this, config), new ShroomsEffectListener(this, config), new HeroinEffectListener(this, config), new EcstasyEffectListener(this, config), new SpeedEffectListener(this, config), new TobaccoEffectListener(this, config)), this);
        getCommand("drugs").setExecutor(new DrugsCommand(this, config));
        getCommand("languageswitch").setExecutor(new LanguageSwitchCommand(this, config));
        getCommand("languageprovided").setExecutor(new LanguageProvidedCommand(this, config));
        FirstStartLanguageInitializer initializer = new FirstStartLanguageInitializer(this, config);
        initializer.initializeLanguageOnFirstStart();
        registerCraftingRecipes();
        DrugsAPI.initialize(this, this.config);
    }


    @Override
    public void onDisable() {
        Bukkit.clearRecipes();
    }
    public NamespacedKey getKey(String key) {
        return new NamespacedKey(this, key);
    }
    public MessageHandler getMessageHandler () {
        return messageHandler;
    }
    private void loadLanguageFiles(File folder) {
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    getLogger().info("Loading languages: " + file.getName());
                }
            }
        }
    }
    public DEHandler getDeHandler(){
        return deHandler;
    }
    public ENHandler getEnHandler(){
        return enHandler;
    }
    public ESHandler getEsHandler(){
        return esHandler;
    }
    public RUHandler getCnHandler (){
        return ruHandler;
    }
    public FRHandler getFrHandler(){
        return frHandler;
    }
    public TRHandler getTrHandler(){
        return trHandler;
    }

    private void registerlanguages(){
        cnHandler = new CNHandler(this);
        deHandler = new DEHandler(this);
        enHandler = new ENHandler(this);
        esHandler = new ESHandler(this);
        frHandler = new FRHandler(this);
        ruHandler = new RUHandler(this);
        trHandler = new TRHandler(this);
        infoHandler = new InfoHandler(this);
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
    public void registerCraftingRecipes() {
        zippoCrafting = new ZippoCrafting(this, config);
        zippoCrafting.registerRecipes();
        papeCrafting = new PapeCrafting(this, config);
        papeCrafting.registerRecipes();
        jointCrafting = new JointCrafting(this, config);
        jointCrafting.registerRecipes();
        cigaretteCrafting = new CigaretteCrafting(this, config);
        cigaretteCrafting.registerRecipes();
        glueRecipeCrafting = new GlueRecipeCrafting(this, config);
        glueRecipeCrafting.registerRecipes();
        longPapeCrafting = new LongPapeCrafting(this, config);
        longPapeCrafting.registerRecipes();
        natriumCrafting = new NatriumCrafting(this, config);
        natriumCrafting.registerRecipes();
        hydrogenCrafting = new HydrogenCrafting(this, config);
        hydrogenCrafting.registerRecipes();
        lsdCrafting = new LSDCrafting(this, config);
        lsdCrafting.registerRecipes();
        lysergicAcidCrafting = new LysergicAcidCrafting(this, config);
        lysergicAcidCrafting.registerRecipes();
        propanolamineCrafting = new PropanolamineCrafting(this, config);
        propanolamineCrafting.registerRecipes();
        ecstasyCrafting = new EcstasyCrafting(this, config);
        ecstasyCrafting.registerRecipes(config);
        isoSafroleCrafting = new IsoSafroleCrafting(this, config);
        isoSafroleCrafting.registerRecipes();
        piperonalCrafting = new PiperonalCrafting(this, config);
        piperonalCrafting.registerRecipes();
        safroleCrafting = new SafroleCrafting(this, config);
        safroleCrafting.registerRecipes();
        phenylCrafting = new PhenylCrafting(this, config);
        phenylCrafting.registerRecipes();
        speedCrafting = new SpeedCrafting(this, config);
        speedCrafting.registerRecipes();
        heroinCrafting = new HeroinCrafting(this, config);
        heroinCrafting.registerRecipes();
        heroinLiquidCrafting = new HeroinLiquidCrafting(this, config);
        heroinLiquidCrafting.registerRecipes();
        syringeCrafting = new SyringeCrafting(this, config);
        syringeCrafting.registerRecipes();
    }


}


