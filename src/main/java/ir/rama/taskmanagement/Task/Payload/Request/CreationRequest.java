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
public class CreationRequest implements CrudRequest {

    private @NonNull Integer priority;

    private @NonNull String title;

    private @Nullable String description;

    private @Nullable LocalDateTime deadline;

    private @NonNull List<Integer> userIds;

    private @NonNull Integer columnId;
}
