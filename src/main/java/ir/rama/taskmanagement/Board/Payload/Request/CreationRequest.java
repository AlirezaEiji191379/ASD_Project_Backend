package ir.rama.taskmanagement.Board.Payload.Request;

import ir.rama.taskmanagement.Core.Request.CrudRequest;
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
}
