package com.inspire12.homepage.controller.security;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inspire12.homepage.interceptor.MethodAllowLevel;
import com.inspire12.homepage.domain.model.User;
import com.inspire12.homepage.message.DefaultResponse;
import com.inspire12.homepage.message.EmailRequest;
import com.inspire12.homepage.message.SignupRequest;
import com.inspire12.homepage.security.AuthProvider;
import com.inspire12.homepage.security.UserDetailService;

import com.inspire12.homepage.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class SecurityController implements ErrorController {
    private final AuthProvider authProvider;
    private final UserDetailService userDetailService;
    private final EmailService emailService;
    private final RedisTemplate<String, String> redisTemplate;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @PostMapping(value = "/valid-email")
    @ResponseBody
    public ResponseEntity<String> registerUser(@Valid @RequestBody final EmailRequest requestBody, RedirectAttributes redirectAttributes) throws InvalidKeyException, NoSuchAlgorithmException {
        String email = requestBody.getEmail();
        String token = emailService.getCertifyTokenByMail(email);
        redisTemplate.opsForValue().set(email, token);
        return ResponseEntity.ok().body(token);
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DefaultResponse> registerUser(@Valid @RequestBody final SignupRequest requestBody, RedirectAttributes redirectAttributes) throws InvalidKeyException, NoSuchAlgorithmException {
        String username = requestBody.getUsername();
        String password = requestBody.getPassword();
        String email = requestBody.getEmail();
        Integer studentId = Integer.parseInt(requestBody.getStudentId());
        String realName = requestBody.getRealName();


        if (Objects.equals(redisTemplate.opsForValue().get(email), requestBody.getEmailToken()) == false){
            DefaultResponse response = new DefaultResponse("signup", "fail", "email_valid");
            return ResponseEntity.badRequest().body(response);
        }

        String encryptedPassword = authProvider.encrypt(username, password);
        User user = User.create(username, email, encryptedPassword, studentId, realName);

        // 중복 체크 추가
        if (userDetailService.isExistUser(user)) {
            DefaultResponse response = new DefaultResponse("signup", "fail", "duplicated_id");
            return ResponseEntity.unprocessableEntity().body(response);
        }
        userDetailService.saveUser(user);
        DefaultResponse response = new DefaultResponse("index", "signup", null);

        return ResponseEntity.ok().body(response);
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "auth/login";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @RequestMapping(value = "/login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String login(
            @RequestParam Map<String, String> authenticationRequest,
            HttpSession session, RedirectAttributes redirectAttributes
    ) {
        String username = (String)authenticationRequest.get("your_username");
        String password = (String)authenticationRequest.get("your_password");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authProvider.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        User user = userDetailService.readUser(username);
        userDetailService.setLastLoginedAt(username);

        session.setAttribute("user", user);
        redirectAttributes.addFlashAttribute("name", "index");
        redirectAttributes.addFlashAttribute("status", "login");
        redirectAttributes.addFlashAttribute("user", user);
        return "redirect:/index";
    }

    @PostMapping("/password")
    public ResponseEntity setPassword(
            @RequestParam Map<String, String> authenticationRequest,
            HttpSession session, RedirectAttributes redirectAttributes
    ) throws Exception {
        String username = (String)authenticationRequest.get("username");
        String password = (String)authenticationRequest.get("password");
        String newPassword = (String)authenticationRequest.get("new_password");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authProvider.authenticate(token);
        if (! authentication.isAuthenticated()) {
            throw new Exception();
        }
        String encryptedPassword = authProvider.encrypt(username, newPassword);
        if (userDetailService.setNewPassword(username, encryptedPassword) != 1){
            throw new Exception();
        }
        return ResponseEntity.ok().build();
    }
//
    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @RequestMapping(value = "/oauth", method = RequestMethod.POST)
    public ResponseEntity<DefaultResponse> loginFromKakao() {
        return ResponseEntity.ok().body(new DefaultResponse(null, null, null));
    }
//
//    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
//    @RequestMapping(value = "/oauth", method = RequestMethod.GET)
//    public ResponseEntity<DefaultResponse> loginFromKakao2() {
//
//        return ResponseEntity.ok().body(response);
//    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//        logger.error("Exception during execution of SpringSecurity application", throwable);
//        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
//        model.addAttribute("errorMessage", errorMessage);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
        model.addAttribute("code", status.toString());

        if (status.toString().equals("401")) {
            model.addAttribute("msg", "회원을 위한 공간입니다. 가입한 후 사용해주세요~");
        } else {
            model.addAttribute("msg", httpStatus.getReasonPhrase());
        }
        model.addAttribute("timestamp", LocalDateTime.now());

        if (httpStatus.equals(HttpStatus.FORBIDDEN)) {
            return "auth/login";
        }
        return getErrorPath();
    }

    @Override
    public String getErrorPath() {
        return "auth/error";
    }
}
