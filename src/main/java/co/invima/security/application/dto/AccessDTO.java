package co.invima.security.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
@Data
@Builder
public class AccessDTO implements Serializable {
    private Boolean manageGroupMembership;
    private Boolean view;
    private Boolean mapRoles;
    private Boolean impersonate;
    private Boolean manage;
}
