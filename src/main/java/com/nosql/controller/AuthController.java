package com.nosql.controller;


import com.nosql.logging.AuthenticationInfo;
import com.nosql.model.User;
import com.nosql.repository.GroupRepository;
import com.nosql.repository.UserRepository;
import com.nosql.security.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository apacheLdapRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    AuthenticationInfo authenticationInfo;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @PostMapping("/generatetoken")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        if (loginRequest.getUsername().isEmpty() || loginRequest.getPassword().isEmpty()) {
            logger.info("User with uid " + authenticationInfo.getUsername() + " try to logged to the system");
            return new ResponseEntity(new ApiResponse(false, MessageConstants.USERNAME_OR_PASSWORD_INVALID),
                    HttpStatus.BAD_REQUEST);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException ex) {
            logger.info("User with uid " + loginRequest.getUsername() + " failed to logged to the system");
            return new ResponseEntity<>("Invalid user name or password", HttpStatus.NOT_FOUND);
        }
        User user = apacheLdapRepository.findByUid(loginRequest.getUsername());
        logger.info("User with uid " + loginRequest.getUsername() + " logged to the system");
        String jwt = tokenProvider.generateToken(user);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/validatetoken")
    public ResponseEntity<?> getTokenByCredentials(@Valid @RequestBody ValidateTokenRequest validateToken) {
        String username = null;
        String jwt = validateToken.getToken();
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            username = tokenProvider.getUsernameFromJWT(jwt);
            //If required we can have one more check here to load the user from LDAP server
            logger.info("Token from user " + username + " is validated");
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, MessageConstants.VALID_TOKEN + username));
        } else {
            logger.info("Token from user " + username + " is invalid token");
            return new ResponseEntity(new ApiResponse(false, MessageConstants.INVALID_TOKEN),
                    HttpStatus.BAD_REQUEST);
        }

    }

}
