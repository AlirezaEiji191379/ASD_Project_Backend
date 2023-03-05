package ir.rama.taskmanagement.BoardColumn.Payload.Request;

import ir.rama.taskmanagement.Core.Payload.Request.CrudRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreationRequest implements CrudRequest {

    private @NonNull String title;

    private @NonNull Integer boardId;
}
