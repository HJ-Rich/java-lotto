package domain;

import java.util.Arrays;

public enum Rank {
    FIRST(6, 2000000000L),
    SECOND(5, 1500000L),
    THIRD(5, 50000L),
    FOURTH(4, 5000L),
    FIFTH(3, 0L),
    NO_MATCH(0, 0L);

    private final int sameNumberCount;
    private final long prize;

    Rank(int rank, long prize) {
        this.sameNumberCount = rank;
        this.prize = prize;
    }

    public static Rank of(int sameNumberCount) {
        return Arrays.stream(Rank.values())
                .filter(rank -> rank.isSameNumberCount(sameNumberCount))
                .findAny()
                .orElse(NO_MATCH);
    }

    public boolean isSameNumberCount(int count) {
        return sameNumberCount == count;
    }
}