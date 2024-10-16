package edu.kh.project.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

// @Component		// Bean 등록(@Controller, @Service의 부모 어노테이션)
// @Aspect		// 공통 관심사(advice)가 작성된 클래스 임을 명시(AOP 동작에 참여)
@Slf4j
public class TestAspect {
	
	// Join Point : advice (공통 관심사 코드) 가 적용될 수 있는 모든 지점(후보)
	
	// Pointcut   : 후보 지점 중 실제 적용될 지점을 잘라낸 것(실제로 적용되고 수행할 지점)
	
	// <Pointcut 작성 방법>
	// execution( [접근제한자(생략가능)] 리턴타입 클래스명 메소드명 ([파라미터]) )
	
	// 클래스명은 패키명부터 모두 작성
	
	// * : 모든
	// edu.kh.project.. : edu.kh.project 이하 패키지
	// 메서드명(..) 에서 매개변수 .. : 매개변수 0~n개 (개수 상관 없음) 
	
	/**
	 * 모든 Controller 메서드 수행 전(Before)에 로그 출력
	 */
//	@Before("execution(* edu.kh.project..*Controller*.*(..))")
	@Before("PointcutBundle.controllerPointcut()")
	public void testAdvice() {
		log.info("-------------------- testAdvice() 수행됨 --------------------");
	}
	
//	@After("execution(* edu.kh.project..*Controller*.*(..))")
	@After("PointcutBundle.controllerPointcut()")
	public void controllerEnd(JoinPoint jp) {
		// JoinPoint : AOP 에서 사용 가능한 부가 기능을 가진 객체
		
		// AOP 가 적용된 클래스명 얻어오기
		String className = jp.getTarget().getClass().getSimpleName();
		
		// Method Signature : 메서드 선언부 == 이름
		// jp.getSignature() : 실행된 타겟의 메서드 선언부 - 접근제한자 반환형 매개변수 메서드이름
		String methodName = jp.getSignature().getName();
		
		//{} {} 순서대로 값 대입 (printf 처럼)
		log.info("-------------------- {}.{} 수행 완료 --------------------", className, methodName);
	}
}
