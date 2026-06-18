package postman.bottler.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import postman.bottler.keyword.infra.KeywordJpaRepository;
import postman.bottler.keyword.infra.entity.KeywordEntity;

@Component
@RequiredArgsConstructor
public class KeywordDataInitializer implements ApplicationRunner {

    private final KeywordJpaRepository keywordJpaRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (keywordJpaRepository.count() > 0) {
            return;
        }

        List<KeywordEntity> keywords = List.of(
            build("사랑", "감정"),
            build("행복", "감정"),
            build("슬픔", "감정"),
            build("설렘", "감정"),
            build("외로움", "감정"),
            build("그리움", "감정"),
            build("공감", "감정"),
            build("후련함", "감정"),
            build("우정", "사회"),
            build("가족", "사회"),
            build("연인", "사회"),
            build("직장", "사회"),
            build("학교", "사회"),
            build("성공", "자기계발"),
            build("도전", "자기계발"),
            build("성장", "자기계발"),
            build("목표", "자기계발"),
            build("노력", "자기계발"),
            build("취미", "일상"),
            build("여행", "일상"),
            build("음식", "일상"),
            build("계절", "일상"),
            build("독서", "일상")
        );

        keywordJpaRepository.saveAll(keywords);
    }

    private KeywordEntity build(String keyword, String category) {
        return KeywordEntity.builder()
                .keyword(keyword)
                .category(category)
                .build();
    }
}
