//@RequiredArgsConstructor
//@Service
//public class ReplyService {
//
//private final CommentRepository commentRepository;
//private final UserRepository userRepository;
//private final PostsRepository postsRepository;
//
///* CREATE */
//        @Transactional
//public Long commentSave(String nickname, Long id, CommentRequestDto dto) {
//User user = userRepository.findByNickname(nickname);
//osts posts = postsRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + id));
//
//dto.setUser(user);
//dto.setPosts(posts);
//
//Comment comment = dto.toEntity();
//commentRepository.save(comment);
//
//return dto.getId();
//}
//}