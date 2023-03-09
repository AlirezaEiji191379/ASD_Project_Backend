package ir.rama.taskmanagement.Core.Request;

import ir.rama.taskmanagement.Account.Authentication.DataAccessLayer.Entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public interface CrudRequest {

    default String getUsername() {
        var user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }
}
