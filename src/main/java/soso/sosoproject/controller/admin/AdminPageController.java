package soso.sosoproject.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import soso.sosoproject.dto.*;
import soso.sosoproject.dto.detail.UserDetail;
import soso.sosoproject.service.admin.blog.AdminBlogService;
import soso.sosoproject.service.admin.member.AdminMemberService;
import soso.sosoproject.service.admin.menu.MenuService;
import soso.sosoproject.service.admin.menu.SosoMenuService;
import soso.sosoproject.service.order.PasOrderService;
import soso.sosoproject.service.order.SosoOrderService;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminPageController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private AdminMemberService adminMemberService;
    @Autowired
    private AdminBlogService adminBlogService;

    @Autowired
    private PasOrderService pasOrderService;
    @Autowired
    private SosoMenuService sosoMenuService;
    @Autowired
    private SosoOrderService sosoOrderService;


    //인덱스
    @GetMapping("index")
    public String index(@AuthenticationPrincipal UserDetail userDetail,
                        @RequestParam(value = "className", defaultValue = "index") String className,
                        Model model, HttpSession session) {

        //회원정보 객체 가져옴 (eamil,name session에 저장)
        MemberDTO memberDTO = userDetail.getMemberDTO();
        session.setAttribute("memberName", memberDTO.getMemberName());
        session.setAttribute("memberEMail", memberDTO.getMemberEmail());

        // total 회원 수 count
        int totalCnt = adminMemberService.memberCount();

        model.addAttribute("totalCnt", totalCnt);
        model.addAttribute("className", className);

        //pas 총 주문수
        List<PasOrderDTO> pasList = pasOrderService.findAllPlaceOrder();
        model.addAttribute("totalPas", pasList.size());

        //pas 총 주문수
        List<SosoOrderDTO> sosoList = sosoOrderService.findAllPlaceOrder();
        model.addAttribute("totalSoso", sosoList.size());
        model.addAttribute("sosoList", sosoList);

        return "admin/admin-index";
    }

    //로그인
    @GetMapping("login")
    public String adminLogin() {
        return "admin/admin-login";
    }

    //메뉴추가
    @GetMapping("/add-menu")
    public String addMenu(@RequestParam(value = "className", defaultValue = "add-menu") String className,
                          @RequestParam(value = "condition", required = false) String condition,
                          @RequestParam(value = "menu_sq", required = false) Long menu_sq, //비활성화 버튼
                          @RequestParam(value = "active", required = false) boolean active, //버튼 처리&&검색처리(활성화,비활성화)
                          @RequestParam(value = "pageId", defaultValue = "0") int pageId, //페이징 처리
                          @RequestParam(value = "searchText", required = false) String searchText, //검색 처리 (메뉴)
                          @RequestParam(value = "searchCategory", required = false) Long searchCategory,
                          Model model) {

        if (condition != null) {
            if (condition.equals("active")) {
                menuService.changeActive(menu_sq, active);
            }
        }

        if (searchCategory != null) {
            List<PasMenuDTO> pasMenuDTOList = menuService.searchCategory(searchCategory);
            model.addAttribute("menuList", pasMenuDTOList);

            //카테고리 리스트 가져오는 부분
            List<MenuCategoryDTO> categoryList = menuService.getCategoryList();
            model.addAttribute("categoryList", categoryList);

            return "admin/add-menu";
        }

        if (searchText != null) {
            if (!searchText.equals("")) {
                //active 변경
                if (searchText.equals("activeYes")) {
                    if (active) {
                        searchActive(active, model, className);
                        return "admin/add-menu";
                    }
                } else if (searchText.equals("activeNo")) {
                    if (!active) {
                        searchActive(active, model, className);
                        return "admin/add-menu";
                    }
                }


                List<PasMenuDTO> pasMenuDTOS = menuService.getSearch(searchText);
                //검색해온 리스트
                model.addAttribute("menuList", pasMenuDTOS);
                //카테고리 리스트 가져오는 부분
                List<MenuCategoryDTO> categoryList = menuService.getCategoryList();
                model.addAttribute("categoryList", categoryList);

                //페이지 active 구분
                model.addAttribute("className", className);

                return "admin/add-menu";
            }
        }

        //메뉴 리스트 가져오는 부분
        Page<PasMenuDTO> menuList = menuService.getMenuList(pageId);

        int startSize = 1;
        int maxSize = 5;
        //5,6,7,8,9     //10,11,12,13,14
        if (pageId >= 5) {

            startSize = pageId / maxSize;
            maxSize = (startSize + 1) * maxSize;

            startSize = maxSize - 4;
        }
        if (maxSize >= menuList.getTotalPages()) {
            maxSize = menuList.getTotalPages();
        }

        model.addAttribute("menuList", menuList);
        model.addAttribute("startSize", startSize);
        model.addAttribute("maxSize", maxSize);


        //카테고리 리스트 가져오는 부분
        List<MenuCategoryDTO> categoryList = menuService.getCategoryList();
        model.addAttribute("categoryList", categoryList);


        //페이지 active 구분
        model.addAttribute("className", className);
        return "admin/add-menu";
    }

    //카테고리 추가
    @GetMapping("/add-category")
    public String addCategory(@RequestParam(value = "className", required = false) String className,
                              @RequestParam(value = "condition", required = false) String condition,
                              @RequestParam(value = "category_id", required = false) Long categoryId,
                              @RequestParam(value = "category_name", required = false) String categoryName,
                              Model model) {


        if (condition != null) {
            if (condition.equals("change")) {
                //바뀐 카테고리 이름을 새로운 dto에 주입
                MenuCategoryDTO menuCategoryDTO = new MenuCategoryDTO();
                menuCategoryDTO.setCategory_sq(categoryId);
                menuCategoryDTO.setCategoryName(categoryName);
                menuService.changeCategory(menuCategoryDTO);
            } else if (condition.equals("delete")) {
                menuService.deleteCategory(categoryId);
            }
        }

        //카테고리 리스트 가져오는 부분
        List<MenuCategoryDTO> categoryList = menuService.getCategoryList();
        model.addAttribute("categoryList", categoryList);


        //페이지 active 구분
        if (className == null) {
            className = "add-category";
        }
        model.addAttribute("className", className);
        return "admin/add-category";
    }

    @GetMapping("/soso/addMenu") //소소 메뉴 추가
    public String addSosoMenu(@RequestParam(value = "className", defaultValue = "sosoMenu") String className, Model model) {

        List<SosoMenuDTO> sosoMenuDTOList = sosoMenuService.findAllSosoList();

        model.addAttribute("sosoMenuList", sosoMenuDTOList);

        //active
        model.addAttribute("className", className);
        return "admin/soso-add-menu";
    }

    @GetMapping("MemberList")
    public String memberList(@RequestParam(value = "className", defaultValue = "MemberList") String className, Model model) {

        //멤버 리스트
        List<MemberDTO> memberDTOS = adminMemberService.getMemberList();
        model.addAttribute("memberList", memberDTOS);
        //active
        model.addAttribute("className", className);

        return "admin/member/MemberList";
    }


    @GetMapping("/memberList/detail")
    public String memberDetail(@RequestParam(value = "memberSq") Long memberSq, Model model) {
        MemberDTO memberDTOS = adminMemberService.getMemberDetail(memberSq);
        model.addAttribute("memberDTO", memberDTOS);
        return "admin/member/memberDetail";
    }

    @GetMapping("/member/detail/orderList")
    public String memberDetailOrderList(@RequestParam(value = "memberSq") Long memberSq,
                                        @RequestParam(value = "place") String place,
                                        Model model) {
        if (place.equals("soso")) {

            List<SosoOrderDTO> sosoOrderDTOS = sosoOrderService.findMemberOrderList(memberSq);
            model.addAttribute("sosoOrderDTOS", sosoOrderDTOS);
            model.addAttribute("placeName", "소소한 부엌");
            model.addAttribute("place","soso");

        } else if (place.equals("pas")) {

            List<PasOrderDTO> pasOrderDTOS = pasOrderService.findMemberOrderList(memberSq);
            model.addAttribute("pasOrderDTOS", pasOrderDTOS);
            model.addAttribute("placeName", "앤 빠스떼우");
            model.addAttribute("place","pas");

        }
        return "admin/member/memberOrderDetailList";
    }


    //블로그로 이동
    @GetMapping("Blog")
    public String Blog(@RequestParam(value = "className", defaultValue = "Blog") String className, Model model) {

        List<BlogCategoryDTO> blogCategoryDTOS = adminBlogService.getCategoryList();
        List<BlogDTO> blogDTOS = adminBlogService.getBlogList();
        int result = blogDTOS.size() - 1;
        //블로그 순서
        if (blogDTOS.size() != 0) {
            model.addAttribute("blogIndex", blogDTOS.get(result).getBlogSq() + 1);
        } else {
            model.addAttribute("blogIndex", 1);
        }
        Collections.reverse(blogDTOS); //리스트 반대로 뒤집기.

        model.addAttribute("blogList", blogDTOS);
        //블로그 카테고리 리스트
        model.addAttribute("blogCategoryList", blogCategoryDTOS);

        //active
        model.addAttribute("className", className);
        return "admin/Blog/AdminBlog";
    }



    //함수-------------------------------------------------------------------------------
    private void searchActive(boolean active, Model model, String className) {
        List<PasMenuDTO> pasMenuDTOList = menuService.getActiveSearch(active);
        model.addAttribute("menuList", pasMenuDTOList);

        //카테고리 리스트 가져오는 부분
        List<MenuCategoryDTO> categoryList = menuService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        //페이지 active 구분
        model.addAttribute("className", className);

    }

}
