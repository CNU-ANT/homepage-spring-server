package com.inspire12.homepage.controller.template;

import com.inspire12.homepage.exception.NotAuthException;
import com.inspire12.homepage.interceptor.MethodAllowLevel;
import com.inspire12.homepage.message.ArticleResponse;
import com.inspire12.homepage.domain.model.User;
import com.inspire12.homepage.service.board.ArticleService;
import com.inspire12.homepage.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final ArticleService articleService;
    private final UserService userService;

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping({"/", "/index"})
    public String index(Model model, HttpSession session) throws Exception {

        User user = (User) session.getAttribute("user");
        model.addAttribute("userInfo", user);
        model.addAttribute("adminUsers", userService.getAdminUsers());
        model.addAttribute("name", "index");
        return "index";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("name", "signup");
        return "auth/signup";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/profile")
    public String getProfile(Model model) {
        model.addAttribute("name", "profiles");
        return "profiles";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/introduce")
    public String getIntroduceView() {
        return "introduce";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/contact")
    public String getContactView() {
        return "contact";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/about")
    public String getAboutView(Model model) {
        model.addAttribute("adminUsers", userService.getAdminUsers());
        return "about";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/blog")
    public String getBlogView() {
        return "blog";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/gallery")
    public String getGalleryView() {
        return "gallery";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/board")
    public String getBoardView(@RequestParam(defaultValue = "0") Integer type, @RequestParam(defaultValue = "30") int articleCount, @RequestParam(defaultValue = "1") int pageNum, Model model) {
        // board 종류
        try {
            List<ArticleResponse> articles = articleService.showArticleMsgsWithCount(type, pageNum, articleCount);
            model.addAttribute("articles", articles);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("name", "board");
        return "board";
    }


    @GetMapping("/privatepolicy")
    public String getPrivatePolicy() {
        return "auth/privatepolicy";
    }

    @GetMapping("/article")
    public String getSingleBlogView(@RequestParam(defaultValue = "1") Long id, Model model) {
        ArticleResponse article;
        try {
            article = articleService.showArticleMsgById(id);
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {

            }
        } catch (Exception e) {
            article = new ArticleResponse();
            e.printStackTrace();
        }
        model.addAttribute("article", article);
        model.addAttribute("name", "article");
        return "article";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.USER)
    @GetMapping("/writing")
    public String getWriteView(Model model,
                               @RequestParam(name = "id", defaultValue = "0") Long id) throws NotAuthException {
        if (id != 0){
            ArticleResponse articleResponse = articleService.getArticleMsgById(id);
//            if (! SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(articleMsg.getAuthor().getUsername())){
//                throw new NotAuthException("작성자만 글을 수정할 수 있습니다.");
//            }
            model.addAttribute("article", articleResponse);
        }
        model.addAttribute("name", "write");
        return "writing";
    }


    @GetMapping("/algorithm")
    public String getAlgorithmView() {
        return "lab/algorithm";
    }

    @GetMapping("/gan-style")
    public String getGanView(Model model) {
        return "lab/gan-styles";
    }

    @GetMapping("/ai")
    public String getAiView() {
        return "lab/ai";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/test")
    public String getGifView() {
        return "lab/test";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/opensource")
    public String getOpensourceView() {
        return "opensource";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/kakao")
    public String getKakaoLabView() {
        return "lab/kakao-login";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/video")
    public String getVideoLabView() {
        return "lab/video";
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/facebook-login")
    public String getFacebookLabView() {
        return "lab/facebook-login";
    }


    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.GUEST)
    @GetMapping("/table")
    public String getTableView() {
        return "lab/table";
    }

}
