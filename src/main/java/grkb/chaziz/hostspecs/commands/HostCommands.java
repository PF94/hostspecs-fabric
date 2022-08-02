package grkb.chaziz.hostspecs.commands;

import java.util.ArrayList;
import java.net.InetAddress;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import oshi.hardware.ComputerSystem;

public class HostCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("host").executes(HostCommand::executeHostSystemStats));
    }
}
class HostSpecifications {
    static SystemInfo si = new SystemInfo();
    public static String getProcessorName() {
        CentralProcessor cpu = si.getHardware().getProcessor();
        return cpu.getProcessorIdentifier().getName();
    }

    public static String getModelName() {
        ComputerSystem pc = si.getHardware().getComputerSystem();
        String manufacturer = pc.getManufacturer();
        String model = pc.getModel();
        return manufacturer + " " + model;
    }

    public static ArrayList<String> getSpecs() {
        ArrayList<String> specs = new ArrayList<>();
        try {
            specs.add("Computer: " + getModelName());
            specs.add("Processor: " + getProcessorName());
            specs.add("Operating system: " + System.getProperty("os.name"));
            specs.add("Architecture: " + System.getProperty("os.arch"));
            specs.add("Host name: " + InetAddress.getLocalHost().getHostName());
            specs.add("Java: " + System.getProperty("java.version") + " (Vendor: " + System.getProperty("java.vendor") + ")");
        }
        catch (Exception E) {
            specs.add("Unable to get system specifications");
            System.err.println(E.getMessage());
        }
        return specs;
    }
}
class HostCommand {
    public static int executeHostSystemStats(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerEntity executor = context.getSource().getPlayer();

        executor.sendMessage(new LiteralText("Server host information:"), false);
        for (String string : HostSpecifications.getSpecs()) {
            executor.sendMessage(new LiteralText("* " + string), false);
        }
        return 1;
    }
}