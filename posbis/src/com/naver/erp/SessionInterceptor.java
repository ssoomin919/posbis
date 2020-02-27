package com.naver.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
// URL 접속 시 [컨트롤러 클래스의 메소드] 호출 전 또는 후에 
// 실행될 메소드를 소유한 [SessionInterceptor 클래스] 선언 선언
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// 컨트롤러 클래스의 메소드] 호출 전 또는 후에 실행될 메소드를 소유한 클래스가 될 자격 조건
	//---------------------------------------------------------------
	//		<1> 스프링이 제공하는 [HandlerInterceptorAdapter 클래스]를 상속 받는다.
	//		<2> HandlerInterceptorAdapter 클래스의 preHandle 메소드를 재정의한다.
	// 		<3> servlset-context.xml 파일에 다음 태그를 삽입한다
	//---------------------------------------------------------------
			//		<interceptors>
			//		<interceptor>
			//			<mapping path="/**/*" />
			//		    <exclude-mapping path="/loginForm.do"/>
			//		    <exclude-mapping path="/loginProc.do"/>
			//		    <beans:bean class="com.naver.erp.SessionInterceptor"></beans:bean>            
			//		</interceptor>        
			//		</interceptors>
	//---------------------------------------------------------------
		//*************************************************
		// [HandlerInterceptorAdapter 객체]의 메소드 종류와 호출 시점
		//*************************************************
		// preHandle()       : Controller 클래스의 메소드 실행 전에 호출. boolean 값을 return 하며 Controller 클래스의 특정 메소드 실행 여부를 결정
		// postHandle()      : Controller 클래스의 메소드 실행 후, JSP 를 실행 전에 호출
		// afterCompletion() : Controller 클래스의 메소드 실행 후, JSP 를 실행 후 호출됨. responseBody를 이용할 경우 값을 클라이언에게 전달 후 호출

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
public class SessionInterceptor extends HandlerInterceptorAdapter {	
	//*************************************************
	// URL 접속 시 [컨트롤러 클래스의 메소드] 호출 전에 실행될 preHandle(~) 메소드 선언
	// 메소드가 false 를 리턴하면 메소드 호출 후 컨트롤러의 메소드를 호출하지 않고, 반대면 호출한다.
	//*************************************************
	@Override
	public boolean preHandle(
		HttpServletRequest request 
		, HttpServletResponse response 
		, Object handler
	) throws Exception {
		System.out.println("-----------------------------------");
		System.out.println("Interceptor : preHandle(~) 메소드 호출 시작!");
		//-------------------------------------
		// HttpSession 객체의 메위주 얻기
		// HttpServletRequest 객체의  getSession() 메소드로  메위주를 얻는다.
		//-------------------------------------
		HttpSession session = request.getSession();   
		//-------------------------------------
		// Session 객체에서 키값이 admin_id 로 저장된 데이터 꺼내기. 
		// 즉 로그인 정보 꺼내기
		//-------------------------------------
		String user_id = (String)session.getAttribute("user_id");	
		
		// 로그인한 사업자번호 꺼내기
		String pos_business_no = (String)session.getAttribute("pos_business_no");	
		
		//-------------------------------------
		// 컨택스트 루트명 구하기.  URL 주소에 존재하는 Project 이름이 컨택스트 루트명이다.
		//-------------------------------------
		String ctRoot  = request.getContextPath();
		//-------------------------------------
		// 접속 URI 구하기.
		//-------------------------------------
		String uri = request.getRequestURI();
		//-------------------------------------
		// admin_id 가 null이면, 즉 로그인 한적이 없으면  ↓
		//-------------------------------------
		
		
		if(user_id==null) {
			// HttpServletResponse 객체의 sendRedirect 메솓 호출로 로그인 화면으로 이동. 
				//<주의> 이동 URL 주소에서 컨택스루트명인 /erp 를 빼지 말것
			response.sendRedirect( ctRoot+"/logoutForm.do" );
			System.out.println("<예외 없이 접속 성공>[접속 URI] → " + uri + "[호출메소드] → SessionInterceptor.preHandle(~)\n ");
			// false 값을 리턴하기.
			// false값을 리턴하면 컨트롤러 클래스의 메소드는 호출되지 않는다.
			return false;
		} 
		
		//-------------------------------------
		// Session 객체에 로그인 정보가 있으면 부모 메소드 preHandle(~) 호출하여 true 리턴한다.
		// true 리턴 시 이후 컨트롤러의 메소드를 호출한다..
		//-------------------------------------
		else { 
			System.out.println("<예외 없이 접속 성공> [접속 URI] → " + uri + "[호출메소드] → SessionInterceptor.preHandle(~)\n ");
			//return super.preHandle(request, response, handler);
			// true 값을 리턴하기.
			// true 값을 리턴하면 컨트롤러 클래스의 메소드는 호출된다.
			return true;
		}
		
		
		
		
		
	}
}

















	
	