<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List, site.entities.Usuario"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="head">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.12/cropper.min.js"></script>
	<link rel="stylesheet"href="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.12/cropper.min.css" />
	<script src="http://chancejs.com/chance.min.js"></script>	
	<script type="text/javascript">const CONTEXT_PATH = "<%= request.getContextPath().trim() %>";</script>
	
	<style>
	.container-fluid {
	  display: flex;
	  min-height: 100%;
	}
	 
	.jogo {
	  flex-grow: 1;
	  display: flex;
	  flex-direction: column;
	  border-right: 1px solid #f3f3fa;
	}
	
	.jogo .jogo-stats {
	  display: flex;
	  padding: 0 25px;
	  align-items: center;
	  margin-top: 0;
	}
	
	.jogo .jogo-stats > *:not(:nth-child(2)) {
		width: 190px;
	}
	
	.jogo .jogo-stats > *:nth-child(2) {
		flex-grow: 1;
		text-align: center;
	}
	
	.jogo .jogo-stats > *:nth-child(3) {
		text-align: right;
	}
	
	.jogo .jogo-stats > .timer {
	  font-size: 2.25em;
	  font-weight: bolder;
	  color: rgba(250, 255, 0, 1);
	}
	
	.jogo .jogo-stats .botao-restart {
	  width: 20px;
	  cursor: pointer;
	}
	
	.jogo .jogo-stats > .jogadas > .jogadas-title {
		font-weight: bold;
	}
	
	/* JOGO AREA */
	.jogo  > .jogo-area {
	  flex-grow: 1;
	  display: grid;
	  grid-template-columns: repeat(6, 75px);
	  grid-template-rows: repeat(4, 75px);
	  justify-content: center; 
	  gap: 20px;
	  border-radius: 15px;
	  padding-top: 25px;
	}
	
	.jogo  > .jogo-area > .celula {
	  width: 75px;
	  height: 85px;
	  background-color: rgba(131, 255, 165, 1);
	  border-radius: 5px;
	  box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
	  color: transparent;
	  text-align: center;
	}
	
	.jogo  > .jogo-area > .celula > * {
		display: none;
		max-width: 100%;
		max-height: 100%;
	}
	
	.jogo  > .jogo-area > .celula:hover {
	  cursor: pointer;
	}
	
	.jogo  > .jogo-area > .celula.virado {
	  cursor: default !important;
	  background-color: gray;
	  color: white;
	}
	
	.jogo  > .jogo-area > .celula.virado > *:not(img) {
		display: block;
	}

	/* RANKING */
	.ranking {
	  width: 325px;
	  padding: 0 10px;
	}
	
	.ranking > .title {
	  display: flex;
	  justify-content: center;
	  color: white;
	  font-size: 1.2em;
	  font-weight: bold;
	  padding: 15px 0 ;
	}
	
	.ranking > .user {
	  display: flex;
	  align-items: center;
	  color: white;
	  font-size: 0.85em;
	  padding: 8px 0 0 0;
	}
	
	.ranking > .user > .position {
	  font-weight: bold;
	  margin-right: 10px;
	  color: white;
	  width: 10px;
	  text-align: right;
	  align-self: flex-start;
	}
	
	.ranking > .user > .name {
	  flex-grow: 1;
	}
	
	.ranking > .user > .avatar {
	  border-radius: 50%;
	  width: 30px;
	  height: 30px;
	  background-color: bisque;
	  margin-right: 10px;
	}
	
	.ranking > .user > .trophy {
	  width: 20px;
	  height: 20px;
	  margin-left: 10px;
	}
	
	.modal-content > * { 
		display: flex; 
		align-items: center; 
		justify-content: center; 
	}
	
	.modal-content > .title {
		font-size: 1.75em; 
		font-weight: bold;
	}
	
	.modal-content .title { margin-bottom: 40px; }
	.modal-content .title i { font-size: 1.75em; }
	.modal-content .content { font-size: 1.25em; }
	.modal-content .content:not(:first-child) { margin: 0 0.5em; } 
	.modal-content #pontuacao.content { font-weight: bold; padding: 15px; font-size: 2em; }
	.modal-content .content.register-message a, .modal-content .content.success-message a { padding: 0 5px; }
	</style>
