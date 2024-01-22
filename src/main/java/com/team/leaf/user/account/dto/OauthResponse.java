package com.team.leaf.user.account.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OauthResponse {
    private String status;
    private Object data;

    public OauthResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }
}
