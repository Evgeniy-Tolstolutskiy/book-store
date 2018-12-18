package com.tolstolutskyi.model.projection;

public interface BookProjection {
    Long getId();

    String getName();

    String getPhotoLink();

    Integer getCount();

    Double getPrice();

    Boolean getVisible();
}
