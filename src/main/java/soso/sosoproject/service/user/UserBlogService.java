package soso.sosoproject.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.BlogCategoryDTO;
import soso.sosoproject.dto.BlogDTO;
import soso.sosoproject.repository.BlogCategoryRepository;
import soso.sosoproject.repository.BlogRepository;

import java.util.List;

@Service
public class UserBlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;


    public Page<BlogDTO> getBlogIdPage(int blogSq) {
        Pageable pageable = PageRequest.of(blogSq, 10);

        return blogRepository.findAllByOrderByBlogSqDesc(pageable);
    }

    public List<BlogDTO> getBlogViewSize() {
        return blogRepository.findAllByOrderByBlogViewSizeDesc();
    }

    public List<BlogCategoryDTO> getCategoryList() {
        return blogCategoryRepository.findAll();
    }

    public List<BlogDTO> getFindCategoryBlogList(String categoryName) {
        return null;
    }
}
