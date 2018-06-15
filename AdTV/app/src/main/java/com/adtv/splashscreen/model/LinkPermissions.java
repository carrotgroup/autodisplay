package com.adtv.splashscreen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinkPermissions {
    @SerializedName("resolved_visibility")
    @Expose
    private ResolvedVisibility resolvedVisibility;
    @SerializedName("requested_visibility")
    @Expose
    private RequestedVisibility requestedVisibility;
    @SerializedName("can_revoke")
    @Expose
    private Boolean canRevoke;

    public ResolvedVisibility getResolvedVisibility() {
        return resolvedVisibility;
    }

    public void setResolvedVisibility(ResolvedVisibility resolvedVisibility) {
        this.resolvedVisibility = resolvedVisibility;
    }

    public RequestedVisibility getRequestedVisibility() {
        return requestedVisibility;
    }

    public void setRequestedVisibility(RequestedVisibility requestedVisibility) {
        this.requestedVisibility = requestedVisibility;
    }

    public Boolean getCanRevoke() {
        return canRevoke;
    }

    public void setCanRevoke(Boolean canRevoke) {
        this.canRevoke = canRevoke;
    }

}
