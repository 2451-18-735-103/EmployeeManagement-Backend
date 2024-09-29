//package com.employee.demo.jwt;
//
//import com.employee.demo.model.User;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class JwtAuthenticationController {
//    private final JwtToken tokenService;
//
//    private final AuthenticationManager authenticationManager;
//
//    public JwtAuthenticationController(JwtToken tokenService,
//                                       AuthenticationManager authenticationManager) {
//        this.tokenService = tokenService;
//        this.authenticationManager = authenticationManager;
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<JwtTokenResponse> generateToken(@RequestBody User user) {
//
//        var authenticationToken = new UsernamePasswordAuthenticationToken(
//                user.getUsername(),
//                user.getPassword());
//
//        var authentication = authenticationManager.authenticate(authenticationToken);
//
//        var token = tokenService.generateToken(authentication);
//
//        return ResponseEntity.ok(new JwtTokenResponse(token));
//    }
//}
