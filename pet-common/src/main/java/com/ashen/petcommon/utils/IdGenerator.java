package com.ashen.petcommon.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.UUID;

public final class IdGenerator {

    private static final long EPOCH = 1672531200000L;

    private static final long WORKER_ID_BITS = 5L;
    private static final long DATACENTER_ID_BITS = 5L;
    private static final long SEQUENCE_BITS = 12L;

    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    private final long workerId;
    private final long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    /**
     * Default singleton instance.
     * <p>
     * Uses a best-effort, deterministic machine fingerprint to derive {@code datacenterId} and {@code workerId},
     * so different nodes in a cluster are far less likely to collide.
     */
    private static final IdGenerator INSTANCE = createDefaultInstance();

    /**
     * Backward-compatible accessor.
     */
    public static IdGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * Preferred API: generate a new snowflake-like id without explicitly fetching the singleton.
     */
    public static long generateId() {
        return INSTANCE.nextId();
    }

    /**
     * Preferred API: string form.
     */
    public static String nextIdStr() {
        return Long.toString(generateId());
    }

    public IdGenerator(long workerId, long datacenterId) {
        if (workerId < 0 || workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException(String.format("workerId must be between 0 and %d", MAX_WORKER_ID));
        }
        if (datacenterId < 0 || datacenterId > MAX_DATACENTER_ID) {
            throw new IllegalArgumentException(String.format("datacenterId must be between 0 and %d", MAX_DATACENTER_ID));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = currentTime();

        if (timestamp < lastTimestamp) {
            throw new IllegalStateException(
                String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp)
            );
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * Instance form kept for compatibility.
     * Prefer {@link #nextIdStr()} when you don't need a custom instance.
     */
    public String generateIdStr() {
        return Long.toString(generateId());
    }

    public static String nextUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = currentTime();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTime();
        }
        return timestamp;
    }

    private long currentTime() {
        return System.currentTimeMillis();
    }

    private static IdGenerator createDefaultInstance() {
        long fingerprint = machineFingerprint();
        long datacenterId = (fingerprint >>> 5) & MAX_DATACENTER_ID;
        long workerId = fingerprint & MAX_WORKER_ID;
        return new IdGenerator(workerId, datacenterId);
    }

    /**
     * Best-effort stable fingerprint for the current process/machine.
     *
     * <p>Inputs (when available): MAC addresses, hostname, and runtime MXBean name (often pid@host).
     */
    private static long machineFingerprint() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // MAC addresses
            try {
                Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
                if (nis != null) {
                    while (nis.hasMoreElements()) {
                        NetworkInterface ni = nis.nextElement();
                        byte[] mac = ni.getHardwareAddress();
                        if (mac != null && mac.length > 0) {
                            md.update(mac);
                        }
                    }
                }
            } catch (Exception ignored) {
                // ignored
            }

            // Hostname
            try {
                String host = InetAddress.getLocalHost().getHostName();
                if (host != null) {
                    md.update(host.getBytes(StandardCharsets.UTF_8));
                }
            } catch (Exception ignored) {
                // ignored
            }

            // pid@host (usually)
            try {
                String runtime = ManagementFactory.getRuntimeMXBean().getName();
                if (runtime != null) {
                    md.update(runtime.getBytes(StandardCharsets.UTF_8));
                }
            } catch (Exception ignored) {
                // ignored
            }

            byte[] digest = md.digest();
            long v = 0L;
            // use first 8 bytes as unsigned-ish long
            for (int i = 0; i < 8 && i < digest.length; i++) {
                v = (v << 8) | (digest[i] & 0xFFL);
            }

            // fold to 10 bits (5+5)
            long folded = v ^ (v >>> 32) ^ (v >>> 16);
            return folded & ((1L << (WORKER_ID_BITS + DATACENTER_ID_BITS)) - 1);
        } catch (Exception e) {
            // last resort: time-based value; still masks to 10 bits
            return (System.nanoTime() ^ System.currentTimeMillis()) & ((1L << (WORKER_ID_BITS + DATACENTER_ID_BITS)) - 1);
        }
    }
}