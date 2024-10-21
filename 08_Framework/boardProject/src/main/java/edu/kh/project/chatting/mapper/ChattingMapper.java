package edu.kh.project.chatting.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.project.chatting.dto.ChattingRoom;
import edu.kh.project.chatting.dto.Message;
import edu.kh.project.member.dto.Member;

@Mapper
public interface ChattingMapper {

	/**
	 * 채팅 상대 검색(본인 제외)
	 * @param query
	 * @param memberNo
	 * @return
	 */
	List<Member> selectTarget(@Param("query")    String query,
							  @Param("memberNo") int memberNo);

	
	/**
	 * 두 회원이 참여한 채팅방이 존재하는지 확인
	 * @param targetNo
	 * @param memberNo
	 * @return
	 */
	int checkChattingRoom(@Param("targetNo") int targetNo,
						  @Param("memberNo") int memberNo);


	/**
	 * 채팅방 테이블 삽입
	 * @param map
	 * @return
	 */
	int createChattingRoom(Map<String, Integer> map);


	/**
	 * 로그인한 회원이 참여한 채팅방 목록 조회
	 * @param memberNo
	 * @return
	 */
	List<ChattingRoom> selectRoomList(int memberNo);


	/**
	 * 특정 채팅방의 메시지 모두 조회
	 * @param chattingNo
	 * @return
	 */
	List<Message> selectMessage(int chattingNo);


	/**
	 * 특정 채팅방의 글 중 내가 보내지 않은 글을 읽음 처리
	 * @param chattingNo
	 * @param memberNo
	 * @return
	 */
	int updateReadFlag(@Param("chattingNo") int chattingNo,
					   @Param("memberNo")   int memberNo);


	/**
	 * 메시지 삽입
	 * @param msg
	 * @return result
	 */
	int insertMessage(Message msg);

}
