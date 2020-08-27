package com.kuvshinov.test.util;

import java.util.concurrent.TimeUnit;

/**
 * Provides locking mechanism for entities by their IDs.
 */
public interface EntityLocker<ID> {

    /**
     * Locks entity by id.
     *
     * @param entityId - unique identification of entity.
     */
    void lock(ID entityId);

    /**
     * Trying to get lock for given timeout.
     *
     * @param entityId - unique identification of entity.
     * @param time - timeout.
     * @param timeUnit - unit of time.
     * @throws InterruptedException if cannot get lock for the given timeout.
     */
    void lock(ID entityId, long time, TimeUnit timeUnit) throws InterruptedException;

    /**
     * Unlock entity by id.
     *
     * @param entityId - unique identification of entity.
     */
    void unlock(ID entityId);
}
