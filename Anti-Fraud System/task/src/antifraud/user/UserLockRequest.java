package antifraud.user;

public record UserLockRequest(
        String username,
        UserLock operation
) {
    public boolean getLock() {
        return switch (operation) {
            case LOCK -> true;
            case UNLOCK -> false;
        };
    }

    public enum UserLock {
        LOCK("locked"),
        UNLOCK("unlocked");

        final String post;

        UserLock(String post) {
            this.post = post;
        }

        public UserLock of(boolean locked) {
            if (locked) {
                return UserLock.LOCK;
            } else return UserLock.UNLOCK;
        }
    }
}
