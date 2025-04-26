package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ModelException;
import model.Post;
import model.User;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;

@WebServlet(urlPatterns = {"/posts", "/post/save", "/post/update", "/post/delete"})
public class PostsController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String action = req.getRequestURI();

		System.out.println(action);

		switch (action) {
		case "/facebook/posts": {
			loadPosts(req);
			RequestDispatcher rd = req.getRequestDispatcher("posts.jsp");
			rd.forward(req, resp);
			break;
		}
		case "/facebook/post/save": {
			String postId = req.getParameter("post_id");
			if (postId != null && !postId.equals(""))
				updatePost(req);
			else
				insertPost(req);

			resp.sendRedirect("/facebook/posts");
			break;
		}
		case "/facebook/post/update": {
			loadPost(req);
			RequestDispatcher rd = req.getRequestDispatcher("/form_post.jsp");
			rd.forward(req, resp);
			break;
		}
		case "/facebook/post/delete": {
			deletePost(req);
			resp.sendRedirect("/facebook/posts");
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	private void loadPosts(HttpServletRequest req) {
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);
		List<Post> posts = null;

		try {
			posts = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}

		if (posts != null)
			req.setAttribute("posts", posts);
	}

	private void loadPost(HttpServletRequest req) {
		String postIdStr = req.getParameter("postId");
		int postId = Integer.parseInt(postIdStr);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			Post post = dao.findById(postId);
			if (post == null)
				throw new ModelException("Postagem não encontrada para alteração");

			req.setAttribute("post", post);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void insertPost(HttpServletRequest req) {
		Post post = createPost(req);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			dao.save(post);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void updatePost(HttpServletRequest req) {
		Post post = createPost(req);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			dao.update(post);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void deletePost(HttpServletRequest req) {
		String postIdStr = req.getParameter("postId");
		int postId = Integer.parseInt(postIdStr);

		Post post = new Post(postId);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			dao.delete(post);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private Post createPost(HttpServletRequest req) {
		String postId = req.getParameter("post_id");
		String postContent = req.getParameter("post_content");

		Post post;
		if (postId == null || postId.equals("")) {
			post = new Post();
		} else {
			post = new Post(Integer.parseInt(postId));
		}

		post.setContent(postContent);

		// Pega o usuário logado da sessão
		HttpSession session = req.getSession();
		User usuarioLogado = (User) session.getAttribute("usuario_logado");

		post.setUser(usuarioLogado);

		return post;
	}

}
