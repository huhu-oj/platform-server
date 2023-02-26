package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.client.ManagerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 下午10:56
 */
@RestController
@RequestMapping("/language")
@RequiredArgsConstructor
public class LanguageController {
    private final ManagerClient managerClient;
    @GetMapping
    public ResponseEntity<Object> getLanguages() {
        return new ResponseEntity<>(managerClient.getLanguages(),HttpStatus.OK);
    }
}
