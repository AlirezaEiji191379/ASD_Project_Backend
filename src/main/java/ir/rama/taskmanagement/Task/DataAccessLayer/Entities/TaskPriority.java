package ir.rama.taskmanagement.Task.DataAccessLayer.Entities;

import lombok.Getter;

@Getter
public enum TaskPriority {
    MUST(100),
    SHOULD(70),
    COULD(40),
    WILL_NOT(0);

    private final int value;

    TaskPriority(int value) {
        this.value = value;
    }
}
