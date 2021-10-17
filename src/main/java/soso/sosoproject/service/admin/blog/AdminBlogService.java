package soso.sosoproject.service.admin.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import soso.sosoproject.dto.BlogCategoryDTO;
import soso.sosoproject.dto.BlogDTO;
import soso.sosoproject.dto.BlogImgDTO;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.repository.AccountRepository;
import soso.sosoproject.repository.BlogCategoryRepository;
import soso.sosoproject.repository.BlogImgRepository;
import soso.sosoproject.repository.BlogRepository;
import soso.sosoproject.service.Account.EmailSendService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminBlogService {

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;
    @Autowired
    private BlogImgRepository blogImgRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AdminBlogService adminBlogService;

    BlogDTO blogDTO = new BlogDTO();


    public List<BlogCategoryDTO> getCategoryList() {
        return blogCategoryRepository.findAll();
    }

    public BlogCategoryDTO insertBlogCategory(String blogCategoryName) {
        BlogCategoryDTO blogCategoryDTO = new BlogCategoryDTO();
        blogCategoryDTO.setBlogCategoryName(blogCategoryName);
        return blogCategoryRepository.save(blogCategoryDTO);
    }

    public BlogCategoryDTO changeInsertBlogCategory(Long id, String blogCategoryName) {

        Optional<BlogCategoryDTO> getBlog = blogCategoryRepository.findById(id);

        BlogCategoryDTO blogCategoryDTO = new BlogCategoryDTO();
        blogCategoryDTO.setCategory_sq(id);
        blogCategoryDTO.setBlogCategoryName(blogCategoryName);
        blogCategoryDTO.setBlogList(getBlog.get().getBlogList());

        return blogCategoryRepository.save(blogCategoryDTO);
    }

    public void deleteBlogCategory(Long id) {
        blogCategoryRepository.deleteById(id);
    }

    public List<String> getBlogImgKeyName(String random) {
        return blogImgRepository.findByBlogImgKeyName(random);
    }

    public List<BlogDTO> getBlogList() {
        return blogRepository.findAll();
    }


    public void insertBlog(Long blogSq, Long memberSq, String blogTitle, Long blogCategorySq, MultipartFile multipartFile) throws IOException {

        String lastRnKey;
        String filePath;

        if (multipartFile != null) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            EmailSendService random = new EmailSendService();
            while (true) {
                lastRnKey = random.certified_key();
                List<String> getDBRandomKey = adminBlogService.getBlogImgKeyName(lastRnKey);
                if (getDBRandomKey.isEmpty()) {
                    filePath = "/img/blog/" + blogSq + "/" + lastRnKey;
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


                blogDTO.setBlogTopImgName(fileName);
                blogDTO.setBlogTopImgPath(filePath);
                blogDTO.setBlogImgKeyname(lastRnKey);

            } catch (IOException e) {
                throw new IOException("파일업로드 안됌");
            }
        }

        //블로그 넣음
        blogDTO.setBlogSq(blogSq);
        blogDTO.setMemberSq(memberSq);
        blogDTO.setBlogTitle(blogTitle);
        blogDTO.setBlogCategorySq(blogCategorySq);
        blogDTO.setBlogViewActive(false);
        blogRepository.save(blogDTO);
    }

    public void saveBlogImg(String fileName, String nowDate, String filePath, String lastRnKey, Long blogSq) {

        BlogImgDTO blogImgDTO = new BlogImgDTO();
        blogImgDTO.setBlogSq(blogSq);
        blogImgDTO.setBlogImgName(fileName);
        blogImgDTO.setBlogImgDate(nowDate);
        blogImgDTO.setBlogImgKeyName(lastRnKey);
        blogImgDTO.setBlogImgPath(filePath);
        blogImgRepository.save(blogImgDTO);
    }

    //실질적인 마지막 저장
    public void saveBlog(MultipartFile multipartFile, Long blogSq, Long memberSq, String blogTitle, Long blogCategorySq, String blogContant) throws IOException {

        String lastRnKey;
        String filePath;

        if (multipartFile != null) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            EmailSendService random = new EmailSendService();
            while (true) {
                lastRnKey = random.certified_key();
                List<String> getDBRandomKey = adminBlogService.getBlogImgKeyName(lastRnKey);
                if (getDBRandomKey.isEmpty()) {
                    filePath = "/img/blog/" + blogSq + "/" + lastRnKey;
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


                blogDTO.setBlogTopImgName(fileName);
                blogDTO.setBlogTopImgPath(filePath);
                blogDTO.setBlogImgKeyname(lastRnKey);

            } catch (IOException e) {
                throw new IOException("파일업로드 안됌");
            }
        }

        List<BlogImgDTO> blogImgDTOList = blogImgRepository.findAllByBlogSq(blogSq);

        // 현재 날짜 구하기
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String nowDate = format.format(now);

        blogDTO.setBlogSq(blogSq);
        blogDTO.setMemberSq(memberSq);
        blogDTO.setBlogTitle(blogTitle);
        blogDTO.setBlogCategorySq(blogCategorySq);
        blogDTO.setBlogViewActive(false);
        blogDTO.setBlogContant(blogContant);
        blogDTO.setBlogDate(nowDate);
        blogDTO.setBlogImg_sq(blogImgDTOList);
        blogRepository.save(blogDTO);
    }
}
