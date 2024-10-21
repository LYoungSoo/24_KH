package edu.kh.project.chatting.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.chatting.dto.ChattingRoom;
import edu.kh.project.chatting.dto.Message;
import edu.kh.project.chatting.mapper.ChattingMapper;
import edu.kh.project.member.dto.Member;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor		// final
public class ChattingServiceImpl implements ChattingService {
	
	private final ChattingMapper mapper;

	// 채팅 상대 검색(본인 제외)
	@Override
	public List<Member> selectTarget(String query, int memberNo) {
		return mapper.selectTarget(query, memberNo);
	}

	
	// 채팅방 입장(처음 채팅이면 채팅방 생성(INSERT)
	@Override
	public int chattingEnter(int targetNo, int memberNo) {
		
		// 두 회원이 참여한 채팅방이 존재하는지 확인
		int chattingNo = mapper.checkChattingRoom(targetNo, memberNo);
		
		// 두 회원이 참여한 채팅방이 없을 경우
		if(chattingNo == 0) {
			Map<String, Integer> map = new HashMap<>();
			map.put("targetNo", targetNo);
			map.put("memberNo", memberNo);
			
			// 갑자기 Map 을 사용하는 이유!!
			// - INSERT 시 새로 생성되는 CHATTING_ROOM_NO 값을 MAP 에 담아서 얻어오기 위해!
			// (useGeneratedKeys = true, <selectKey>)
			// 0, 1 을 받아오는게 아니라 값을 받아오기 위해
			
			// 채팅방 테이블 삽입
			int result = mapper.createChattingRoom(map);
			
			chattingNo = (int)map.get("chattingNo");
		}
		
		return chattingNo;
	}


	// 로그인한 회원이 참여한 채팅방 목록 조회
	@Override
	public List<ChattingRoom> selectRoomList(int memberNo) {
		return mapper.selectRoomList(memberNo);
	}
	

	
	// 특정 채팅방의 메시지 모두 조회
	@Override
	public List<Message> selectMessage(int chattingNo, int memberNo) {
		
		List<Message> messageList = mapper.selectMessage(chattingNo);
		
		// 조회된 메시지 목록이 있을 경우
		if(messageList.isEmpty() == false) {
			
			// 특정 채팅방의 글 중 내가 보내지 않은 글을 읽음 처리
			// ==> 채팅방에 들어가면 모두 읽음 처리하고 메시지 보이기
			int result = mapper.updateReadFlag(chattingNo, memberNo);
		}
		
		return messageList;
	}


	// 채팅 읽음 표시
	@Override
	public int updateReadFlag(int chattingNo, int memberNo) {
		return mapper.updateReadFlag(chattingNo, memberNo);
	}


	// 메시지 삽입
	@Override
	public int insertMessage(Message msg) {
		return mapper.insertMessage(msg);
	}
	
}
