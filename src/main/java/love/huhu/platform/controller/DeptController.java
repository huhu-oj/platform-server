package love.huhu.platform.controller;

import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.AuthorizationRequired;
import love.huhu.platform.authorization.PermissionEnum;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.client.ManagerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 上午9:47
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dept")
public class DeptController {


    private final ManagerClient managerClient;
    @GetMapping
    @AuthorizationRequired(PermissionEnum.TEACHER)
    public ResponseEntity<Object> getDept(String name, Boolean enabled, Long pid, Boolean pidIsNull) {
        return new ResponseEntity<>(managerClient.getDept(name,enabled,pid,pidIsNull), HttpStatus.OK);
    }
}
