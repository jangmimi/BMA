//@Getter
//public class TalkTalkReplyDto {
//
//private Long id;
//private String comment;
//private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
//private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
//private String nickname;
//private Long postsId;
//
///* Entity -> Dto*/
//        public TalkTalkReplyDto(Comment comment) {
//this.id = comment.getId();
//this.comment = comment.getComment();
//this.createdDate = comment.getCreatedDate();
//this.modifiedDate = comment.getModifiedDate();
//this.nickname = comment.getUser().getNickname();
//this.postsId = comment.getPosts().getId();
//}
//}