package com.ap4j.bma.model.entity.board;


import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BoardDTO {

	private Long id;
	private String title;
	private String content;
	private String status;
	private String writer;
	private Date writtenDate;

	public BoardEntity toEntity(){
		return new BoardEntity(id, title, content,status, writer, writtenDate);
	}
	@Override
	public String toString() {
		return "BoardDTO{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", status='" + status + '\'' +
				", writer='" + writer + '\'' +
				", writtenDate='" + writtenDate + '\'' +
				'}';
	}

}
