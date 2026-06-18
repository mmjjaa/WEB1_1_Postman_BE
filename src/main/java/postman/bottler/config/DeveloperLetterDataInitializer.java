package postman.bottler.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import postman.bottler.keyword.application.service.RedisLetterService;
import postman.bottler.letter.domain.BoxType;
import postman.bottler.letter.domain.LetterBox;
import postman.bottler.letter.domain.LetterType;
import postman.bottler.letter.infra.LetterBoxJdbcRepository;
import postman.bottler.letter.infra.LetterJpaRepository;
import postman.bottler.letter.infra.entity.LetterEntity;
import postman.bottler.user.infra.UserJpaRepository;
import postman.bottler.user.infra.entity.UserEntity;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class DeveloperLetterDataInitializer implements ApplicationRunner {

    private final LetterJpaRepository letterJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final LetterBoxJdbcRepository letterBoxJdbcRepository;
    private final RedisLetterService redisLetterService;

    private static final List<LetterEntity> DEVELOPER_LETTERS = List.of(
        LetterEntity.builder()
            .title("안녕하세요, Bottler에 오신 걸 환영해요!")
            .content("처음으로 편지를 받으셨군요. 저는 Bottler를 만든 개발팀이에요.\n\n이 바다 어딘가에서 당신의 유리병이 흘러왔을 거예요.\n편지를 쓰고, 누군가에게 전해지는 그 설렘을 함께 느껴보세요.\n\n당신의 이야기가 누군가에게 따뜻한 위로가 될 거예요.")
            .font("initial")
            .paper("1")
            .label("/라벨_샘플_01.png")
            .userId(1L)
            .isDeleted(false)
            .isBlocked(false)
            .createdAt(LocalDateTime.now())
            .build(),
        LetterEntity.builder()
            .title("오늘 하루도 수고했어요")
            .content("오늘 하루가 지치고 힘드셨나요?\n\n아무리 바쁜 하루라도, 이렇게 유리병 편지 하나를 열어봤다는 건\n마음속에 아직 여유가 있다는 뜻이에요.\n\n내일도 잘 부탁해요. 응원할게요.")
            .font("initial")
            .paper("2")
            .label("/라벨_샘플_02.png")
            .userId(1L)
            .isDeleted(false)
            .isBlocked(false)
            .createdAt(LocalDateTime.now())
            .build(),
        LetterEntity.builder()
            .title("이 편지가 당신에게 닿기를")
            .content("넓은 바다에서 작은 유리병 하나가 흘러왔어요.\n\n세상 어딘가에서 누군가가 당신을 생각하며 편지를 썼을 거예요.\n그 마음이 여기까지 닿았으니, 오늘 하루도 괜찮을 거예요.\n\n당신의 이야기도 언젠가 누군가에게 닿을 거예요.")
            .font("initial")
            .paper("3")
            .label("/라벨_샘플_1.png")
            .userId(1L)
            .isDeleted(false)
            .isBlocked(false)
            .createdAt(LocalDateTime.now())
            .build(),
        LetterEntity.builder()
            .title("Bottler를 사용해주셔서 감사해요")
            .content("안녕하세요! Bottler 개발팀입니다.\n\n여러분이 이 서비스를 사용해주신 덕분에 저희가 만든 것들이\n의미 있게 느껴져요.\n\n앞으로도 더 좋은 서비스를 만들기 위해 노력할게요.\n따뜻한 편지 많이 써주세요!")
            .font("initial")
            .paper("4")
            .label("/라벨_샘플_2.png")
            .userId(1L)
            .isDeleted(false)
            .isBlocked(false)
            .createdAt(LocalDateTime.now())
            .build(),
        LetterEntity.builder()
            .title("당신의 이야기를 들려줘요")
            .content("요즘 어떻게 지내고 계신가요?\n\n이 편지를 받은 당신이 궁금해요.\n행복한 일이 있었다면 함께 기뻐하고,\n슬픈 일이 있었다면 위로해드리고 싶어요.\n\n당신의 이야기를 편지로 써서 바다에 띄워보세요.")
            .font("initial")
            .paper("5")
            .label("/라벨_샘플_3.png")
            .userId(1L)
            .isDeleted(false)
            .isBlocked(false)
            .createdAt(LocalDateTime.now())
            .build(),
        LetterEntity.builder()
            .title("작은 인연의 시작")
            .content("낯선 사람에게 편지를 쓴다는 건 용기 있는 일이에요.\n\n이 유리병이 바다를 건너 당신에게 닿았듯이,\n당신의 편지도 누군가에게 특별한 하루를 만들어줄 수 있어요.\n\n오늘, 첫 번째 편지를 써보는 건 어떨까요?")
            .font("initial")
            .paper("6")
            .label("/라벨_샘플_4.png")
            .userId(1L)
            .isDeleted(false)
            .isBlocked(false)
            .createdAt(LocalDateTime.now())
            .build(),
        LetterEntity.builder()
            .title("봄비 같은 편지")
            .content("봄비가 내리는 날, 창밖을 바라보며 편지를 씁니다.\n\n비 소리를 들으면 왠지 모르게 누군가에게 연락하고 싶어지죠.\n저도 그런 마음으로 이 편지를 적었어요.\n\n오늘 하루, 소중한 사람에게 마음을 전해보세요.")
            .font("initial")
            .paper("1")
            .label("/라벨_샘플_5.png")
            .userId(1L)
            .isDeleted(false)
            .isBlocked(false)
            .createdAt(LocalDateTime.now())
            .build(),
        LetterEntity.builder()
            .title("유리병에 담긴 마음")
            .content("유리병에 편지를 담아 바다에 띄우는 건 오래된 낭만이에요.\n\n이제는 디지털 세상에서도 그 낭만을 이어갈 수 있게 되었어요.\n당신의 진심 어린 이야기를 담아 바다에 띄워보세요.\n\n어딘가에서 누군가가 기다리고 있을 거예요.")
            .font("initial")
            .paper("2")
            .label("/라벨_샘플_6.png")
            .userId(1L)
            .isDeleted(false)
            .isBlocked(false)
            .createdAt(LocalDateTime.now())
            .build()
    );

    private static final List<Long> DEVELOPER_LETTER_IDS = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);

    @Override
    public void run(ApplicationArguments args) {
        if (letterJpaRepository.count() > 0) {
            log.info("개발자 편지가 이미 존재합니다. Redis만 설정합니다.");
            setupRedisAndLetterBox(DEVELOPER_LETTER_IDS);
            return;
        }

        log.info("개발자 편지 시드 시작...");
        List<LetterEntity> saved = letterJpaRepository.saveAll(DEVELOPER_LETTERS);
        log.info("개발자 편지 {}개 저장 완료", saved.size());

        List<Long> letterIds = saved.stream()
            .map(l -> l.toDomain().getId())
            .filter(id -> id != null)
            .toList();

        setupRedisAndLetterBox(letterIds);
    }

    private void setupRedisAndLetterBox(List<Long> letterIds) {
        List<UserEntity> users = userJpaRepository.findAll();

        for (UserEntity user : users) {
            Long userId = user.getUserId();
            try {
                List<Long> recommendations = pickRandom3(letterIds);
                redisLetterService.saveDeveloperLetter(userId, recommendations);

                for (Long letterId : recommendations) {
                    if (!letterBoxJdbcRepository.existsByUserIdAndLetterId(letterId, userId)) {
                        LetterBox letterBox = LetterBox.builder()
                            .userId(userId)
                            .letterId(letterId)
                            .letterType(LetterType.LETTER)
                            .boxType(BoxType.RECEIVE)
                            .createdAt(LocalDateTime.now())
                            .build();
                        letterBoxJdbcRepository.save(letterBox);
                    }
                }
                log.info("userId={} 개발자 편지 추천 설정 완료", userId);
            } catch (Exception e) {
                log.warn("userId={} 추천 설정 실패: {}", userId, e.getMessage());
            }
        }
    }

    private List<Long> pickRandom3(List<Long> ids) {
        List<Long> shuffled = new ArrayList<>(ids);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, Math.min(3, shuffled.size()));
    }
}
