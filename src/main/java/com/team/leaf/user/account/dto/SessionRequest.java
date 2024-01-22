package com.team.leaf.user.account.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SessionRequest {
    private String sessionId;
}
