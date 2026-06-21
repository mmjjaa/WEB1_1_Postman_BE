package postman.bottler.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import postman.bottler.mapletter.domain.MapLetterType;
import postman.bottler.mapletter.infra.MapLetterJpaRepository;
import postman.bottler.mapletter.infra.entity.MapLetterEntity;

@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class MapLetterDataInitializer implements ApplicationRunner {

    private final MapLetterJpaRepository mapLetterJpaRepository;

    private static final Long DEVELOPER_USER_ID = 1L;

    @Override
    public void run(ApplicationArguments args) {
        List<MapLetterEntity> existing = mapLetterJpaRepository.findAllByCreateUserId(DEVELOPER_USER_ID);
        if (!existing.isEmpty()) {
            log.info("개발자 지도 편지가 이미 존재합니다. 시드를 건너뜁니다. ({}개)", existing.size());
            return;
        }

        log.info("개발자 지도 편지 시드 시작...");
        List<MapLetterEntity> letters = buildSeoulMapLetters();
        mapLetterJpaRepository.saveAll(letters);
        log.info("개발자 지도 편지 {}개 저장 완료", letters.size());
    }

    private List<MapLetterEntity> buildSeoulMapLetters() {
        LocalDateTime now = LocalDateTime.now();
        return List.of(
            build("경복궁에서 보내는 편지", "고궁의 돌담 사이로 바람이 불어요.\n이곳에 서면 시간이 멈춘 것 같아요.\n당신도 언젠가 이 길을 걸어보셨나요?", "37.5796", "126.9770", "조선의 아침처럼 고요한 곳", "/라벨_샘플_01.png", now),
            build("남산에서 보내는 편지", "서울 야경이 한눈에 보이는 이곳에서 편지를 씁니다.\n불빛 하나하나가 누군가의 하루인 것 같아요.\n오늘 하루도 수고하셨어요.", "37.5512", "126.9882", "서울의 심장에서", "/라벨_샘플_02.png", now),
            build("홍대 골목에서", "음악 소리와 웃음소리가 가득한 이 골목에서.\n낯선 사람들과 스쳐 지나가는 것도 어떤 인연이겠죠.\n오늘 밤 이 거리를 걷고 있나요?", "37.5563", "126.9233", "청춘이 넘치는 곳", "/라벨_샘플_1.png", now),
            build("강남에서 띄우는 편지", "바쁘게 걷는 사람들 사이에서 잠깐 멈춰봐요.\n빠르게 흘러가는 일상 속에도 소중한 순간이 있으니까요.\n잠깐, 숨 한 번 쉬어가요.", "37.4979", "127.0276", "빠름 속의 여유", "/라벨_샘플_2.png", now),
            build("명동에서 보내는 편지", "사람들로 북적이는 이 거리에서도\n당신만의 속도로 걸어도 괜찮아요.\n맛있는 거 드셨나요? 오늘 하루 잘 챙겨 드세요.", "37.5637", "126.9847", "활기찬 거리에서", "/라벨_샘플_3.png", now),
            build("신촌에서 띄우는 편지", "이곳에서 꿈을 키우는 사람들에게.\n지금 이 순간이 훗날 가장 빛나는 시간이 될 거예요.\n오늘도 열심히 살고 있는 당신을 응원해요.", "37.5589", "126.9370", "꿈을 키우는 곳에서", "/라벨_샘플_4.png", now),
            build("이태원에서 보내는 편지", "다양한 문화가 섞이는 이 거리처럼\n우리도 서로 다른 이야기를 가지고 있죠.\n당신의 이야기는 어떤가요?", "37.5340", "126.9942", "다채로운 거리에서", "/라벨_샘플_5.png", now),
            build("잠실에서 띄우는 편지", "한강이 흐르는 이 도시에서\n강물처럼 흘러가는 시간이 아깝지 않게\n오늘 하루를 가득 채워봐요.", "37.5132", "127.1026", "한강 옆 도시에서", "/라벨_샘플_6.png", now),
            build("동대문에서 보내는 편지", "밤새 불 꺼지지 않는 이 거리에서\n열심히 살아가는 모든 분들께 전합니다.\n당신의 노력은 반드시 빛을 발할 거예요.", "37.5700", "127.0098", "밤새 빛나는 곳에서", "/라벨_샘플_01.png", now),
            build("인사동 골목에서", "오래된 것들이 모여있는 이 골목에서\n느리게 걷는 것이 얼마나 소중한지 느껴요.\n오늘 하루는 조금 천천히 걸어봐요.", "37.5739", "126.9876", "옛것의 향기가 나는 곳", "/라벨_샘플_02.png", now),
            build("성수동에서 보내는 편지", "힙하고 따뜻한 이 동네에서\n새로운 것을 만들어가는 사람들을 보며\n저도 무언가 시작하고 싶어졌어요.", "37.5445", "127.0557", "감성 가득한 성수에서", "/라벨_샘플_1.png", now),
            build("여의도 한강공원에서", "바람이 시원하게 부는 한강공원에서\n자전거를 타는 사람들, 피크닉을 즐기는 사람들.\n이 평화로운 풍경이 오래 이어지길 바라요.", "37.5219", "126.9245", "한강 바람 맞으며", "/라벨_샘플_2.png", now),
            build("대학로에서 띄우는 편지", "공연이 끝나고 쏟아지는 사람들 사이에서\n오늘 본 공연의 여운이 아직 남아있어요.\n예술이 우리 곁에 있다는 게 참 다행이에요.", "37.5820", "127.0025", "예술이 살아숨쉬는 곳", "/라벨_샘플_3.png", now),
            build("합정에서 보내는 편지", "한강과 가까운 이 동네에서\n오후의 햇살을 받으며 카페에 앉아있어요.\n당신도 지금 어딘가에서 잠깐 쉬고 있길 바라요.", "37.5495", "126.9144", "여유로운 오후에", "/라벨_샘플_4.png", now),
            build("압구정에서 띄우는 편지", "화려한 거리를 걷다 문득 하늘을 올려다봤어요.\n높은 빌딩 사이로 보이는 하늘이 참 파랗네요.\n바쁜 일상 속에서도 하늘 한 번 올려다봐요.", "37.5271", "127.0286", "하늘을 올려다보며", "/라벨_샘플_5.png", now)
        );
    }

    private MapLetterEntity build(String title, String content, String lat, String lng, String description, String label, LocalDateTime now) {
        return MapLetterEntity.builder()
            .title(title)
            .content(content)
            .latitude(new BigDecimal(lat))
            .longitude(new BigDecimal(lng))
            .font("initial")
            .paper("1")
            .label(label)
            .description(description)
            .type(MapLetterType.PUBLIC)
            .targetUserId(null)
            .createUserId(DEVELOPER_USER_ID)
            .createdAt(now)
            .updatedAt(now)
            .isDeleted(false)
            .isBlocked(false)
            .isRead(false)
            .isRecipientDeleted(false)
            .build();
    }
}
