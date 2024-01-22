package com.team.leaf.user.account.controller;

import com.team.leaf.user.account.dto.OauthResponse;
import com.team.leaf.user.account.dto.SessionRequest;
import com.team.leaf.user.account.dto.SessionUser;
import com.team.leaf.user.account.service.OauthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {
    private final OauthService oauthService;
    //private final HttpSession httpSession;

    @PostMapping("/user")
    public OauthResponse getUserInfo(@RequestBody SessionRequest sessionRequest, HttpSession httpSession) {
        String sessionId = sessionRequest.getSessionId();
        SessionUser sessionUser = oauthService.getSessionUserBySessionId(sessionId, httpSession);

        if (sessionUser != null) {
            return new OauthResponse("success", sessionUser);
        } else {
            return new OauthResponse("error", "세션 정보를 찾을 수 없습니다");
        }
    }

}
