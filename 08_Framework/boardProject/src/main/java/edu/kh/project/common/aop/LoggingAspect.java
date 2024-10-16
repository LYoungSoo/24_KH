package edu.kh.project.common.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.kh.project.member.dto.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그를 중간 중간에 출력하기 위한 AOP 코드
 */
@Component	// Bean 으로 등록
@Aspect		// 관점 등록(AOP 수행)
@Slf4j		// Log 출력 객체 생성
public class LoggingAspect {
	
	// 모든 컨트롤러 수행 전에 로그 출력
	@Before("PointcutBundle.controllerPointcut()")
	public void beforeController(JoinPoint jp) {
		
		// 클래스명
		String className = jp.getTarget().getClass().getSimpleName();
		
		// 메서드명
		String methodName = jp.getSignature().getName() + "()";
		
		// 컨트롤러를 요청한 클라이언트의
		// HttpServletRequest 객체 얻어오기
		HttpServletRequest req = 
				((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		// 요청한 클라이언트의 IP 얻어오기
		String clientIp = getRemoteAddr(req);
		
		// String 불변성 문제를 해결한 객체
		// ==> 메모리 효울 좋게 문자열 수정, 누적 등이 가능
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("[%s.%s] 요청 / ip : %s", className, methodName, clientIp));
		
		// 로그인이 되어있는 경우
		// 로그인한 회원의 회원 번호, 이메일도 추가
		Object loginMember = req.getSession().getAttribute("loginMember");
		
		if(loginMember != null) {		// 로그인 데이터가 세션에 있는 경우
			Member member = (Member)loginMember;		// 다운 캐스팅
			
			sb.append(String.format(" / 회원번호 : %d, 이메일 : %s",
					member.getMemberNo(), member.getMemberEmail()));
		}
		
		log.info("--------------------------------------------------");
		log.info(sb.toString());
	}
	
	/**
	 * 서비스 전/후 동작하는 코드(advice)
	 * @param pjp : JoinPoint 상속 객체
	 * - @Around() 가 적용된 메서드 매개 변수로 얻어올 수 있음
	 * 
	 * [전/후에 수행할 코드를 구분하는 방법]
	 * ProceedingJoinPoint.proceed() 메서드를 기준으로 함!
	 * 
	 * @Before 코드
	 * 	proceed();
	 * @After 코드
	 * 
	 * [주의사항]
	 * 1) @Around 사용 시 항상 반환형은 Object (다른건 다 void)
	 * 2) 메서드 종료 시 proceed() 메서드 호출 값을 반환해야 함!
	 */
	@Around("PointcutBundle.serviceImplPointcut()")
	public Object aroundServiceImpl(ProceedingJoinPoint pjp) throws Throwable{
		
		/* Before */
		String className = pjp.getTarget().getClass().getSimpleName();
		String methodName = pjp.getSignature().getName() + "()";
		String parameter = Arrays.toString(pjp.getArgs());
		
		log.info("========== {}.{} 서비스 호출 =====", className, methodName);
		log.info("Parameter : {}", parameter);
		
		// 서비스 코드가 수행되는 시점의 시간
		long startMs = System.currentTimeMillis();
		
		Object obj = pjp.proceed();		// 전/후 나누는 기준
		
		/* After */
		// 서비스 종료 시점의 시간을 기록
		long endMs = System.currentTimeMillis();
		
		// 서비스 수행에 걸린 시간 계산
		long delay = endMs - startMs;
		
		log.info("Running Time : {}ms", delay);
		
		log.info("================================================================================");
		return obj;
	}
	
	// ----------------------------------------------------------------------------------------------------
	
	/** @Transactional 어노테이션 롤백 동작 후 수행
	 * 사용 조건 : 서비스 메서드 레벨로 @Transactional이 작성되어야 동작함
	 * @param jp
	 * @param ex
	 */
	// 예외 발생 후 수행되는 코드
	// 서비스 수행 중에 예외가 발생했을 경우 - transaction Rollback 시도 할 때 Error 레벨을 로그에!
	@AfterThrowing(pointcut = "@annotation(org.springframework.transaction.annotation.Transactional)", 
				   throwing = "ex")
	public void transactionRollback(JoinPoint jp, Throwable ex) {
		log.info("***** 트랜잭션이 롤백됨 {} *****", jp.getSignature().getName());
		log.error("[롤백 원인] : {}", ex.getMessage());
	}	
	
	/** 접속자 IP 얻어오는 메서드
	 * @param request
	 * @return ip
	 */
	private String getRemoteAddr(HttpServletRequest request) {

		String ip = null;

		ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-RealIP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
}
