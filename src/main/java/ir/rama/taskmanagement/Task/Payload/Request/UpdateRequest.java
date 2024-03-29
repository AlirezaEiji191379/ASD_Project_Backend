package ir.rama.taskmanagement.Task.Payload.Request;

import ir.rama.taskmanagement.Core.Request.CrudRequest;
import ir.rama.taskmanagement.Task.DataAccessLayer.Entities.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest implements CrudRequest {

    private @NonNull Integer id;

    private @Nullable TaskPriority priority;

    private @Nullable String title;

    private @Nullable String description;

    private @Nullable LocalDateTime deadline;

    private @Nullable Integer userId;

    private @Nullable Integer columnId;
}
