package soso.sosoproject.service.admin.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.SosoMenuDTO;
import soso.sosoproject.repository.SosoMenuRepository;

import java.util.List;

@Service
public class SosoMenuService {

    @Autowired
    private SosoMenuRepository sosoMenuRepository;

    public List<SosoMenuDTO> findAllSosoList() {
        return sosoMenuRepository.findAll();
    }

    public void saveMenu(SosoMenuDTO sosoMenuDTO) {
        sosoMenuRepository.save(sosoMenuDTO);
    }

    public void deleteMenu(Long sosoMenuSq) {
        sosoMenuRepository.deleteById(sosoMenuSq);
    }
}
