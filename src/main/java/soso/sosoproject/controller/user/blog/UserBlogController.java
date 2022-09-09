package soso.sosoproject.controller.user.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import soso.sosoproject.dto.BlogCommentDTO;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.RoleDTO;
import soso.sosoproject.message.AccountMessage;
import soso.sosoproject.service.user.UserBlogService;
import soso.sosoproject.service.user.UserRoleService;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserBlogController {

    @Autowired
    private UserBlogService userBlogService;
    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/user/blog/comment")
    public String getComment(BlogCommentDTO blogCommentDTO, Model model) {

        userBlogService.saveComment(blogCommentDTO);
        model.addAttribute("data", new AccountMessage("/user/blog-single?blogSq=" + blogCommentDTO.getBlogSq()));
        return "/message/account-message";
    }

    @GetMapping("/user/blog/delete")
    public String deleteComment(@RequestParam(name = "memberSq") Long memberSq,
                                @RequestParam(name = "getCommentMember") Long getCommentMember,
                                @RequestParam(name = "blogCommentSq") Long blogCommentSq,
                                @RequestParam(name = "blogSq") Long blogSq,
                                HttpSession session, Model model) {

        Long sessionSq = (Long) session.getAttribute("memberSq");
        Optional<MemberDTO> roleDTOOptional = userRoleService.getRole(sessionSq);
        if (!roleDTOOptional.isPresent()) {
            model.addAttribute("data", new AccountMessage("잘못된 접근입니다.", "/user/blog-single?blogSq=" + blogSq));
            return "/message/account-message";
        }
        MemberDTO memberDTO = roleDTOOptional.get();

        Set<RoleDTO> roles = memberDTO.getRole();
        List<String> list = new ArrayList<>();
        for (RoleDTO role : roles) {
            list.add(role.getRoleName());
        }

        if (list.get(0).equals("ROLE_ADMIN")) {
            if (sessionSq == memberSq) {
                userBlogService.deleteComment(blogCommentSq);
                model.addAttribute("data", new AccountMessage("/user/blog-single?blogSq=" + blogSq));
            }
        } else {
            if (sessionSq == memberSq && sessionSq == getCommentMember) {
                Optional<BlogCommentDTO> blogCommentMemberSqRedirect = userBlogService.findCommentMember(blogCommentSq);
                if (sessionSq == blogCommentMemberSqRedirect.get().getMemberSq()) {
                    userBlogService.deleteComment(blogCommentSq);
                }
                model.addAttribute("data", new AccountMessage("/user/blog-single?blogSq=" + blogSq));
            } else {
                model.addAttribute("data", new AccountMessage("댓글을 삭제할 수 없습니다.", "/user/blog-single?blogSq=" + blogSq));
            }
        }

        return "/message/account-message";
    }
}
