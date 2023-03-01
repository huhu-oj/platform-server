package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.client.ManagerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 下午10:54
 */
@RestController
@RequestMapping("/api/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {
    private final ManagerClient managerClient;
    @AuthorizationRequired
    @GetMapping
    public ResponseEntity<Object> getKnowledgeById(Long id) {
        return new ResponseEntity<>(managerClient.getKnowledgeById(id),HttpStatus.OK);
    }
}
