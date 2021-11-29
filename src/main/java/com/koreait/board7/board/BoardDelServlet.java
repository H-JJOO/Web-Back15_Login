package com.koreait.board7.board;

import com.koreait.board7.MyUtils;
import com.koreait.board7.dao.BoardDAO;
import com.koreait.board7.model.BoardVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/del")
public class BoardDelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (MyUtils.isLogout(req)) {
            res.sendRedirect("/user/login");
            req.setAttribute("err", "!!!정상적인 방법으로 접근하세요!!!");
            req.getRequestDispatcher("/user/login").forward(req, res);
            return;
        }

        int iboard = MyUtils.getParameterInt(req, "iboard");

        BoardVO param = new BoardVO();

        param.setIboard(iboard);
        param.setWriter(MyUtils.getLoginUserIboard(req));

        int result = BoardDAO.delBoard(param);

        switch (result) {
            case 1:
                res.sendRedirect("/board/list");
                break;
            default:
                req.setAttribute("err", "삭제를 실패하였습니다.");
                doGet(req, res);
                break;
        }
    }

}
