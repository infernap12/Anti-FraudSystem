package antifraud.auth.user;

import lombok.Getter;

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

    @Getter
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
