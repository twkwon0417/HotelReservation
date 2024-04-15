package b09.model.member;

public enum Rank {    // 절대 Rank같은거 code에 써야 할떄 Rank.BRONZE 이런씩으로 써주세염
    BRONZE(0),
    SILVER(200000),
    GOLD(500000),
    PLATINUM(800000),
    VIP(1000000);

    private final int minimumSpent;

    Rank(int minimumSpent) {
        this.minimumSpent = minimumSpent;
    }

    public int getMinimumSpent() {
        return minimumSpent;
    }
}