</c:set>

<c:set var="body">
	<div class="container-fluid">
		<div class="jogo">
			<div class="jogo-stats">
				<p class="jogadas">
					<span class="jogadas-title"> Número de jogadas: </span> 
					<span class="jogadas-count">0</span> 
					<br /> 
					<span class="jogadas-title">Acertos: </span> 
					<span class="acertos-count">0</span>
					<br /> 
					<span class="jogadas-title">Pontuação: </span> 
					<span class="pontuacao-count">0</span>
				</p>
				<p class="timer">00:00</p>
				<p>
					<input type="image"
						src="<%=request.getContextPath()%>/public/imagens/reload.png"
						class="botao-restart" onclick='window.location.href = "<%= request.getContextPath() %>"' >
				</p>
			</div>
			<div class="jogo-area"></div>
		</div>
		<div class="ranking">
			<div class="title">
				<span>Ranking</span>
			</div>
			<c:if test="${ranking.size() == 0}">
				<span class="empty">Nenhum registro encontrado, seja o primeiro a entrar!</span>
			</c:if>
			<c:forEach var="rank" varStatus="status" items="${ranking}">
				<div class="user">
					<p class="position">
						<c:out value="${status.index + 1}" />
					</p>
					<div class="avatar">
						<c:if test="${rank.jogador.avatar != null}">
							<img src="data:image/png;base64, ${rank.jogador.avatar}"
								width="32" height="32">
						</c:if>
						<c:if test="${rank.jogador.avatar == null}">
							<img
								src="https://eu.ui-avatars.com/api/?name=${rank.jogador.name}&size=32"
								width="32" height="32">
						</c:if>
					</div>
					<span class="name">
						${rank.jogador.name} <br /> <strong>${rank.partida.pontuacao}
							pts</strong>
					</span>
					<c:choose>
						<c:when test="${status.index == 0}">
							<img class="trophy"
								src="<%=request.getContextPath()%>/public/imagens/medalha-ouro.png">
						</c:when>
						<c:when test="${status.index == 1}">
							<img class="trophy"
								src="<%=request.getContextPath()%>/public/imagens/medalha-prata.png">
						</c:when>
						<c:when test="${status.index == 2}">
							<img class="trophy"
								src="<%=request.getContextPath()%>/public/imagens/medalha-bronze.png">
						</c:when>
					</c:choose>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<!-- Modal Structure -->
	<div id="modal" class="modal">
	  <div class="modal-content">
	    <span class="title primary-text"><i class="material-icons">military_tech</i> Parabéns!</span> 
	    <p class="content secondary-text">Você concluiu o com sucesso o Jogo da Memória da FACOM!</p>
	    <p class="content secondary-text">Sua pontuação final foi</p>
	    <p class="content secondary-text underline" id="pontuacao"></p>
	    <c:if test="${sessionScope.jogador == null}">
	    <p class="content secondary-text register-message">
	    	Faça <a class="primary-text" href="<%= request.getContextPath() %>/usuario">login ou cadastre-se</a> para salvar seus resultados!
	    </p>
	    </c:if>
	    <c:if test="${sessionScope.jogador != null}">
	    <p class="content secondary-text success-message">
	    	Acesse seu <a class="primary-text" href="<%= request.getContextPath() %>/historico">histórico</a> ou recarregue acessar o ranking atualizado!
	    </p>
	    </c:if>
	  </div>
	  <div class="modal-footer"> 
	    <a class="modal-close btn-flat grey-text">Fechar</a>
	  </div>
	</div>
</c:set>

<c:set var="end">
	<script src="<%=request.getContextPath()%>/public/scripts/home.js"></script>
	<script type="text/javascript">
		populateCards();
		if (params.uuid) startTimer();
	</script>
</c:set>

<t:layout>
	<jsp:attribute name="head">${head}</jsp:attribute>
	<jsp:attribute name="end">${end}</jsp:attribute>
	<jsp:body>${body}</jsp:body>
</t:layout>