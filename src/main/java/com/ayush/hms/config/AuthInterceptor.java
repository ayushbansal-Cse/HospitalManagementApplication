package com.ayush.hms.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        String uri =
                request.getRequestURI();


        // =====================================================
        // LOGIN PAGES KO ALLOW KARO
        // =====================================================

        if (uri.equals("/") ||
                uri.equals("/login")) {

            return true;
        }


        // =====================================================
        // STATIC FILES KO ALLOW KARO
        // =====================================================

        if (uri.startsWith("/css/") ||
                uri.startsWith("/js/") ||
                uri.startsWith("/images/") ||
                uri.startsWith("/favicon")) {

            return true;
        }


        // =====================================================
        // SESSION CHECK
        // =====================================================

        HttpSession session =
                request.getSession(false);


        if (session != null &&
                session.getAttribute("username") != null) {

            return true;
        }


        // =====================================================
        // LOGIN NAHI HAI
        // LOGIN PAGE PAR BHEJO
        // =====================================================

        response.sendRedirect("/login");

        return false;
    }
}