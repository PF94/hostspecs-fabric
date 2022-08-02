package grkb.chaziz.hostspecs.commands;

import java.util.ArrayList; // import the ArrayList class

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class HostCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("host")
                .executes(HostCommand::executeHostSystemStats));
    }
}
class HostSpecifications {
    public static ArrayList<String> getSpecs() {
        ArrayList<String> specs = new ArrayList<>();
        specs.add("Operating system: " + System.getProperty("os.name"));
        specs.add("Java version: " + System.getProperty("java.version"));
        specs.add("Java vendor: " + System.getProperty("java.vendor"));
        return specs;
    }
}
class HostCommand {
    public static int executeHostSystemStats(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerEntity executor = context.getSource().getPlayer();

        executor.sendMessage(new LiteralText("Server host information:"), false);
        for (String string : HostSpecifications.getSpecs()) {
            executor.sendMessage(new LiteralText(string), false);
        }
        return 1;
    }
}