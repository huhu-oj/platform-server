package love.huhu.platform.controller;

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
@RequestMapping("/knowledge")
public class KnowledgeController {
    @GetMapping
    public ResponseEntity<Object> getKnowledgeById(Long id) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
