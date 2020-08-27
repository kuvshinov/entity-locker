package com.kuvshinov.test.util;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Tread safe implementation of {@link EntityLocker}.
 *
 * @param <ID> - id type.
 */
class ConcurrentEntityLocker<ID> implements EntityLocker<ID> {

    private final ConcurrentMap<ID, ReentrantLock> lockHashMap;

    private final boolean fair;

    ConcurrentEntityLocker() {
        this(false);
    }

    ConcurrentEntityLocker(boolean fair) {
        this.lockHashMap = new ConcurrentHashMap<>();
        this.fair = fair;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lock(ID entityId) {
        getLock(entityId).lock();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lock(ID entityId, long time, TimeUnit timeUnit) throws InterruptedException {
        if (!getLock(entityId).tryLock(time, timeUnit)) {
            throw new InterruptedException("Unable to get lock at this moment");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlock(ID entityId) {
        Objects.requireNonNull(entityId);
        ReentrantLock lock = Optional.ofNullable(lockHashMap.get(entityId))
                .orElseThrow(IllegalMonitorStateException::new);
        lock.unlock();
    }

    private ReentrantLock getLock(ID entityId) {
        Objects.requireNonNull(entityId);
        return lockHashMap.computeIfAbsent(entityId, id -> new ReentrantLock(fair));
    }
}
