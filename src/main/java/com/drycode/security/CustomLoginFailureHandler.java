package com.drycode.security;

import com.drycode.models.User;
import com.drycode.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final Long FAILURE_TIMEOUT = 3L;
    private static final Logger log = LoggerFactory.getLogger(CustomLoginFailureHandler.class);

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        User user = this.userService.findByUsername(username);
        if (user != null) {
            log.info("User Found  Login:{}", username);
            if (user.getEnabled() && !user.getUserLocked()) {

                if (user.getFailedAttempt() <= FAILURE_TIMEOUT) {
                    try {
                        user.setFailedAttempt((short) (user.getFailedAttempt() + 1));
                        userService.save(user);
                        exception = new LockedException("Invalid User name or password: Please provide the correct details, Your account will be locked on 3 failed attempts: Failed Attempt: " + user.getFailedAttempt());
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                } else {
                    try {
                        user.setUserLocked(true);
                        user.setEnabled(false);
                        user.setLockTime(LocalDateTime.now());
                        user.setLockedReason("User Account Is Locked Please contact the System Admin");
                        userService.save(user);
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                    exception = new LockedException(user.getLockedReason());
                }
            }

        } else {
            log.info("User Does not exist {} ", username);
            throw new RuntimeException("User Does not exist: " + username);
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}
