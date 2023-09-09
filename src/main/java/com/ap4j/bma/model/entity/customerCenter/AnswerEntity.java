package com.ap4j.bma.model.entity.customerCenter;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity

public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answerID; // 댓글 식별번호

    @OneToOne
    @JoinColumn(name = "qna_id")
    private QnAEntity qnAEntity; // 외래키(문의글 번호)

    @Column
    private String content;
}
