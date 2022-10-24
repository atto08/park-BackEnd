package com.killdongmu.Hanghae99Week5BackEnd.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ade)
            throws IOException {
        // 필요한 권한이 없이 접근하려 할때 403
        res.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
