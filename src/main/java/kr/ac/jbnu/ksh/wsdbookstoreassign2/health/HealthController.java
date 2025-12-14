package kr.ac.jbnu.ksh.wsdbookstoreassign2.health;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Tag(name = "Health", description = "헬스 체크")
@RestController
public class HealthController {

    private final ObjectProvider<BuildProperties> buildPropertiesProvider;
    private final ObjectProvider<GitProperties> gitPropertiesProvider;

    public HealthController(ObjectProvider<BuildProperties> buildPropertiesProvider,
                            ObjectProvider<GitProperties> gitPropertiesProvider) {
        this.buildPropertiesProvider = buildPropertiesProvider;
        this.gitPropertiesProvider = gitPropertiesProvider;
    }

    @Operation(summary = "헬스 체크", description = "서버 상태를 확인합니다(UP/DOWN). 배포 검증용으로 인증 없이 접근 가능해야 합니다.")
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("status", "UP");
        res.put("timestamp", Instant.now().toString());

        BuildProperties build = buildPropertiesProvider.getIfAvailable();
        if (build != null) {
            res.put("name", build.getName());
            res.put("version", build.getVersion());
            res.put("buildTime", build.getTime() != null ? build.getTime().toString() : null);
        } else {
            // 테스트/개발 환경에서 build-info가 없을 때도 200 나오게
            res.put("name", "wsd-bookstoreassign2");
            res.put("version", "unknown");
            res.put("buildTime", null);
        }

        GitProperties git = gitPropertiesProvider.getIfAvailable();
        if (git != null) {
            res.put("gitCommit", git.getCommitId());
            res.put("gitBranch", git.getBranch());
        }

        return res;
    }
}
