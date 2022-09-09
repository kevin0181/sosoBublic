package soso.sosoproject.controller.admin.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import soso.sosoproject.dto.BlogCategoryDTO;
import soso.sosoproject.dto.BlogDTO;
import soso.sosoproject.service.admin.blog.AdminBlogService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/BlogCn")
public class BlogController {

    @Autowired
    private AdminBlogService adminBlogService;

    @GetMapping("changeBlog")
    public String changeBlog(@RequestParam(name = "blogSq") Long blogSq, Model model) {

        Optional<BlogDTO> blogDTOOptional = adminBlogService.getChangeBlog(blogSq);
        BlogDTO blogDTO = blogDTOOptional.get();

        List<BlogCategoryDTO> blogCategoryDTOS = adminBlogService.getCategoryList();

        model.addAttribute("blogCategoryDTO", blogCategoryDTOS);
        model.addAttribute("blogDTO", blogDTO);

        return "admin/Blog/changeBlog";
    }
}
