package com.soldesk.ex01.persistence;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
	private WebApplicationContext wac; // À¥¾Û °´Ã¼
	
	private MockMvc mock; // Å×½ºÆ®¿ë mock-up ´õ¹Ì °´Ã¼
	
	@Before
	public void beforeTest() {
		log.info("beforeTest()");
		mock = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void test() {
		testRegistMemberPost();
//		testRegistMemberGet();
		
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





