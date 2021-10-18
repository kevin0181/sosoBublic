package soso.sosoproject.controller.admin.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soso.sosoproject.dto.BlogCategoryDTO;
import soso.sosoproject.service.Account.EmailSendService;
import soso.sosoproject.service.admin.blog.AdminBlogService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/blog")
@ResponseBody
public class BlogApiController {

    @Autowired
    private AdminBlogService adminBlogService;


    //블로그 카테고리 추가 변경 삭제
    @GetMapping("blog-category")
    public BlogCategoryDTO addBlogCategory(@RequestParam(name = "categoryName", required = false) String blogCategoryName,
                                           @RequestParam(name = "categoryId", required = false) Long id,
                                           @RequestParam(name = "condition", defaultValue = "none") String condition) {

        BlogCategoryDTO blogCategoryDTO = null;

        if (condition.equals("add")) {
            //블로그 카테고리 추가
            blogCategoryDTO = adminBlogService.insertBlogCategory(blogCategoryName);
            return blogCategoryDTO;

        } else if (condition.equals("change")) {
            //블로그 카테고리 변경
            blogCategoryDTO = adminBlogService.changeInsertBlogCategory(id, blogCategoryName);
            return blogCategoryDTO;

        } else if (condition.equals("delete")) {
            adminBlogService.deleteBlogCategory(id);
        } else if (condition.equals("none")) {
            return null;
        }
        return null;
    }


    //블로그 에디터 사진
    @PostMapping("image")
    public String addBlogImgEditor(@RequestParam(name = "file", required = false) MultipartFile multipartFile,
                                   @RequestParam(name = "BlogId", required = false) Long BlogSq,
                                   @RequestParam(name = "MemberSq", required = false) Long memberSq,
                                   @RequestParam(name = "BlogTitle", required = false) String blogTitle,
                                   @RequestParam(name = "BlogCategory", required = false) Long blogCategorySq,
                                   @RequestParam(name = "condition", required = false) String condition) throws IOException {

        String filePath;
        String lastRnKey;

        if (condition.equals("makeBlogStart")) {
            adminBlogService.insertBlog(BlogSq, memberSq, blogTitle, blogCategorySq);
        } else if (condition.equals("addImg")) {

            // 현재 날짜 구하기
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String nowDate = format.format(now);

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            EmailSendService random = new EmailSendService();
            while (true) {
                lastRnKey = random.certified_key();
                List<String> getDBRandomKey = adminBlogService.getBlogImgKeyName(lastRnKey);
                if (getDBRandomKey.isEmpty()) {
                    filePath = "/img/blog/" + BlogSq + "/" + lastRnKey;
                    break;
                }
            }

            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }


            try {
                InputStream inputStream = multipartFile.getInputStream();
                Path pushFilePath = path.resolve(fileName);
                Files.copy(inputStream, pushFilePath, StandardCopyOption.REPLACE_EXISTING);


                adminBlogService.saveBlogImg(fileName, nowDate, filePath, lastRnKey, BlogSq);

            } catch (IOException e) {
                throw new IOException("파일업로드 안됌");
            }

            return filePath + "/" + fileName;
        }
        return null;
    }

    @PostMapping("saveBlog")
    public String saveBlog(@RequestParam(name = "file", required = false) MultipartFile multipartFile,
                           @RequestParam(name = "BlogId", required = false) Long BlogSq,
                           @RequestParam(name = "MemberSq", required = false) Long memberSq,
                           @RequestParam(name = "BlogTitle", required = false) String blogTitle,
                           @RequestParam(name = "BlogCategory", required = false) Long blogCategorySq,
                           @RequestParam(name = "blogContant", required = false) String blogContant) throws IOException {

        adminBlogService.saveBlog(multipartFile, BlogSq, memberSq, blogTitle, blogCategorySq, blogContant);
        return null;
    }

    @GetMapping("/blogList/changeActive")
    public void activeChange(@RequestParam(name = "active") boolean active,
                             @RequestParam(name = "blogSq") Long blogSq) {
        adminBlogService.changeActive(active, blogSq);
    }

    @GetMapping("deleteBlog")
    public boolean deleteBlog(@RequestParam(name = "blogCheck[]") List<Long> blogSq) throws IOException, InterruptedException {
        adminBlogService.deleteBlog(blogSq);
        return true;
    }

}
