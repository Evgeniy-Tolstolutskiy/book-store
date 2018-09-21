package com.tolstolutskyi.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface UserProjection {
    Long getId();

    String getUsername();

    @Value("#{target.phoneCountryCode!=null ? target.phoneCountryCode + target.phoneNumber : null }")
    String getPhoneNumber();

    Long getReportCount();

    @Value("#{target.creationDate!=null ? target.creationDate.getTime() : null }")
    Long getCreationDate();

    @Value("#{target.lastActivityDate!=null ? target.lastActivityDate.getTime() : null}")
    Long getLastActivityDate();

    Boolean getIsBlocked();

    String getLoginType();
}
