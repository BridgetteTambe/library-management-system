package com.drycode.web;

import com.drycode.models.User;
import com.drycode.security.JwtUtil;
import com.drycode.security.MyUserDetailsService;
import com.drycode.services.UserService;
import com.drycode.web.dto.AuthenticationRequest;
import com.drycode.web.dto.AuthenticationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api")
public class AuthenticateResource {

    private Logger logger = LoggerFactory.getLogger(AuthenticateResource.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String jwtToken = null;
        try {
            logger.info("Authentication Body {} ", authenticationRequest.toString());
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            jwtToken = this.jwtUtil.generateToken(userDetails);
            User user = null;
            if (userDetails != null) {
                user = this.userService.findByUsername(authenticationRequest.getUsername());
                user.setLastLoginDate(LocalDateTime.now());
                userService.save(user);
            }
            return ResponseEntity.ok(new AuthenticationResponse(jwtToken, user));
        } catch (BadCredentialsException b) {
            logger.info("Incorrect Username or Password:  {} ", b.getMessage());
            return ResponseEntity.badRequest().body("Incorrect Username or Password ");
        }

    }
}
