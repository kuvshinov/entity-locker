package com.kuvshinov.test.util;

/**
 * Creates {@link EntityLocker} for the given class.
 */
public final class EntityLockerFactory {

    private EntityLockerFactory() {}

    public static <ID> EntityLocker<ID> getLocker() {
        return new ConcurrentEntityLocker<>();
    }

    public static <ID> EntityLocker<ID> getLocker(boolean fair) {
        return new ConcurrentEntityLocker<>(fair);
    }

}
