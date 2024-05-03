package com.soldesk.ex01.persistence;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.soldesk.ex01.config.ServletConfig;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServletConfig.class})
@WebAppConfiguration
@Log4j
public class MemberControllerTest {

	@Autowired
	private WebApplicationContext wac; // 웹앱 객체
	
	private MockMvc mock; // 테스트용 mock-up 더미 객체
	
	@Before
	public void beforeTest() {
		log.info("beforeTest()");
		mock = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void test() {
//		testRegistMemberPost();
//		testRegistMemberGet();
//		testdetailMember();
		testUpdateMember();
	}

	private void testUpdateMember() {
		
		
	}

	private void testdetailMember() {
		 // given
        Integer memberId = 1; // 테스트를 위한 Member ID
        // when & then
        try {
            mock.perform(get("/member/detail").param("memberId", memberId.toString())) // "/detail" 엔드포인트에 GET 요청을 보냄
                .andExpect(status().isOk()) // 응답 상태코드가 OK(200)인지 확인
                .andExpect(view().name("member/detail")); // 반환되는 뷰의 이름이 "detail"인지 확인
        } catch (Exception e) {
            log.error("Exception occurred during testDetailMember(): " + e.getMessage());
        }
	}

	private void testRegistMemberGet() {
		log.info("testRegistMemberGet()");
		
	}

	private void testRegistMemberPost() {
		log.info("testRegistMember()");
		
		RequestBuilder requestBuilder = post("/member/regist")
				.param("memberName", "test")
				.param("memberPassword", "test")
				.param("memberEmail", "test@test.com");
				
		
		try {
			log.info(mock.perform(requestBuilder).andReturn());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
}





