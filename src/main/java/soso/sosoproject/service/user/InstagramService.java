package soso.sosoproject.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import soso.sosoproject.dto.InstagramTagDTO;
import soso.sosoproject.repository.InstagramRepository;

import java.util.List;

@Service
public class InstagramService {

    private final WebClient webClient;

    public InstagramService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.instagram.com").build();
    }

    @Autowired
    private InstagramRepository instagramRepository;

    public List<InstagramTagDTO> getTagList() {
        return instagramRepository.findAll();
    }

    public String searchTag(String tagName) {
        String response =
                this.webClient.get().uri("/explore/tags/" + tagName + "/?__a=1&max_id=1")
                        .retrieve().bodyToMono(String.class)
                        .block();
        return response;
    }

}
