package postman.bottler.letter.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postman.bottler.keyword.application.service.LetterKeywordService;
import postman.bottler.keyword.application.service.RedisLetterService;
import postman.bottler.keyword.domain.LetterKeyword;
import postman.bottler.letter.application.dto.request.LetterRequestDTO;
import postman.bottler.letter.application.dto.response.LetterDetailResponseDTO;
import postman.bottler.letter.application.dto.response.LetterRecommendSummaryResponseDTO;
import postman.bottler.letter.application.dto.response.LetterResponseDTO;
import postman.bottler.letter.domain.Letter;
import postman.bottler.user.application.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class LetterFacadeService {

    private final LetterBoxService letterBoxService;
    private final LetterService letterService;
    private final LetterKeywordService letterKeywordService;
    private final RedisLetterService redisLetterService;
    private final UserService userService;
    private final ReplyLetterService replyLetterService;

    @Transactional
    public LetterResponseDTO createLetter(LetterRequestDTO letterRequestDTO, Long userId) {
        Letter letter = letterService.createLetter(letterRequestDTO, userId);
        List<LetterKeyword> keywords = letterKeywordService.createLetterKeywords(letter.getId(),
                letterRequestDTO.keywords());
        return LetterResponseDTO.from(letter, keywords);
    }

    @Transactional(readOnly = true)
    public LetterDetailResponseDTO findLetterDetail(Long letterId, Long currentUserId) {
        letterBoxService.validateLetterInUserBox(letterId, currentUserId);
        boolean isReplied = replyLetterService.checkIsReplied(letterId, currentUserId);
        List<LetterKeyword> keywords = letterKeywordService.getKeywords(letterId);
        String profile = userService.getProfileImageUrlById(currentUserId);
        Letter letter = letterService.findLetter(letterId);
        return LetterDetailResponseDTO.from(letter, keywords, currentUserId, profile, isReplied);
    }

    @Transactional(readOnly = true)
    public List<LetterRecommendSummaryResponseDTO> findRecommendHeaders(Long userId) {
        try {
            List<Long> letterIds = redisLetterService.fetchActiveRecommendations(userId);
            if (letterIds == null || letterIds.isEmpty()) {
                return findDeveloperLetterFallback();
            }
            List<Letter> letters = letterService.findRecommendedLetters(letterIds);
            if (letters.isEmpty()) {
                return findDeveloperLetterFallback();
            }
            return letters.stream().map(LetterRecommendSummaryResponseDTO::from).toList();
        } catch (Exception e) {
            log.warn("추천 편지 조회 실패 (userId={}): {}", userId, e.getMessage());
            return findDeveloperLetterFallback();
        }
    }

    private List<LetterRecommendSummaryResponseDTO> findDeveloperLetterFallback() {
        try {
            List<Long> developerLetterIds = letterService.findIdsByUserId(1L);
            if (developerLetterIds.isEmpty()) {
                return List.of();
            }
            java.util.ArrayList<Long> shuffled = new java.util.ArrayList<>(developerLetterIds);
            java.util.Collections.shuffle(shuffled);
            List<Long> picked = shuffled.subList(0, Math.min(3, shuffled.size()));
            return letterService.findRecommendedLetters(picked).stream()
                .map(LetterRecommendSummaryResponseDTO::from)
                .toList();
        } catch (Exception e) {
            log.warn("개발자 편지 fallback 조회 실패: {}", e.getMessage());
            return List.of();
        }
    }
}
