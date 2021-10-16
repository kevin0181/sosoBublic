package soso.sosoproject.service.admin.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.BlogCategoryDTO;
import soso.sosoproject.dto.BlogDTO;
import soso.sosoproject.dto.BlogImgDTO;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.repository.AccountRepository;
import soso.sosoproject.repository.BlogCategoryRepository;
import soso.sosoproject.repository.BlogImgRepository;
import soso.sosoproject.repository.BlogRepository;

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


    public void insertBlog(Long blogSq, Long memberSq, String blogTitle, Long blogCategorySq) {
        blogDTO.setBlogSq(blogSq);
        blogDTO.setMemberSq(memberSq);
        blogDTO.setBlogTitle(blogTitle);
        blogDTO.setBlogCategorySq(blogCategorySq);
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
}
