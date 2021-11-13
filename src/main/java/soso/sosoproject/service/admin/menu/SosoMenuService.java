package soso.sosoproject.service.admin.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.SosoMenuDTO;
import soso.sosoproject.repository.SosoMenuRepository;

@Service
public class SosoMenuService {

    @Autowired
    private SosoMenuRepository sosoMenuRepository;


    public void saveMenu(SosoMenuDTO sosoMenuDTO) {
        sosoMenuRepository.save(sosoMenuDTO);
    }
}
