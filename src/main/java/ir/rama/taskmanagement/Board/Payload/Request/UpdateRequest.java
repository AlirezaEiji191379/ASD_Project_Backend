package ir.rama.taskmanagement.Board.Payload.Request;

import ir.rama.taskmanagement.Core.Request.CrudRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest implements CrudRequest {

    private @NonNull Integer id;
    private @Nullable String title;
}
