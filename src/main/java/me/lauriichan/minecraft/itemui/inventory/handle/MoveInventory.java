package me.lauriichan.minecraft.itemui.inventory.handle;

public enum MoveInventory {

    SOURCE_INITIATOR(true, true),
    SOURCE_DESTINATION(false, true),
    DESTINATION(false, false),
    INITIATOR(true, false);

    private final boolean initiator, source;

    MoveInventory(final boolean initiator, final boolean source) {
        this.initiator = initiator;
        this.source = source;
    }

    public boolean isInitiator() {
        return initiator;
    }

    public boolean isSource() {
        return source;
    }

    public static MoveInventory of(final boolean initiator, final boolean source) {
        if (initiator) {
            if (source) {
                return MoveInventory.SOURCE_INITIATOR;
            }
            return MoveInventory.INITIATOR;
        }
        if (source) {
            return MoveInventory.SOURCE_DESTINATION;
        }
        return MoveInventory.DESTINATION;
    }

}
