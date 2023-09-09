package com.ap4j.bma.service.community;

import com.ap4j.bma.model.entity.community.CommunityCommentEntity;
import com.ap4j.bma.model.entity.community.CommunityEntity;
import com.ap4j.bma.model.repository.CommunityCommentRepository;
import com.ap4j.bma.model.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private CommunityCommentRepository communityCommentRepository;

    //Create
    //글작성처리
    public CommunityEntity saveCommunity(CommunityEntity communityEntity) {
        CommunityEntity savedEntity = communityRepository.save(communityEntity);
        updateTotalCommunityCount(); // 총 게시글 개수 업데이트
        return savedEntity;
    }

    public Long updateTotalCommunityCount() {
        Long totalCount = communityRepository.count(); // 총 게시글 개수 조회
        // totalCount를 원하는 변수에 저장하거나 활용

        return totalCount;
    }

    //게시글 페이징
    public Page<CommunityEntity> getCommunityPage(Pageable pageable) {
        return communityRepository.findAll(pageable);
    }

    //Read
    //상세페이지 보기
    public CommunityEntity communityView(Integer id) {
        return communityRepository.findById(id).get();
    }

    //이전글
    public Object getPreArticle(Integer id) {
        return communityRepository.findTopByIdLessThanOrderByIdDesc(id);
    }
    //다음글
    public Object getNextArticle(Integer id) {
        return communityRepository.findTopByIdGreaterThanOrderByIdAsc(id);
    }


    //Update

    //Delete
    //게시글 삭제
    public void communityDelete(Integer id) {
        communityRepository.deleteById(id);
        updateTotalCommunityCount(); //총 게시글 개수 업데이트
    }


    //조회수 처리
    @Transactional
    public void updateViewCount(Integer id) {
        CommunityEntity communityEntity = communityRepository.findById(id).orElse(null);
        if (communityEntity != null) {
            // 조회수 증가
            communityEntity.setView(communityEntity.getView() + 1);
            communityRepository.save(communityEntity);
        }
    }

    // 제목으로 검색
    public Page<CommunityEntity> searchByTitle(String keyword, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id").descending());
        return communityRepository.findByTitleContaining(keyword, pageable);
    }

    // 내용으로 검색
    public Page<CommunityEntity> searchByContent(String keyword, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id").descending());
        return communityRepository.findByContentContaining(keyword, pageable);
    }

    // 작성자로 검색
    public Page<CommunityEntity> searchByAuthor(String keyword, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id").descending());
        return communityRepository.findByAuthorContaining(keyword, pageable);
    }

    // 모든 필드(제목, 내용, 작성자 등)에서 검색
    public Page<CommunityEntity> searchByAll(String keyword, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id").descending());
        return communityRepository.findByTitleContainingOrContentContainingOrAuthorContaining(keyword, keyword, keyword, pageable);
    }

    //댓글
    //댓글 삭제
    public void communityCommentDelete(Integer id){
        communityCommentRepository.deleteById(id);
    }

    //댓글 보기
    public List<CommunityCommentEntity> communityCommentEntity(Integer id){
        List<CommunityCommentEntity> commentList = communityCommentRepository.findAllComment(id);
        return commentList;
    }

    //댓글 작성
    public void CommentWrite(CommunityCommentEntity communityCommentEntity){
        communityCommentRepository.save(communityCommentEntity);
    }

}
