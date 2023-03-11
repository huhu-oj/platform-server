package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.PermissionEnum;
import love.huhu.platform.client.ManagerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-28 下午9:35
 */
@RestController
@RequestMapping("/api/executeResult")
@RequiredArgsConstructor
public class ExecuteResultController {

    private final ManagerClient managerClient;
    @AuthorizationRequired({PermissionEnum.STUDENT,PermissionEnum.TEACHER})
    @GetMapping
    public ResponseEntity<Object> getExecuteResults() {
        return new ResponseEntity<>(managerClient.getExecuteResult(), HttpStatus.OK);
    }
}
