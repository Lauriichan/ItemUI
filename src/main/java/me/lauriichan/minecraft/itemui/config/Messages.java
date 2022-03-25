package me.lauriichan.minecraft.itemui.config;

import com.syntaxphoenix.syntaxapi.utils.java.UniCode;

public final class Messages {

    private Messages() {
        throw new UnsupportedOperationException("Constant class");
    }

    public static final Message NAME;
    public static final Message PREFIX;

    public static final Message CONFIG_RELOAD_SUCCESS;
    public static final Message CONFIG_RELOAD_FAILED;
    public static final Message CONFIG_RELOAD_ERROR_LOAD;
    public static final Message CONFIG_RELOAD_ERROR_SAVE;
    public static final Message CONFIG_RELOAD_HANDLER_FAILED;

    public static final Message DATA_LOAD_START;
    public static final Message DATA_LOAD_SUCCESS;
    public static final Message DATA_LOAD_FAILED;
    public static final Message DATA_SAVE_START;
    public static final Message DATA_SAVE_SUCCESS;
    public static final Message DATA_SAVE_FAILED;

    public static final Message ACTION_NOT_PERMITTED;

    public static final Message COMMAND_ONLY_PLAYER;
    public static final Message COMMAND_DOESNT_EXIST;
    public static final Message COMMAND_EXECUTION_FAILED;

    public static final Message COMMAND_ITEMS_NO_ITEM;
    public static final Message COMMAND_ITEMS_ADD_FAILED;
    public static final Message COMMAND_ITEMS_ADD_SUCCESS;
    public static final Message COMMAND_ITEMS_SAVE;
    public static final Message COMMAND_ITEMS_REMOVE_FAILED;
    public static final Message COMMAND_ITEMS_REMOVE_SUCCESS;
    public static final Message COMMAND_ITEMS_GET_INVALID_PLAYER;
    public static final Message COMMAND_ITEMS_GET_NOT_IN_RANGE;
    public static final Message COMMAND_ITEMS_GET_SUCCESS;
    public static final Message COMMAND_ITEMS_INDEX_LIST_ITEM_HOVER;
    public static final Message COMMAND_ITEMS_INDEX_LIST_ITEM_POINT;
    public static final Message COMMAND_ITEMS_INDEX_LIST_HEADER;
    public static final Message COMMAND_ITEMS_INDEX_LIST_FOOTER;
    public static final Message COMMAND_ITEMS_INDEX_LIST_EMPTY;

    public static final Message INVENTORY_NAVIGATION_FIRST;
    public static final Message INVENTORY_NAVIGATION_PREVIOUS;
    public static final Message INVENTORY_NAVIGATION_INFO;
    public static final Message INVENTORY_NAVIGATION_NEXT;
    public static final Message INVENTORY_NAVIGATION_LAST;

