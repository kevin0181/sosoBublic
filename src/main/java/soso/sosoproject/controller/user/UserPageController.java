package soso.sosoproject.controller.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import soso.sosoproject.dto.*;
import soso.sosoproject.dto.detail.UserDetail;
import soso.sosoproject.service.admin.menu.MenuService;
import soso.sosoproject.service.user.UserBlogService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;


@Controller
public class UserPageController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserBlogService userBlogService;

    @GetMapping("/")
    public String start(@AuthenticationPrincipal UserDetail userDetail, Model model, Principal principal) {

        getSection(model);

        return "/user/index";
    }


    @GetMapping("/user/index")
    public String index(@AuthenticationPrincipal UserDetail userDetail, Model model, HttpSession session, MemberDTO memberDTO) {
        if (userDetail == null) {
            getSection(model);

            return "/user/index";
        }

        //회원정보 객체 가져옴 (eamil,name session에 저장)
        memberDTO = userDetail.getMemberDTO();
        session.setAttribute("memberName", memberDTO.getMemberName());
        session.setAttribute("memberEMail", memberDTO.getMemberEmail());

        getSection(model);

        return "/user/index";
    }

    @GetMapping("/user/menu")
    public String menu(Model model) {
        getSection(model);
        return "/user/menu";
    }

    @GetMapping("/user/blog")
    public String blog(@RequestParam(name = "blogPageSize", defaultValue = "0") int blogPageSize,
                       Model model) {


        Page<BlogDTO> blogDTOList = userBlogService.getBlogIdPage(blogPageSize);

        int startSize = 1;
        int maxSize = 5;
        //5,6,7,8,9     //10,11,12,13,14
        if (blogPageSize >= 5) {

            startSize = blogPageSize / maxSize;
            maxSize = (startSize + 1) * maxSize;

            startSize = maxSize - 4;
        }
        if (maxSize >= blogDTOList.getTotalPages()) {
            maxSize = blogDTOList.getTotalPages();
        }

        model.addAttribute("blogDTOList", blogDTOList);
        model.addAttribute("maxSize", maxSize);
        model.addAttribute("startSize", startSize);

        List<BlogDTO> getViewBlogDTO = userBlogService.getBlogViewSize();
        List<BlogCategoryDTO> blogCategoryDTOS = userBlogService.getCategoryList();
        model.addAttribute("blogViewSize", getViewBlogDTO);


        List<CategorySizeBlogClass> getCategortSizeList = new ArrayList<>();
        int resultSize = 0;

        for (int i = 0; i < blogCategoryDTOS.size(); i++) {
            CategorySizeBlogClass categorySizeBlogClass = new CategorySizeBlogClass();
            for (int j = 0; j < getViewBlogDTO.size(); j++) {
                if (blogCategoryDTOS.get(i).getCategory_sq() == getViewBlogDTO.get(j).getBlogCategorySq()) {
                    resultSize++;
                }
            }
            categorySizeBlogClass.setCategorySq(blogCategoryDTOS.get(i).getCategory_sq());
            categorySizeBlogClass.setCategoryName(blogCategoryDTOS.get(i).getBlogCategoryName());
            categorySizeBlogClass.setSize(resultSize);
            getCategortSizeList.add(categorySizeBlogClass);
            resultSize = 0;
        }

        model.addAttribute("blogCategoryList", getCategortSizeList);

        return "/user/blog/blog-home";
    }


    @GetMapping("/user/blog-search")
    public String blogSearch(@RequestParam(name = "categorySq", required = false) Long categorySq, Model model) {
        if (categorySq == 0) {
            return "/user/blog/blog-home";
        } else {
            List<BlogDTO> blogDTOList = userBlogService.getFindCategoryBlogList(categorySq);
            model.addAttribute("blogDTOCategoryList", blogDTOList);

            List<BlogDTO> getViewBlogDTO = userBlogService.getBlogViewSize();
            List<BlogCategoryDTO> blogCategoryDTOS = userBlogService.getCategoryList();
            model.addAttribute("blogViewSize", getViewBlogDTO);

            List<CategorySizeBlogClass> getCategortSizeList = new ArrayList<>();
            int resultSize = 0;

            for (int i = 0; i < blogCategoryDTOS.size(); i++) {
                CategorySizeBlogClass categorySizeBlogClass = new CategorySizeBlogClass();
                for (int j = 0; j < getViewBlogDTO.size(); j++) {
                    if (blogCategoryDTOS.get(i).getCategory_sq() == getViewBlogDTO.get(j).getBlogCategorySq()) {
                        resultSize++;
                    }
                }
                categorySizeBlogClass.setCategorySq(blogCategoryDTOS.get(i).getCategory_sq());
                categorySizeBlogClass.setCategoryName(blogCategoryDTOS.get(i).getBlogCategoryName());
                categorySizeBlogClass.setSize(resultSize);
                getCategortSizeList.add(categorySizeBlogClass);
                resultSize = 0;
            }

            model.addAttribute("blogCategoryList", getCategortSizeList);

            return "/user/blog/blog-search";
        }
    }


    //각각의 블로그로 가는 맵핑
    @GetMapping("/user/blog-single")
    public String blogSingle(@RequestParam(name = "blogSq", required = false) Long blogSq,
                             @RequestParam(name = "up", required = false) boolean viewSizeUp, Model model) {
        Optional<BlogDTO> blogDTOOptional = userBlogService.findBlog(blogSq);
        BlogDTO blogDTO = blogDTOOptional.get();
        if (viewSizeUp) {
            int viewResult = 0;
            blogDTO.setBlogViewSize(blogDTO.getBlogViewSize() + 1);
            userBlogService.saveViewBlog(blogDTO);
        }
        model.addAttribute("blogDTO", blogDTO);
        return "/user/blog/blog-single";
    }

    @GetMapping("/user/about")
    public String about() {
        return "/user/about";
    }


    //그냥 만들어둠 나중에 쓸꺼같아서
    @GetMapping("/user/elements")
    public String elements() {
        return "/user/elements";
    }


    //함수--------------------------------------------------------------------------

    //index 페이지 오늘의 메뉴 가지고 오는 함수
    public MenuDTO todayMenu() {
        MenuDTO menuDTO = menuService.getTodayMenu();
        return menuDTO;
    }

    private List<MenuCategoryDTO> getCategory() {
        //카테고리 리스트 가져오는 부분
        List<MenuCategoryDTO> categoryList = menuService.getCategoryList();
        return categoryList;
    }

    private List<MenuDTO> getMenuList() {
        return menuService.AllMenu();
    }

    private void getSection(Model model) {
        //오늘의 메뉴 가져옴
        MenuDTO todayMenu = todayMenu();
        model.addAttribute("todayMenu", todayMenu);

        //카테고리 가져옴
        List<MenuCategoryDTO> menuCategoryDTO = getCategory();
        model.addAttribute("category", menuCategoryDTO);

        //메뉴 리스트
        List<MenuDTO> menuDTOList = getMenuList();
        model.addAttribute("menu", menuDTOList);
    }


}

@Getter
@Setter
class CategorySizeBlogClass {
    Long categorySq;
    String categoryName;
    int size;
}
