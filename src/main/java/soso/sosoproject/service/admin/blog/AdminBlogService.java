package soso.sosoproject.service.admin.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import soso.sosoproject.dto.BlogCategoryDTO;
import soso.sosoproject.dto.BlogDTO;
import soso.sosoproject.dto.BlogImgDTO;
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


    public void insertBlog(Long blogSq, Long memberSq, String blogTitle, Long blogCategorySq) throws IOException {
        List<BlogImgDTO> blogImgDTOList = blogImgRepository.findAllByBlogSq(blogSq);

        //블로그 넣음
        blogDTO.setBlogSq(blogSq);
        blogDTO.setMemberSq(memberSq);
        blogDTO.setBlogTitle(blogTitle);
        blogDTO.setBlogCategorySq(blogCategorySq);
        blogDTO.setBlogViewActive(false);
        blogDTO.setBlogViewSize(0);
        blogDTO.setBlogImg_sq(blogImgDTOList);
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
        } else if (multipartFile == null) {
            Optional<BlogDTO> blogDTOOptional = blogRepository.findById(blogSq);
            blogDTO.setBlogImgKeyname(blogDTOOptional.get().getBlogImgKeyname());
            blogDTO.setBlogTopImgName(blogDTOOptional.get().getBlogTopImgName());
            blogDTO.setBlogTopImgPath(blogDTOOptional.get().getBlogTopImgPath());
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

    public void changeActive(boolean active, Long blogSq) {
        Optional<BlogDTO> blogDTO = blogRepository.findById(blogSq);
        BlogDTO blogDTO1 = blogDTO.get();
        blogDTO1.setBlogViewActive(active);
        blogRepository.save(blogDTO1);
    }

    //블로그 삭제
    public void deleteBlog(List<Long> blogSq) throws IOException, InterruptedException {
        String filePath;
        Path deleteFilePath;

        deleteImg(blogSq);
        deleteTopImg(blogSq);


        for (int i = 0; i < blogSq.size(); i++) {
            filePath = "/img/blog/" + blogSq.get(i);
            deleteFilePath = Paths.get(filePath);
            if (Files.exists(deleteFilePath)) {
                Files.delete(deleteFilePath);
            }
        }

        for (int i = 0; i < blogSq.size(); i++) {
            List<BlogImgDTO> blogImgDTOList = blogImgRepository.findAllByBlogSq(blogSq.get(i));
            for (int j = 0; j < blogImgDTOList.size(); j++) {
                Long imgId = blogImgDTOList.get(j).getBlogImgSq();
                blogImgRepository.deleteById(imgId);
            }
        }

        blogRepository.deleteAllById(blogSq);

    }

    private void deleteImg(List<Long> blogSq) throws IOException, InterruptedException {
        String filePath;
        Path deleteFilePath;

        for (int i = 0; i < blogSq.size(); i++) {
            List<BlogImgDTO> blogImgDTO = blogImgRepository.findAllByBlogSq(blogSq.get(i));
            for (int j = 0; j < blogImgDTO.size(); j++) {
                filePath = blogImgDTO.get(j).getBlogImgPath() + "/" + blogImgDTO.get(j).getBlogImgName();
                deleteFilePath = Paths.get(filePath);
                if (Files.exists(deleteFilePath)) {
                    Files.delete(deleteFilePath);
                }
                filePath = "/img/blog/" + blogImgDTO.get(j).getBlogSq() + "/" + blogImgDTO.get(j).getBlogImgKeyName();
                deleteFilePath = Paths.get(filePath);
                if (Files.exists(deleteFilePath)) {
                    Files.delete(deleteFilePath);
                }

            }
        }

    }

    //이미지 파일 삭제
    private void deleteTopImg(List<Long> blogSq) throws IOException {
        String filePath;
        Path deleteFilePath;
        for (int i = 0; i < blogSq.size(); i++) {
            Optional<BlogDTO> blogDTO = blogRepository.findById(blogSq.get(i));
            filePath = blogDTO.get().getBlogTopImgPath() + "/" + blogDTO.get().getBlogTopImgName();
            deleteFilePath = Paths.get(filePath);
            if (Files.exists(deleteFilePath)) {
                Files.delete(deleteFilePath);
            }

            filePath = "/img/blog/" + blogSq.get(i) + "/" + blogDTO.get().getBlogImgKeyname();
            deleteFilePath = Paths.get(filePath);
            if (Files.exists(deleteFilePath)) {
                Files.delete(deleteFilePath);
            }


        }
    }

    public Optional<BlogDTO> getChangeBlog(Long blogSq) {
        return blogRepository.findById(blogSq);
    }
}
