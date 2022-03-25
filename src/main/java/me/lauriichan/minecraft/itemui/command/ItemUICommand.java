package me.lauriichan.minecraft.itemui.command;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.avinity.command.CommandContext;
import com.syntaxphoenix.avinity.command.node.Argument;
import com.syntaxphoenix.avinity.command.node.Literal;
import com.syntaxphoenix.avinity.command.node.Root;
import com.syntaxphoenix.avinity.command.type.IArgumentType;

import me.lauriichan.minecraft.itemui.command.impl.BukkitSource;
import me.lauriichan.minecraft.itemui.command.impl.ICommandSource;
import me.lauriichan.minecraft.itemui.compatibility.IVersionProvider;
import me.lauriichan.minecraft.itemui.config.Messages;
import me.lauriichan.minecraft.itemui.config.item.SimpleItemContainer;
import me.lauriichan.minecraft.itemui.inventory.handle.GuiInventory;
import me.lauriichan.minecraft.itemui.inventory.handle.PlayerInventoryHolder;
import me.lauriichan.minecraft.itemui.inventory.item.ItemEditor;
import me.lauriichan.minecraft.itemui.util.InventoryHelper;
import me.lauriichan.minecraft.itemui.util.JavaHelper;
import me.lauriichan.minecraft.itemui.util.Tuple;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

@SuppressWarnings("unchecked")
public final class ItemUICommand implements ICommandSource {

    private final SimpleItemContainer container;

    public ItemUICommand(final SimpleItemContainer container) {
        this.container = container;
    }

    @Override
    public String name() {
        return "items";
    }

    @Override
    public Root<BukkitSource> build(String name) {
        return Root.<BukkitSource>of(name).permission(Permissions.COMMAND_ITEMS_INDEX).execute(this::onItemIndexUi)
            .append(Literal.<BukkitSource>of("get").permission(Permissions.COMMAND_ITEMS_GET)
                .argument("index", Argument.between(0, 2, IArgumentType.minimum(0)))
                .argument("target", Argument.between(0, 2, PlayerArgument.PLAYER))
                .argument("muted", Argument.between(0, 2, IArgumentType.BOOLEAN)).execute(this::onItemGet))
            .append(Literal.<BukkitSource>of("add").permission(Permissions.COMMAND_ITEMS_ADD).execute(this::onItemAdd))
            .append(Literal.<BukkitSource>of("remove").permission(Permissions.COMMAND_ITEMS_REMOVE).execute(this::onItemRemove))
            .append(Literal.<BukkitSource>of("save").permission(Permissions.COMMAND_ITEMS_SAVE).execute(this::onItemSave))
            .append(Literal.<BukkitSource>of("ui").permission(Permissions.COMMAND_ITEMS_INDEX).execute(this::onItemIndexUi)).append(Literal
                .<BukkitSource>of("list").argument("page", Argument.at(0, IArgumentType.minimum(1), true)).execute(this::onItemIndexList));
    }

