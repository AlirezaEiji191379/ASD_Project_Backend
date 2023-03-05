package ir.rama.taskmanagement.Task.Payload.Request;

import ir.rama.taskmanagement.Core.Payload.Request.CrudRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest implements CrudRequest {

    private @NonNull Integer id;

    private @Nullable Integer priority;

    private @Nullable String title;

    private @Nullable String description;

    private @Nullable LocalDateTime deadline;

    private @Nullable List<Integer> userIds;

    private @Nullable Integer columnId;
}
