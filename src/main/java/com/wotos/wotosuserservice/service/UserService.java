package com.wotos.wotosuserservice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wotos.wotosuserservice.dao.UserRepo;
import com.wotos.wotosuserservice.model.*;
import com.wotos.wotosuserservice.security.CustomUserDetailsService;
import com.wotos.wotosuserservice.util.JwtUtil;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Service
public class UserService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final JSONParser jsonParser = new JSONParser();
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders httpHeaders = new HttpHeaders();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserRepo repo;

    @Autowired
    public UserService() {
        mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false
        );
        httpHeaders.add("Web Token", "");
//        httpHeaders.add("Language", "");
//        httpHeaders.add("Last Modified", "");
    }

    public ResponseEntity<LocalUserViewModel> login(UserAuthenticationRequest userAuthenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userAuthenticationRequest.getUsername(),
                            userAuthenticationRequest.getPassword())
            );

            final UserDetails userDetails = customUserDetailsService
                    .loadUserByUsername(userAuthenticationRequest.getUsername());

            final String jwt = jwtTokenUtil.generateToken(userDetails);

            httpHeaders.set("Web Token", jwt);
            Optional<LocalUser> localUser = repo.findLocalUserByUsername(userAuthenticationRequest.getUsername());
            LocalUserViewModel localUserViewModel = createViewModel(localUser.map(LocalUser::new).get());

            return new ResponseEntity<>(localUserViewModel, httpHeaders, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(httpHeaders, HttpStatus.NOT_FOUND);
        } catch(BadCredentialsException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<LocalUserViewModel> createLocalUser(LocalUser localUser) {
        try {
            repo.save(localUser);

            LocalUserViewModel localUserViewModel = createViewModel(localUser);

            return new ResponseEntity<>(localUserViewModel, httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            // todo: Catch Exception to return response based on error
            // todo: i.e: password error, username duplication, ect
            System.out.println(e.getMessage());
            return new ResponseEntity<>(httpHeaders, HttpStatus.CONFLICT);
        }
    }

    private LocalUserViewModel createViewModel(LocalUser localUser) {
        return new LocalUserViewModel(
                localUser.getUsername(),
                localUser.isDark_mode()
        );
    }

}
