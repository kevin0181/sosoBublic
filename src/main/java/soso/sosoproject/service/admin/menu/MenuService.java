package soso.sosoproject.service.admin.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import soso.sosoproject.dto.*;
import soso.sosoproject.repository.CategoryRepository;
import soso.sosoproject.repository.ImgRepository;
import soso.sosoproject.repository.PasMenuRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MenuService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PasMenuRepository pasMenuRepository;

    @Autowired
    private ImgRepository imgRepository;

    //카테고리 생성
    public void save_category(MenuCategoryDTO menuCategoryDTO) {
        categoryRepository.save(menuCategoryDTO);
    }

    //카테고리 리스트
    public List<MenuCategoryDTO> getCategoryList() {
        List<MenuCategoryDTO> menuCategoryDTOList = categoryRepository.findAll();
        return menuCategoryDTOList;
    }

    //카테고리 삭제
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    //카테고리 변경
    public void changeCategory(MenuCategoryDTO menuCategoryDTO) {
        Optional<MenuCategoryDTO> getCategoryDTO = categoryRepository.findById(menuCategoryDTO.getCategory_sq());
        menuCategoryDTO.setMenuList(getCategoryDTO.get().getMenuList());
        categoryRepository.save(menuCategoryDTO);
    }

    //메뉴 생성
    public List<PasMenuDTO> save_menu(MenuImgDTO menuImgDTO) {
        List<ImgDTO> imgDTOList = imgRepository.findAllByMenuSq(menuImgDTO.getMenuSq());
        PasMenuDTO pasMenuDTO = new PasMenuDTO(menuImgDTO.getMenuSq(), menuImgDTO.getMenuName(), menuImgDTO.getMenuCategorySq(), menuImgDTO.getMenu_contant(),
                menuImgDTO.getMenu_price(), menuImgDTO.isMenuSoldOut(), menuImgDTO.isMenuEnable(), menuImgDTO.isMenu_today(), imgDTOList);

        pasMenuRepository.save(pasMenuDTO);
        return pasMenuRepository.findAllByMenuName(pasMenuDTO.getMenuName());
    }

    //메뉴 리스트
    public Page<PasMenuDTO> getMenuList(int pageId) {
        Pageable pageable = PageRequest.of(pageId, 10);
        return pasMenuRepository.findAllByOrderByMenuSqDesc(pageable);
    }


    //메뉴 삭제
    public void deleteMenu(List<Long> menuCheck) throws IOException, InterruptedException {

        String dirPath;
        //이미지 삭제 함수
        deleteImg(menuCheck, null);
    }

    //이미지 삭제
    public void deleteImg(List<Long> menuCheck, List<Long> imgsq) throws IOException, InterruptedException {

        String fileName;
        String imgDate = null;
        String filePath;
        String dirPath;
        List<ImgDTO> deleteImg = null;

        if (menuCheck != null && imgsq == null) {
            for (int i = 0; i < menuCheck.size(); i++) {
                deleteImg = imgRepository.findAllByMenuSq(menuCheck.get(i));
                for (int j = 0; j < deleteImg.size(); j++) {
                    fileName = deleteImg.get(j).getImg_name();
                    imgDate = deleteImg.get(j).getImg_date();
                    filePath = "/img/menu/" + menuCheck.get(i) + "/" + imgDate + "/" + fileName;
                    Path path = Paths.get(filePath);
                    if (Files.exists(path)) {
                        Files.delete(path);
                    }
                    Thread.sleep(100);
                }

                //디렉토리 삭제
                dirPath = "/img/menu/" + menuCheck.get(i) + "/" + imgDate;
                Path deleteDirPath = Paths.get(dirPath);
                if (Files.exists(deleteDirPath)) {
                    Files.delete(deleteDirPath);
                }
                Thread.sleep(100);
                dirPath = "/img/menu/" + menuCheck.get(i);
                Path deleteDirPath1 = Paths.get(dirPath);
                if (Files.exists(deleteDirPath1)) {
                    Files.delete(deleteDirPath1);
                }
                Thread.sleep(100);
                imgRepository.deleteAllByMenuSq(menuCheck.get(i));
                pasMenuRepository.deleteById(menuCheck.get(i));
            }

        } else if (menuCheck == null && imgsq != null) {
            for (int i = 0; i < imgsq.size(); i++) {
                deleteImg = imgRepository.findAllById(Collections.singleton(imgsq.get(i)));
                for (int j = 0; j < deleteImg.size(); j++) {
                    fileName = deleteImg.get(j).getImg_name();
                    imgDate = deleteImg.get(j).getImg_date();
                    filePath = "/img/menu/" + deleteImg.get(j).getMenuSq() + "/" + imgDate + "/" + fileName;
                    Path path = Paths.get(filePath);
                    if (Files.exists(path)) {
                        Files.delete(path);
                    }
                }
                imgRepository.deleteAllById(Collections.singleton(imgsq.get(i)));
            }

            List<ImgDTO> count = imgRepository.findAllByMenuSq(deleteImg.get(0).getMenuSq());
            if (count.size() == 0) {
                dirPath = "/img/menu/" + deleteImg.get(0).getMenuSq() + "/" + imgDate;
                Path deleteDirPath = Paths.get(dirPath);
                if (Files.exists(deleteDirPath)) {
                    Files.delete(deleteDirPath);
                }
            }
        }
    }

    //active 변경
    public void changeActive(Long menu_sq, boolean active) {
        Optional<PasMenuDTO> optionalMenuDTO = pasMenuRepository.findById(menu_sq);
        PasMenuDTO pasMenuDTO = optionalMenuDTO.get();
        pasMenuDTO.setMenuEnable(active);
        pasMenuRepository.save(pasMenuDTO);
    }

    //이미지 저장
    public void saveImg(String fileName, String fullPath, Long id, String nowDate) {
        ImgDTO imgDTO = new ImgDTO();
        imgDTO.setImg_name(fileName);
        imgDTO.setImg_path(fullPath);
        imgDTO.setMenuSq(id);
        imgDTO.setImg_date(nowDate);

        imgRepository.save(imgDTO);
    }

    //서버에 이미지 저장(실질적인)
    public boolean saveRealImg(MenuImgDTO menuImgDTO, List<ImgDTO> imgDTO, List<PasMenuDTO> sqPasMenuDTO, int lastMenuSq, Model model) throws IOException {

        String filePath;

        if (menuImgDTO.getMenu_img().get(0).getOriginalFilename().equals("")) {
            model.addAttribute("className", "add-menu");
            return false;
        } else {
            for (int i = 0; i < menuImgDTO.getMenu_img().size(); i++) {
                String fileName = StringUtils.cleanPath(menuImgDTO.getMenu_img().get(i).getOriginalFilename());

                // 현재 날짜 구하기
                Date now = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String nowDate = format.format(now);

                if (imgDTO.isEmpty()) {
                    filePath = "/img/menu/" + sqPasMenuDTO.get(lastMenuSq).getMenuSq() + "/" + nowDate;
                } else {
                    filePath = "/img/menu/" + sqPasMenuDTO.get(lastMenuSq).getMenuSq() + "/" + nowDate;
                }
                Path path = Paths.get(filePath);

                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }

                try {
                    InputStream inputStream = menuImgDTO.getMenu_img().get(i).getInputStream();
                    Path pushFilePath = path.resolve(fileName);
                    Files.copy(inputStream, pushFilePath, StandardCopyOption.REPLACE_EXISTING);
                    saveImg(fileName, pushFilePath.toString(), sqPasMenuDTO.get(lastMenuSq).getMenuSq(), nowDate);
                } catch (IOException e) {
                    throw new IOException("파일업로드 안됌");
                }

            }
        }
        return true;
    }

    //이미지 저장을 위한 시퀀스 가져옴
    public List<ImgDTO> getImgList() {
        List<ImgDTO> imgDTO = imgRepository.findAll();
        return imgDTO;
    }

    //today list 가져옴
    public boolean getTodayList(Long menuSq) {

        List<Long> menuTodayList = pasMenuRepository.findAllByMenuToday(true);

        if (menuTodayList.size() == 0) {
            return true;
        } else {
            return false;
        }

    }

    //메뉴이름 검색
    public List<PasMenuDTO> getSearch(String searchText) {

        List<PasMenuDTO> pasMenuDTOS = pasMenuRepository.findByMenuNameContains(searchText);

        return pasMenuDTOS;
    }

    //active 검색
    public List<PasMenuDTO> getActiveSearch(boolean active) {

        List<PasMenuDTO> pasMenuDTOList = pasMenuRepository.findAllByMenuEnable(active);
        return pasMenuDTOList;
    }

    public List<PasMenuDTO> searchCategory(Long searchCategory) {
        List<PasMenuDTO> pasMenuDTOList = pasMenuRepository.findAllByMenuCategorySq(searchCategory);
        return pasMenuDTOList;
    }

    //오늘의 메뉴 가져옴
    public PasMenuDTO getTodayMenu() {
        boolean id = true;
        return pasMenuRepository.findByMenuToday(id);
    }

    public List<PasMenuDTO> AllMenu() {
        return pasMenuRepository.findAll();
    }

    public int getOrderMenuAmmount(List<PasOrdersDetailDTO> pasOrdersDetailDTOS) {
        int result = 0;
        List<Long> menuId = new ArrayList<>();
        Map<Long, Integer> orderMenuSize = new HashMap<>();
        for (int i = 0; i < pasOrdersDetailDTOS.size(); i++) {
            menuId.add(pasOrdersDetailDTOS.get(i).getMenuSq());
            orderMenuSize.put(pasOrdersDetailDTOS.get(i).getMenuSq(), pasOrdersDetailDTOS.get(i).getMenuOrderSize());
        }

        List<PasMenuDTO> pasMenuDTOList = pasMenuRepository.findAllById(menuId);

        for (int i = 0; i < pasMenuDTOList.size(); i++) {

            result += pasMenuDTOList.get(i).getMenu_price() * orderMenuSize.get(pasMenuDTOList.get(i).getMenuSq());

        }

        return result;
    }
}
