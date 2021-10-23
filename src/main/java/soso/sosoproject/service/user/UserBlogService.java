package soso.sosoproject.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.BlogCategoryDTO;
import soso.sosoproject.dto.BlogCommentDTO;
import soso.sosoproject.dto.BlogDTO;
import soso.sosoproject.repository.BlogCategoryRepository;
import soso.sosoproject.repository.BlogCommentRepository;
import soso.sosoproject.repository.BlogRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserBlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    @Autowired
    private BlogCommentRepository blogCommentRepository;

    public Page<BlogDTO> getBlogIdPage(int blogSq) {
        Pageable pageable = PageRequest.of(blogSq, 5);

        return blogRepository.findAllByOrderByBlogSqDesc(pageable);
    }

    public List<BlogDTO> getBlogViewSize() {
        return blogRepository.findAllByOrderByBlogViewSizeDesc();
    }

    public List<BlogCategoryDTO> getCategoryList() {
        return blogCategoryRepository.findAll();
    }

    public List<BlogDTO> getFindCategoryBlogList(Long categoryName) {
        return blogRepository.findAllByBlogCategorySqOrderByBlogDateDesc(categoryName);
    }

    @Transactional
    public void saveComment(BlogCommentDTO blogCommentDTO) {
        // 현재 날짜/시간
        LocalDateTime now = LocalDateTime.now();

        // 포맷팅
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));

        blogCommentDTO.setBlogCommentDate(formatedNow);

        blogCommentRepository.save(blogCommentDTO);
    }

    @Transactional
    public Optional<BlogDTO> findBlog(Long blogSq) {
        return blogRepository.findById(blogSq);
    }

    //댓글 삭제
    public void deleteComment(Long blogCommentSq) {
        blogCommentRepository.deleteById(blogCommentSq);
    }

    public Optional<BlogCommentDTO> findCommentMember(Long getCommentMember) {
        return blogCommentRepository.findById(getCommentMember);
    }

    public void saveViewBlog(BlogDTO blogDTO) {
        blogRepository.save(blogDTO);
    }
}
