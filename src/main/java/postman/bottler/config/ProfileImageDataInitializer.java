package postman.bottler.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import postman.bottler.user.infra.ProfileImageJpaRepository;
import postman.bottler.user.infra.entity.ProfileImageEntity;

@Component
@Order(1)
@RequiredArgsConstructor
public class ProfileImageDataInitializer implements ApplicationRunner {

    private final ProfileImageJpaRepository profileImageJpaRepository;

    private static final List<String> PROFILE_IMAGE_URLS = List.of(
        "https://img.bottler.store/profile1.svg",
        "https://img.bottler.store/profile2.svg",
        "https://img.bottler.store/profile3.svg",
        "https://img.bottler.store/profile4.svg"
    );

    @Override
    public void run(ApplicationArguments args) {
        if (profileImageJpaRepository.count() > 0) {
            return;
        }

        List<ProfileImageEntity> images = PROFILE_IMAGE_URLS.stream()
            .map(url -> ProfileImageEntity.builder().imageUrl(url).build())
            .toList();

        profileImageJpaRepository.saveAll(images);
    }
}
