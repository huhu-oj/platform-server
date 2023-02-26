package love.huhu.platform.controller;

import love.huhu.platform.domain.Solution;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 下午10:59
 */
@RestController
@RequestMapping("/solution")
public class SolutionController {
    @GetMapping
    public ResponseEntity<Object> getSolutions(Long problemId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{solutionId}")
    public ResponseEntity<Object> getSolution(@PathVariable Long solutionId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Object> saveSolution(@RequestBody Solution solution) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Object> updateSolution(@RequestBody Solution solution) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<Object> deleteSolution(Long solutionId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
