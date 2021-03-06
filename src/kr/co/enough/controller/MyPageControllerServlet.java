package kr.co.enough.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.enough.action.Action;
import kr.co.enough.action.ActionForward;
import kr.co.enough.dao.PayDao;
import kr.co.enough.service.DropOutMentoService;
import kr.co.enough.service.DropOutService;
import kr.co.enough.service.MentoMyStudyService;
import kr.co.enough.service.MentoMyStudyUpdateListService;
import kr.co.enough.service.MentoProfileService;
import kr.co.enough.service.MentoStudyMemberService;
import kr.co.enough.service.MyProfileService;
import kr.co.enough.service.MyStudyDeleteService;
import kr.co.enough.service.MyStudyService;
import kr.co.enough.service.MyWishService;
import kr.co.enough.service.UpdateMentoProfileService;
import kr.co.enough.service.UpdateProfileService;
import kr.co.enough.service.WishDeleteService;


@WebServlet("*.mypage")
public class MyPageControllerServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
   
    public MyPageControllerServlet() {
        super();
       
    }

   
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doProcess(request, response);
   }

   
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doProcess(request, response);
   }
   

   private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String RequestURI = request.getRequestURI();
      String ContextPath = request.getContextPath();
      String url_command = RequestURI.substring(ContextPath.length());
      Action action= null;
      ActionForward forward = null;
      System.out.println("MyPageController 성공");
      System.out.println(RequestURI);
      System.out.println(url_command);
      if(url_command.equals("/myProfile.mypage")) {
         try {
            action = new MyProfileService();
            forward = action.execute(request, response);
         }catch(Exception e) {
         e.printStackTrace();
         }
      }else if(url_command.equals("/updateProfile.mypage")) {
         try {
            
            action = new UpdateProfileService();
            forward = action.execute(request, response);
            System.out.println("member profile update success");
         }catch(Exception e) {
            
         }
      }else if(url_command.equals("/dropOut.mypage")) {
         try {
            action = new DropOutService();
            forward = action.execute(request, response);
            System.out.println("탈퇴");
         }catch(Exception e) {
            
         }
      }else if(url_command.equals("/myStudy.mypage")) {
         try {
            System.out.println("myStudy servlet 진입");
            action = new MyStudyService();
            forward = action.execute(request, response);
            System.out.println("마이스터디 리스트 뽑기");
         }catch(Exception e) {
         }
      }else if(url_command.equals("/myWish.mypage")) {
         try {
            System.out.println("");
            action = new MyWishService();
            forward = action.execute(request, response);
            System.out.println("마이스터디 리스트 뽑기");
         }catch(Exception e) {
         }
      }else if (url_command.equals("/mentoMyStudy.mypage")) { /* 멘토가 생성한 스터디 목록 보기 */
			try {
				action = new MentoMyStudyService();
				forward = action.execute(request, response);

			} catch (Exception e) {
			}
		} else if (url_command.equals("/mentoMyStudyUpdateList.mypage")) { /* 멘토가 자기 스터디 수정 리스트 보여주기 */
			try {
				action = new MentoMyStudyUpdateListService();
				forward = action.execute(request, response);
			} catch (Exception e) {
			}
		} else if (url_command.equals("/mentoStudyMember.mypage")) { /* 멘토가 스터디 별 회원 리스트 보기 - 셀렉트 박스 만듦 */
			try {
				action = new MentoStudyMemberService();
				forward = action.execute(request, response);
			} catch (Exception e) {
			}
			/*Delete 회원이 마이페이지에서 결재취소~ */ 
	      }else if(url_command.equals("/wishDelete.mypage")) {
	            try {
	                 System.out.println("위시딜리트테스트");
	               action = new WishDeleteService();
	                 forward = action.execute(request, response);
	                 System.out.println("마이스터디 리스트 뽑기");
	              }catch(Exception e) {
	              }
	        }else if(url_command.equals("/myStudyDelete.mypage")) {
	            try {
	                 System.out.println("스터디테스트딜리트테스트");
	               action = new MyStudyDeleteService();
	                 forward = action.execute(request, response);
	              }catch(Exception e) {
	              }
	        } else if (url_command.equals("/mentoProfile.mypage")) {
			try {
				action = new MentoProfileService();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (url_command.equals("/updateMentoProfile.mypage")) {
			try {
				action = new UpdateMentoProfileService();
				forward = action.execute(request, response);
				System.out.println("member profile update success");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(url_command.equals("/dropOutMentoProfile.mypage")) {
			try {
				action = new DropOutMentoService();
				forward = action.execute(request, response);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}

		
		
		
		
		else if(url_command.equals("/payDelete.mypage")) {
	    	  PrintWriter out = response.getWriter();
	    	  HttpSession session = request.getSession();

				String MEM_EMAIL = (String) session.getAttribute("email");
				int study_num = Integer.parseInt(request.getParameter("study_num"));			
	    	  try {
	  			
	  			PayDao dao = new PayDao();
	  			int studyDeleteRow = dao.studyMemberDelete(MEM_EMAIL, study_num);

	  			if (studyDeleteRow > 0) {
	  				System.out.println("studyMember delete 성공 : " + studyDeleteRow);
	  				int payDeleteRow = dao.payDelete(MEM_EMAIL, study_num);
	  				System.out.println("결재 payDeleteRow : " + payDeleteRow);
	  				if (payDeleteRow > 0) {
	  					System.out.println("결제 delete 성공");
	  					out.print("true");
	  				} else {
	  					System.out.println("결제 delete 실패");
	  					out.print("false");
	  				}
	  			} else {
	  				System.out.println("studyMember delete 실패");
	  				out.print("false");
	  			}

	  			
	  			

	  		} catch (Exception e) {
	  			e.printStackTrace();
	  		}
			  } //결재취소 끝

      
      if(forward !=null) {
         if(forward.isRedirect()) { //forward.isRedirect()가 true이면..
            response.sendRedirect(forward.getPath()); //location.href 
         
         }else { //forward.isRedirect()가 false이면..
            //forward >> 주소변호 없고 내용만 변화 >> 
            
            RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
            dis.forward(request, response);
         }
      }
      
      
   }

}




