//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Getter
//@Entity
//public class TalkTalkReviewEntity extends TimeEntity {
//
//@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
//private Long id;
//
//@Column(length = 500, nullable = false)
//private String title;
//
//@Column(columnDefinition = "TEXT", nullable = false)
//private String content;
//
//private String writer;
//
//@Column(columnDefinition = "integer default 0")
//private int view;
//
//@ManyToOne(fetch = FetchType.LAZY)
//@JoinColumn(name = "user_id")
//private User user;
//
//@OneToMany(mappedBy = "posts", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//@OrderBy("id asc") // 댓글 정렬
//private List<Comment> comments;
//
///* 게시글 수정 메소드 */
//        public void update(String title, String content) {
//this.title = title;
//this.content = content;
//}
//}