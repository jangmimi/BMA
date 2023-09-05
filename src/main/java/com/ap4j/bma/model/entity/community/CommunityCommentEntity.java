package com.ap4j.bma.model.entity.community;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "community-comment")
public class CommunityCommentEntity {

    @ManyToOne(cascade=CascadeType.REMOVE)
    @JoinColumn(name="community_id", referencedColumnName = "id")
    private CommunityEntity communityEntity;

    @Column
    private String author;

    @Column
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
