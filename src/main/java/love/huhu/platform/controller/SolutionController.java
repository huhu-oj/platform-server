package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.client.ManagerClient;
import love.huhu.platform.domain.Solution;
import love.huhu.platform.service.dto.SolutionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 下午10:59
 */
@RestController
@RequestMapping("/api/solution")
@RequiredArgsConstructor
public class SolutionController {
    private final ManagerClient managerClient;
    @AuthorizationRequired
    @GetMapping
    public ResponseEntity<Object> getSolutions(Long problemId, Long solutionId) {
        return new ResponseEntity<>(managerClient.getSolution(problemId,solutionId,null),HttpStatus.OK);
    }
    @AuthorizationRequired
    @GetMapping("my")
    public ResponseEntity<Object> getMySolutions(Long problemId, Long solutionId) {
        return new ResponseEntity<>(managerClient.getSolution(problemId,solutionId,UserHolder.getUserId()),HttpStatus.OK);
    }
    @AuthorizationRequired
    @PostMapping
    public ResponseEntity<Object> saveSolution(@RequestBody SolutionDto solution) {
        solution.setUserId(UserHolder.getUserId());
        return new ResponseEntity<>(managerClient.saveSolution(solution),HttpStatus.OK);
    }
    @AuthorizationRequired
    @PutMapping
    public ResponseEntity<Object> updateSolution(@RequestBody SolutionDto solution) {
        return new ResponseEntity<>(managerClient.updateSolution(solution),HttpStatus.OK);
    }
    @AuthorizationRequired
    @DeleteMapping
    public ResponseEntity<Object> deleteSolution(Long solutionId) {
        return new ResponseEntity<>(managerClient.deleteSolutions(new Long[]{solutionId}),HttpStatus.OK);
    }
}
