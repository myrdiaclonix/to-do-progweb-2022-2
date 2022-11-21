<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="headTitle">
	<title>Login</title>
</c:set>

<c:set var="head">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/public/css/login.css">
</c:set>

<c:set var="body">

	<section class="content-login">

		<!-- Sign in article -->
		<article id="signIn" class="container overflow-hidden sing-page show">
			<div class="row justify-content-around">

				<!-- Wellcome content -->
				<div class="col-12 col-md-7 d-flex flex-column align-items-center justify-content-center">
					<div class="text-center mb-3 d-flex align-items-center d-md-block">
						<img src="<%=request.getContextPath()%>/public/images/logo.png" class="img-logo" alt="paperclip" />

						<h1 class="fw-normal mt-2">Paper</h1>
					</div>
					<div class="text-center mt-2 d-none d-md-block">
						<h2 class="fw-normal">
							Bem-vindo de volta!
						</h2>
						<p class="p-2 fs-5">
							Realize o login para conectar-se a sua conta Paper.
						</p>
					</div>
				</div>

				<!-- Login content -->
				<div class="col-12 col-md-5">
					<div class="rounded-login bg-white shadow">

						<!-- Login input -->
						<form id="form-login" class="form-login gap-3">
							<label class="fs-4 fw-light"><strong>Login</strong></label>
							
							<label for="loginEmail" class="form-label">E-mail</label>
							<input type="email" class="form-control form-background" id="loginEmail" name="loginEmail"  tabindex="1"/>
							
							<label for="loginPassword" class="form-label">Senha</label>
							<input type="password" class="form-control form-background" id="loginPassword" name="loginPassword" tabindex="2"/>
							
							<div class="d-flex flex-row-reverse mt-5">
								<button type="submit" class="btn rounded-5 btn-default shadow-sm" tabindex="3">Login</button>
							</div>
						</form>

						<!-- Sign up redirector -->
						<div class="footer-login text-center py-3">
							<span>
								Não possui conta?
								<a href="#signUp" class="link-dark btn-alter-sing-page">Criar conta</a>
							</span>
						</div>

					</div>
				</div>

			</div>
		</article>

		<!-- Sign up article -->
		<article id="signUp" class="container overflow-hidden sing-page">
			<div class="row justify-content-around pb-5">
				<div class="col-12 col-md-6 col-lg-5">
					<div class="mb-3 mb-md-0 text-center">
						<img src="<%=request.getContextPath()%>/public/images/logo.png" class="img-logo" alt="paperclip" />
					</div>

					<!-- Sign up form -->
					<div class="rounded-login bg-white shadow">
						<form id="form-cadastro" class="form-cadastro gap-3">
							<label class="fs-4 fw-light"><strong>Cadastro</strong></label>

							<label for="signUpName" class="form-label">Nome</label>
							<input type="text" class="form-control form-background" id="signUpName" name="signUpName" tabindex="1"/>

							<label for="signUpEmail" class="form-label">E-mail</label>
							<input type="email" class="form-control form-background" id="signUpEmail" name="signUpEmail" tabindex="2"/>

							<label for="signUpPassword" class="form-label">Senha</label>
							<input type="password" class="form-control form-background" id="signUpPassword"  name="signUpPassword"  tabindex="3"/>

							<div class="d-flex justify-content-between mt-5">
								<a href="#signIn" class="btn rounded-5 btn-default shadow-sm btn-alter-sing-page" tabindex="5">Voltar</a>
								<button type="submit" class="btn rounded-5 btn-default shadow-sm" tabindex="4">Cadastrar</button>
							</div>
						</form>
					</div>
					
				</div>
			</div>
		</article>
	</section>
</c:set>

<c:set var="end">
	<script src="<%=request.getContextPath()%>/public/js/login.js"></script>
</c:set>

<t:layout>
	<jsp:attribute name="headTitle">${headTitle}</jsp:attribute>
	<jsp:attribute name="head">${head}</jsp:attribute>
	<jsp:attribute name="end">${end}</jsp:attribute>
	<jsp:body>${body}</jsp:body>
</t:layout>