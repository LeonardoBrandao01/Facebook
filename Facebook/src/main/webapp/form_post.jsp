<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
	<meta charset="UTF-8">
	<title>Novo Post</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>
<body>

<div class="container">
	<div class="row">
		<div class="col-2"></div>

		<form action="${pageContext.request.contextPath}/post/save" method="GET" class="col-8">
			<h1>Novo Post</h1>

			<input type="hidden" id="post_id" name="post_id" value="${post.id}" />

			<div class="mb-3">
				<label for="post_content_id" class="form-label">Conteúdo</label>
				<input type="text" id="post_content_id" name="post_content"
					   class="form-control" value="${post.content}" required />
			</div>

			<button type="submit" class="btn btn-primary">Salvar</button>
			<a href="${pageContext.request.contextPath}/posts" class="btn btn-secondary">Cancelar</a>
		</form>

		<div class="col-2"></div>
	</div>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