    private void onItemAdd(CommandContext<BukkitSource> context) {
        Optional<Player> option = context.getSource().getPlayerAsOptional();
        BukkitSource source = context.getSource();
        if (option.isEmpty()) {
            source.send(Messages.COMMAND_ONLY_PLAYER, Tuple.of("name", context.getNode().getName()));
            return;
        }
        ItemStack itemStack = option.get().getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType().name().endsWith("AIR")) {
            source.send(Messages.COMMAND_ITEMS_NO_ITEM);
            return;
        }
        String name = ItemEditor.of(itemStack).getName().orElse(JavaHelper.formatPascalCase(itemStack.getType()));
        if (!container.add(itemStack)) {
            source.send(Messages.COMMAND_ITEMS_ADD_FAILED, Tuple.of("name", name));
            return;
        }
        source.send(Messages.COMMAND_ITEMS_ADD_SUCCESS, Tuple.of("name", name));
    }

    private void onItemRemove(CommandContext<BukkitSource> context) {
        Optional<Player> option = context.getSource().getPlayerAsOptional();
        BukkitSource source = context.getSource();
        if (option.isEmpty()) {
            source.send(Messages.COMMAND_ONLY_PLAYER, Tuple.of("name", context.getNode().getName()));
            return;
        }
        ItemStack itemStack = option.get().getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType().name().endsWith("AIR")) {
            source.send(Messages.COMMAND_ITEMS_NO_ITEM);
            return;
        }
        String name = ItemEditor.of(itemStack).getName().orElse(JavaHelper.formatPascalCase(itemStack.getType()));
        if (container.remove(itemStack)) {
            source.send(Messages.COMMAND_ITEMS_REMOVE_SUCCESS, Tuple.of("name", name));
            return;
        }
        source.send(Messages.COMMAND_ITEMS_REMOVE_FAILED, Tuple.of("name", name));
    }

    private void onItemSave(CommandContext<BukkitSource> context) {
        container.forceSave();
        context.getSource().send(Messages.COMMAND_ITEMS_SAVE);
    }

    private void onItemGet(CommandContext<BukkitSource> context) {
        Optional<Player> option = context.getSource().getPlayerAsOptional();
        Player target = context.has("target") ? context.get("target", Player.class) : null;
        BukkitSource source = context.getSource();
        boolean muted = context.getOrDefault("muted", false);
        if (option.isEmpty() && target == null) {
            if (muted) {
                return;
            }
            source.send(Messages.COMMAND_ITEMS_GET_INVALID_PLAYER);
            return;
        }
        if (target == null) {
            target = option.get();
        }
        int index = context.getOrDefault("index", 0);
        int size = container.size();
        if (index >= size) {
            if (muted) {
                return;
            }
            source.send(Messages.COMMAND_ITEMS_GET_NOT_IN_RANGE, Tuple.of("index", index), Tuple.of("maxIndex", size - 1));
            return;
        }
        ItemStack itemStack = container.get(index);
        InventoryHelper.add(target.getLocation(), target.getInventory(), itemStack, 1);
        if (muted) {
            return;
        }
        source.send(Messages.COMMAND_ITEMS_GET_SUCCESS, Tuple.of("target", target.getName()),
            Tuple.of("name", ItemEditor.of(itemStack).getName().orElse(JavaHelper.formatPascalCase(itemStack.getType()))));
    }

    private void onItemIndexUi(CommandContext<BukkitSource> context) {
        Optional<Player> option = context.getSource().getPlayerAsOptional();
        BukkitSource source = context.getSource();
        if (option.isEmpty()) {
            source.send(Messages.COMMAND_ONLY_PLAYER, Tuple.of("name", context.getNode().getName()));
            return;
        }
        Player player = option.get();
        GuiInventory gui = PlayerInventoryHolder.HOLDER.getInventory(player, container.getHandler());
        gui.getProperties().clear();
        String name = Messages.NAME.asColoredMessageString();
        if (!name.equals(gui.getName())) {
            gui.updateName(name);
        }
        gui.update().open(player);
    }

    private void onItemIndexList(CommandContext<BukkitSource> context) {
        BukkitSource source = context.getSource();
        int page = context.getOrDefault("page", 1) - 1;
        int maxPage = (int) Math.ceil(container.size() / 10d);
        if (maxPage == 0) {
            source.send(Messages.COMMAND_ITEMS_INDEX_LIST_EMPTY);
            return;
        }
        if (page >= maxPage) {
            page = maxPage - 1;
        }
        int startIdx = page * 10;
        int endIdx = ((page + 1) * 10) - 1;
        if (endIdx > container.size()) {
            endIdx = container.size() - 1;
        }
        Tuple<String, Object> pageTuple = Tuple.of("page", (page + 1));
        Tuple<String, Object> maxPageTuple = Tuple.of("maxPage", maxPage);
        source.send(Messages.COMMAND_ITEMS_INDEX_LIST_HEADER, pageTuple, maxPageTuple);
        for (int idx = startIdx; idx <= endIdx; idx++) {
            ItemStack itemStack = container.get(idx);
            if (itemStack == null) {
                continue;
            }
            String name = ItemEditor.of(itemStack).getName().orElse(JavaHelper.formatPascalCase(itemStack.getType()));
            TextComponent component = new TextComponent(
                Messages.COMMAND_ITEMS_INDEX_LIST_ITEM_POINT.asColoredMessageString(Tuple.of("index", idx), Tuple.of("name", name)));
            component.setHoverEvent(IVersionProvider.provider().createTextHover(new BaseComponent[] {
                new TextComponent(
                    Messages.COMMAND_ITEMS_INDEX_LIST_ITEM_HOVER.asColoredMessageString(Tuple.of("index", idx), Tuple.of("material", name)))
            }));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/items get " + idx + " true"));
            source.getSender().spigot().sendMessage(component);
        }
        source.send(Messages.COMMAND_ITEMS_INDEX_LIST_FOOTER, pageTuple, maxPageTuple);
    }

}