    static {
        NAME = Message.register("name", "&5Item&dUI");
        PREFIX = Message.register("prefix", "$#name &8" + UniCode.ARROWS_RIGHT + "&7");
        CONFIG_RELOAD_SUCCESS = Message.register("config.reload.success",
            "$#prefix &7Configuration &a$name &7was reloaded &asuccessfully&7!");
        CONFIG_RELOAD_FAILED = Message.register("config.reload.failed", "$#prefix &7Configuration &c$name &7couldn't be reloaded!");
        CONFIG_RELOAD_ERROR_LOAD = Message.register("config.reload.error.load",
            "$#prefix &7Something went wrong while loading configuration &c$name&7!");
        CONFIG_RELOAD_ERROR_SAVE = Message.register("config.reload.error.save",
            "$#prefix &7Something went wrong while saving configuration &c$name&7!");
        CONFIG_RELOAD_HANDLER_FAILED = Message.register("config.reload.handler.failed",
            "$#prefix &7Something went wrong while trying to reload a configuration handler for configuration &c$name&7!");
        DATA_LOAD_START = Message.register("data.load.start", "$#prefix &7Trying to load data from &e$name&7...");
        DATA_LOAD_SUCCESS = Message.register("data.load.success", "$#prefix &7Data was successfully loaded from &a$name&7!");
        DATA_LOAD_FAILED = Message.register("data.load.failed", "$#prefix &7Something went wrong while loading data from &c$name&7!");
        DATA_SAVE_START = Message.register("data.save.start", "$#prefix &7Trying to save data to &e$name&7...");
        DATA_SAVE_SUCCESS = Message.register("data.save.success", "$#prefix &7Data was successfully saved to &a$name&7!");
        DATA_SAVE_FAILED = Message.register("data.save.failed", "$#prefix  &7Something went wrong while saving data to &c$name&7!");
        ACTION_NOT_PERMITTED = Message.register("action.not-permitted",
            "$#prefix &7You're lacking the permission &c$permission &7to do this action!");
        COMMAND_ONLY_PLAYER = Message.register("command.general.only-player",
            "$#prefix &7The command &c$name &7can only be done by a player!");
        COMMAND_DOESNT_EXIST = Message.register("command.general.not-existent", "$#prefix &7The command &c$name &7doesn't exist!");
        COMMAND_EXECUTION_FAILED = Message.register("command.general.failed",
            "$#prefix &7Something went wrong while running command &c$name&7!");
        COMMAND_ITEMS_NO_ITEM = Message.register("command.items.no-item", "$#prefix &7You need to hold an item in your hand!");
        COMMAND_ITEMS_ADD_FAILED = Message.register("command.items.add.failed", "$#prefix &7Item &5$name &7is already on the item list!");
        COMMAND_ITEMS_ADD_SUCCESS = Message.register("command.items.add.success",
            "$#prefix &dSuccessfully &7added item &5$name &7to the item list!");
        COMMAND_ITEMS_SAVE = Message.register("command.items.save", "$#prefix &7Saved all items!");
        COMMAND_ITEMS_REMOVE_FAILED = Message.register("command.items.remove.failed",
            "$#prefix &7The item &5$name &7is not in the item list!");
        COMMAND_ITEMS_REMOVE_SUCCESS = Message.register("command.items.remove.success",
            "$#prefix &dSuccessfully &7removed item &5$name&7 from the item list!");
        COMMAND_ITEMS_GET_INVALID_PLAYER = Message.register("command.items.get.invalid-player",
            "$#prefix &7The player you specified doesn't exist or isn't online right now!");
        COMMAND_ITEMS_GET_NOT_IN_RANGE = Message.register("command.items.get.not-in-range",
            "$#prefix &7The index &d$index &7is not in range! &8(&7Max: &5$maxIndex&8)");
        COMMAND_ITEMS_GET_SUCCESS = Message.register("command.items.get.success", "$#prefix &7Gave item &d$name &7to &5$target&7!");
        COMMAND_ITEMS_INDEX_LIST_ITEM_HOVER = Message.register("command.items.index.list.hover", "&7Click to get &d$name&7!");
        COMMAND_ITEMS_INDEX_LIST_ITEM_POINT = Message.register("command.items.index.list.point", "&8[&d$index&8]: &5$name");
        COMMAND_ITEMS_INDEX_LIST_HEADER = Message.register("command.items.index.list.header", "&8=> $#name &8(&d$page&8/&5$maxPage&8)");
        COMMAND_ITEMS_INDEX_LIST_FOOTER = Message.register("command.items.index.list.footer", "&8=> $#name &8(&d$page&8/&5$maxPage&8)");
        COMMAND_ITEMS_INDEX_LIST_EMPTY = Message.register("command.items.index.list.empty", "$#prefix &7There are no items to be listed!");
        INVENTORY_NAVIGATION_FIRST = Message.register("inventory.navigation.first", "&7First page");
        INVENTORY_NAVIGATION_PREVIOUS = Message.register("inventory.navigation.previous", "&7Previous page");
        INVENTORY_NAVIGATION_INFO = Message.register("inventory.navigation.info", "&7Current page");
        INVENTORY_NAVIGATION_NEXT = Message.register("inventory.navigation.next", "&7Next page");
        INVENTORY_NAVIGATION_LAST = Message.register("inventory.navigation.last", "&7Last page");
    }

}
