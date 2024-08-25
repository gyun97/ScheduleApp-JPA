package kdg.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kdg.entity.User;
import kdg.exception.MissingTokenException;
import kdg.jwt.JwtUtil;
import kdg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURI();

        try {
            if (StringUtils.hasText(url) && (url.endsWith("/signup") || url.endsWith("/login"))
            ) {
                // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
                chain.doFilter(request, response); // 다음 Filter 로 이동
            } else {
                // 나머지 API 요청은 인증 처리 진행
                // 토큰 확인
                String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

                if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                    // JWT 토큰 substring
                    String token = jwtUtil.substringToken(tokenValue);

                    // 토큰 검증
                    if (!jwtUtil.validateToken(token)) {
                        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Error");
                        return;
                    }

                    // 토큰에서 사용자 정보 가져오기
                    Claims info = jwtUtil.getUserInfoFromToken(token);

                    User user = userRepository.findByEmail(info.getSubject()).orElseThrow(() ->
                            new NullPointerException("해당 이메일을 갖고 있는 유저를 찾지 못했습니다.")
                    );

                    request.setAttribute("user", user);
                    chain.doFilter(request, response); // 다음 Filter 로 이동
                } else {
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 존재하지 않습니다.");
                    return;

                }
            }
        } catch (Exception e) {
            httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage());

        }
    }
}