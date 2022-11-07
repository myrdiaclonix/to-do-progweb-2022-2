<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List,java.util.Date,site.entities.User"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="headTitle">
	<title>Tasks | To-Do</title>
</c:set>

<c:set var="head">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/public/css/task.css">
</c:set>

<c:set var="body">
	
	<% Date dtCurrent = new Date(); %>

	<header>
		<nav class="navbar header-main">
			<div class="container-fluid">

				<div class="d-flex align-items-center gap-2">
					<img src="<%=request.getContextPath()%>/public/images/logo.png" class="img-logo" alt="paperclip" />
					<a class="navbar-brand text-white" href="#home">Paper</a>
				</div>

				<span class="navbar-text" data-bs-toggle="offcanvas" data-bs-target="#offcanvas-user-profile"
					aria-controls="offcanvas-user-profile">
					<a href="#photo" class="">
						<img src="<%=request.getContextPath()%>/public/images/person-circle.svg" width="25" height="25" alt="User logo perfil">
					</a>
				</span>

				<button class="navbar-toggler d-block d-md-none" type="button" data-bs-toggle="offcanvas"
					data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar">
					<span class="navbar-toggler-icon"></span>
				</button>
			</div>

			<!-- USER CONFIG -->
			<div class="offcanvas offcanvas-end offcanvas-body-user text-user-default" tabindex="-1" id="offcanvas-user-profile"
				aria-labelledby="offcanvas-user-name">
				<div class="offcanvas-header">
					<label class="offcanvas-title fs-4 text" id="offcanvas-user-name">Nome Usuário</label>
					<a href="#login" class="">
						<img src="<%=request.getContextPath()%>/public/images/person-circle.svg" width="45" height="45" alt="User logo perfil">
					</a>
				</div>
				<div class="offcanvas-body">
					<div>
						<ul class="navbar-nav">
							<li class="nav-item">
								<a class="nav-link text-user-default fs-4" href="#modal-perfil-edit" data-bs-toggle="modal" data-bs-target="#modal-perfil-edit">
									<i class="far fa-user-edit"></i>
									<span>Editar Perfil</span>
								</a>
							</li>

							<li class="nav-item">
								<a class="nav-link text-user-default fs-4" href="#modal-pass-alter" data-bs-toggle="modal" data-bs-target="#modal-pass-alter">
									<i class="far fa-lock"></i>
									<span>Trocar Senha</span>
								</a>
							</li>

							<li class="nav-item">
								<a class="nav-link text-user-default fs-4" href="#modal-photo-alter" data-bs-toggle="modal" data-bs-target="#modal-photo-alter">
									<i class="far fa-camera"></i>
									<span>Trocar Foto</span>
								</a>
							</li>

							<hr>

							<li class="nav-item">
								<a class="nav-link text-user-default fs-4" href="#modal-tag-edit" data-bs-toggle="modal" data-bs-target="#modal-tag-edit">
									<i class="far fa-tags"></i>
									<span>Editar Etiquetas</span>
								</a>
							</li>
						</ul>
					</div>
				</div>
				<div class="offcanvas-footer px-3 py-2">
					<div>
						<ul class="navbar-nav">
							<li class="nav-item">
								<a class="nav-link text-user-default fs-4" href="#logout">
									<i class="far fa-sign-out"></i>
									<span>Sair</span>
								</a>
							</li>
						</ul>
					</div>
				</div>
			</div>

			<div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasNavbar"
				aria-labelledby="offcanvasNavbarLabel">
				<div class="offcanvas-header">
					<h5 class="offcanvas-title" id="offcanvasNavbarLabel">Offcanvas</h5>
					<button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
				</div>
				<div class="offcanvas-body">
					<ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
						<li class="nav-item">
							<a class="nav-link active" aria-current="page" href="#">Home</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Link</a>
						</li>
						<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
								aria-expanded="false">
								Dropdown
							</a>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item" href="#">Action</a></li>
								<li><a class="dropdown-item" href="#">Another action</a></li>
								<li>
									<hr class="dropdown-divider">
								</li>
								<li><a class="dropdown-item" href="#">Something else here</a></li>
							</ul>
						</li>
					</ul>
					<form class="d-flex" role="search">
						<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
						<button class="btn btn-outline-success" type="submit">Search</button>
					</form>
				</div>
			</div>
		</nav>
	</header>

	<section class="container-fluid row">

		<!-- Tasks list menu -->
		<article id="menu-tasks" class="col-12 col-md-3 px-0 py-2">
			<div class="list-group rounded-0">
			
				<c:if test="${listaTask.idLista != null}">${active = ""}</c:if>
				<c:if test="${listaTask.idLista == null}"><span class="d-none">${active = "actual"}</span></c:if>
				
				<a href="#home" class="list-group-item list-group-item-action border-0 list-item-tasks fs-5 menu-task ${active}"> 
					<span>
						<i class="fas fa-home-lg-alt icons-menu-tasks"></i>
						Minhas Tarefas
					</span>
				</a>
				<a href="#modal-share-lists" class="list-group-item list-group-item-action border-0 list-item-tasks fs-5" data-bs-toggle="modal" data-bs-target="#modal-share-lists">
					<span>
						<i class="fal fa-share-alt icons-menu-tasks"></i>
						Compartilhar
					</span>
				</a>
			</div>
			<hr>
			<div id="menu-list-tasks" class="list-group rounded-0">
				<c:forEach var="ls" varStatus="status" items="${listas}">
				
				<c:if test="${ls.idLista != listaTask.idLista}">${active = ""}</c:if>
				<c:if test="${ls.idLista == listaTask.idLista}"><span class="d-none">${active = "actual"}</span></c:if>
				
				<a href="#lista-${ls.idLista}" class='list-group-item list-group-item-action border-0 list-item-tasks menu-task fs-5 ${active}'>
					<span>
						<i class="far fa-bars icons-menu-tasks"></i>
						${ls.title}
					</span>
					<span class="btn-list-edit float-end ${active}">
						<i class="fad fa-edit icons-menu-tasks" data-bs-toggle="modal" data-bs-target="#modal-add-lists" data-lista="${ls.idLista}"></i>
						<i class="fas fa-trash-alt icons-menu-tasks btn-remove-lista" data-lista="${ls.idLista}"></i>
					</span>
				</a>
				
				</c:forEach>
				
			</div>

			<div class="list-group rounded-0">
				<a href="#modal-add-lists" class="list-group-item list-group-item-action border-0 list-item-tasks fs-5" data-bs-toggle="modal" data-bs-target="#modal-add-lists" data-type="1">
					<span>
						<i class="fal fa-plus"></i>
						Lista
					</span>
				</a>
			</div>

			<hr>

			<div class="list-group rounded-0">
				<a href="#list-viagem" class="list-group-item list-group-item-action border-0 list-item-tasks menu-task fs-5">
					<span>
						<i class="fad fa-share-alt icons-menu-tasks"></i>
						Compartilhada
					</span>
				</a>
			</div>

		</article>

		<main class="col-12 col-md ps-5">

			<div class="search-tasks py-4">
				<form id="form-search-tasks" class="row align-items-center">
					<div class="col-12 col-md">
						<div class="d-flex">
							<input type="text" class="form-control input-default input-search border-0"
								id="input-search-task" name="input-search-task" value="${search}" />
							<button id="btn-search-tasks" class="btn input-default border-0 icons-menu-tasks" type="submit">
								<i class="far fa-search"></i>
							</button>
						</div>
					</div>
					<div class="col-12 col-md-4 d-flex justify-content-end">
						<a href="#modal-add-tasks" class="btn btn-default rounded-5" data-bs-toggle="modal"
							data-bs-target="#modal-add-tasks" data-type="">
							Adicionar Tarefa
						</a>
					</div>
				</form>
			</div>

			<!-- To do tasks list -->
			<div id="list-all-tasks" class="list-tasks py-2">
				
				<c:if test="${listaTask == null}">
					<h2>Minhas Tarefas</h2>
				</c:if>
				<c:if test="${listaTask != null}">
					<h2>${listaTask.title}</h2>
				</c:if>
				
				<div id="list-my-tasks" class="list-group py-3 gap-3">
					
					<c:if test="${pTasks.size() == 0}">
					
					<span class="empty">Nenhuma tarefa pendente encontrada, Adicione uma!</span>
					
					</c:if>
					
					<c:forEach var="task" varStatus="status" items="${pTasks}">
					
					<div class="list-group-item list-group-item-action border rounded-4 fs-4 py-1 fundo-task">
						<div class="row">
							<div class="col-12 col-md-6">
								<div class="d-flex align-items-start gap-3">
									<input class="form-check-input mt-2" type="checkbox" value="${task.idTask}" id="input-check-task" name="input-check-task">
									<label class="form-check-label d-flex flex-column " for="input-check-task">
										<span>${task.title}</span>
										<small class="fs-6 d-inline-block text-truncate text-task-desc">
										${task.description}
										</small>
									</label>
								</div>
							</div>
							<div class="col d-flex justify-content-end align-items-center gap-5">
								
								<span class="fs-6">
									
									<c:if test="${task.dtLimit == null}">
									SEM DATA
									</c:if>
									
									${task.getDtConvert(task.dtLimit)}
								
								</span>
								<a href="#modal-add-tasks" class="link-dark fs-5" data-bs-toggle="modal" data-bs-target="#modal-add-tasks" data-task="${task.idTask}">
									<i class="far fa-pen"></i>
								</a>
							</div>
						</div>
					</div>
						
					</c:forEach>
					
				</div>
			</div>

			<!-- Done tasks list -->
			<div id="list-my-tasks-complete" class="list-tasks-resolved">
				
				<c:if test="${cTasks.size() > 0}">
				<a class="link-dark text-decoration-none d-flex align-items-center gap-2" data-bs-toggle="collapse"
					href="#collapse-tasks" role="button" aria-expanded="false" aria-controls="collapse-tasks">
					<i class="fal fa-chevron-down"></i>
					<span>Concluída</span>
					<span class="text-muted">${cTasks.size()}</span>
				</a>
				</c:if>
				<div class="collapse" id="collapse-tasks">
					<div class="list-group py-3 gap-3">
						
						<c:forEach var="task" varStatus="status" items="${cTasks}">
						
						<div class="list-group-item list-group-item-secondary border rounded-4 fs-4">
							<div class="row">
								<div class="col-5">
									<div class="form-check">
										<input class="form-check-input" type="checkbox" value="${task.idTask}" id="input-check-task-resolved" name="input-check-task-resolved" checked>
										<label class="form-check-label text-decoration-line-through" for="input-check-task-resolved">
											${task.title}
										</label>
									</div>
								</div>
								<div class="col-7 d-flex justify-content-end align-items-center gap-5">
									<span class="fs-6">
										<c:if test="${task.dtLimit == null}">
										Sem data
										</c:if>
										
										${task.getDtConvert(task.dtLimit)}
									</span>
								</div>
							</div>
						</div>
						
						</c:forEach>
						
					</div>
				</div>
			</div>
		</main>
	</section>

	<footer></footer>

	<!-- Modals -->
	<div class="modal fade" id="modal-add-tasks" data-bs-keyboard="false"
		tabindex="-1" aria-labelledby="modal-add-tasks" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0">
				<div
					class="modal-header modal-header-default border-0 justify-content-center">
					<h5 class="modal-title fw-normal text-white">
						<i class="fad fa-check"></i> <span> Adicionar tarefa </span>
					</h5>
				</div>
				<div class="modal-body modal-body-default">
					<form method="post" id="form-modal-add-tasks" class="row gap-3">
						<div class="col-12">
							<input type="text" class="form-control input-custom bd ps-0"
								id="input-title-task" name="input-title-task"
								placeholder="Título" autocomplete="off">
						</div>

						<div class="col-12">
							<input type="datetime-local" class="form-control input-custom"
								id="input-date-limit" name="input-date-limit"
								placeholder="Finalizar em...">
						</div>

						<div class="col-12">
							<textarea class="form-control input-custom"
								name="textarea-description" id="textarea-description"
								placeholder="Descrição" cols="30" rows="3"></textarea>
						</div>

						<div class="col-12">
							<select id="select-list-task" class="form-select input-custom"
								name="task-list-option">
								<c:forEach var="ls" items="${listas}">
									<option value="${ls.idLista}">${ls.title}</option>
								</c:forEach>
							</select>
						</div>

						<div class="col-12">
							<div class="input-group mb-3">
								<label class="input-group-text input-custom"
									for="select-tag-task"> <i class="fas fa-tag"></i>
								</label>
								<select class="form-select input-custom border-start-0"
									id="select-tag-task">
									<c:forEach var="tag" varStatus="status" items="${userTags}">
										<option value="${status.index}">${tag.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="col-12 pt-3 d-flex justify-content-between gap-5">
							<button type="button"
								class="btn rounded-5 btn-modal-default modal-back shadow-sm"
								data-bs-dismiss="modal">Cancelar</button>
							<button type="submit"
								class="btn rounded-5 btn-modal-default modal-back shadow-sm">Adicionar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-add-lists" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modal-add-lists" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0">
				<div class="modal-header modal-header-default border-0 justify-content-center">
					<h5 class="modal-title fw-normal text-white">
						<i class="fad fa-tasks"></i>
						<span>Adicionar lista</span>
					</h5>
				</div>
				<div class="modal-body modal-body-default">
					<form id="form-modal-add-lists" class="row gap-3">
						<div class="col-12">
							<input type="text" class="form-control input-custom bd ps-0" id="input-title-list" name="input-title-list" placeholder="Título da lista">
						</div>

						<div class="col-12">
							<textarea class="form-control input-custom" name="textarea-description-list" id="textarea-description-list" placeholder="Descrição" cols="30" rows="3"></textarea>
						</div>
						
						<div class="col-12 pt-3 d-flex justify-content-between gap-5">
							<button type="button" class="btn rounded-5 btn-modal-default modal-back shadow-sm" data-bs-dismiss="modal">Cancelar</button>
							<button type="submit" class="btn rounded-5 btn-modal-default shadow-sm">Adicionar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-share-lists" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modal-share-lists" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0">
				<div class="modal-header modal-header-default border-0 justify-content-center">
					<h5 class="modal-title fw-normal text-white">
						<i class="fas fa-share-alt"></i>
						<span>Compartilhar</span>
					</h5>
				</div>
				<div class="modal-body modal-body-default">

					<div>
						<label class="h5 text-white fw-normal">
							Minhas listas
						</label>
					</div>

					<form id="form-modal-share-lists" class="row gap-3">
						
						<div class="col-12">

							<div class="list-group gap-3">
								
								<div class="list-group-item border-0 rounded-0 fs-5 check-share-list all">
									<div class="row">
										<div class="col">
											<div class="form-check">
												<label class="form-check-label text-white" for="input-check-share-list-all">Selecione Todes</label>
												<input class="form-check-input" type="checkbox" value="" id="input-check-share-list-all">
											</div>
										</div>
									</div>
								</div>

								<div class="list-group-item border-0 rounded-0 fs-5 check-share-list">
									<div class="row">
										<div class="col">
											<div class="form-check">
												<label class="form-check-label" for="input-check-share-list">Faculdade</label>
												<input class="form-check-input" type="checkbox" value="" id="input-check-share-list" name="input-check-share-list">
											</div>
										</div>
										<div class="col-2 d-flex justify-content-end align-items-center gap-5">
											<a href="#modal-share-list-config" class="icons-menu-tasks" data-bs-toggle="modal"
												data-bs-target="#modal-share-list-config">
												<i class="fad fa-cog"></i>
											</a>
										</div>
									</div>
								</div>

								<div class="list-group-item border-0 rounded-0 fs-5 check-share-list">
									<div class="row">
										<div class="col">
											<div class="form-check">
												<label class="form-check-label" for="input-check-share-list2">Viagem</label>
												<input class="form-check-input" type="checkbox" value="" id="input-check-share-list2" name="input-check-share-list">
											</div>
										</div>
										<div class="col-2 d-flex justify-content-end align-items-center gap-5">
											<a href="#modal-share-list-config" class="icons-menu-tasks" data-bs-toggle="modal"
												data-bs-target="#modal-share-list-config">
												<i class="fad fa-cog"></i>
											</a>
										</div>
									</div>
								</div>

							</div>

						</div>
						
						<div class="col-12">
							<textarea class="form-control input-custom" name="textarea-share-list" id="textarea-share-list" placeholder="Compartilhar com..." cols="30" rows="2"></textarea>
						</div>

						<div class="col-12 pt-3 d-flex flex-row-reverse justify-content-between gap-5">
							<button type="submit" class="btn rounded-5 btn-modal-default shadow-sm" form="form-modal-add-tasks">Compartilhar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-share-list-config" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modal-share-list-config" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0">
				<div class="modal-header modal-header-default border-0 justify-content-center">
					<h5 class="modal-title fw-normal text-white">
						<i class="fas fa-share-alt"></i>
						<span>Gerenciar Lista</span>
					</h5>
				</div>
				<div class="modal-body modal-body-default">

					<div>
						<label class="h5 text-white fw-normal">
							Usuários Permitidos
						</label>
					</div>

					<form id="form-modal-share-list-config" class="row gap-3">
						
						<div class="col-12">

							<div class="list-group gap-2">

								<div class="list-group-item border-0 rounded-0 check-share-list">
									<div class="row">
										<div class="col">
											<div class="form-check">
												<label class="form-check-label" for="input-check-share-config">Fulanedetal@email.com</label>
												<input class="form-check-input" type="checkbox" value="" id="input-check-share-config" name="input-check-share-config">
											</div>
										</div>
										<div class="col-2 d-flex justify-content-end align-items-center gap-5">
											<a href="#" class="icons-menu-tasks">
												<i class="fad fa-user-minus"></i>
											</a>
										</div>
									</div>
								</div>

								<div class="list-group-item border-0 rounded-0 check-share-list">
									<div class="row">
										<div class="col">
											<div class="form-check">
												<label class="form-check-label" for="input-check-share-config2">Fulanedetal@email.com</label>
												<input class="form-check-input" type="checkbox" value="" id="input-check-share-config2" name="input-check-share-config">
											</div>
										</div>
										<div class="col-2 d-flex justify-content-end align-items-center gap-5">
											<a href="#" class="icons-menu-tasks">
												<i class="fad fa-user-minus"></i>
											</a>
										</div>
									</div>
								</div>

								<div class="list-group-item border-0 rounded-0 check-share-list">
									<div class="row">
										<div class="col">
											<div class="form-check">
												<label class="form-check-label" for="input-check-share-config3">Fulanedetal@email.com</label>
												<input class="form-check-input" type="checkbox" value="" id="input-check-share-config3" name="input-check-share-config">
											</div>
										</div>
										<div class="col-2 d-flex justify-content-end align-items-center gap-5">
											<a href="#" class="icons-menu-tasks">
												<i class="fad fa-user-minus"></i>
											</a>
										</div>
									</div>
								</div>

								<div class="list-group-item border-0 rounded-0 check-share-list">
									<div class="row">
										<div class="col">
											<div class="form-check">
												<label class="form-check-label" for="input-check-share-config4">Fulanedetal@email.com</label>
												<input class="form-check-input" type="checkbox" value="" id="input-check-share-config4" name="input-check-share-config">
											</div>
										</div>
										<div class="col-2 d-flex justify-content-end align-items-center gap-5">
											<a href="#" class="icons-menu-tasks">
												<i class="fad fa-user-minus"></i>
											</a>
										</div>
									</div>
								</div>

								<div class="list-group-item border-0 rounded-0 check-share-list">
									<div class="row">
										<div class="col">
											<div class="form-check">
												<label class="form-check-label" for="input-check-share-config5">Fulanedetal@email.com</label>
												<input class="form-check-input" type="checkbox" value="" id="input-check-share-config5" name="input-check-share-config">
											</div>
										</div>
										<div class="col-2 d-flex justify-content-end align-items-center gap-5">
											<a href="#" class="icons-menu-tasks">
												<i class="fad fa-user-minus"></i>
											</a>
										</div>
									</div>
								</div>

							</div>

							<div class="d-flex flex-row-reverse">
								<a href="#" class="text-white text-decoration-none pt-2">
									<i class="fal fa-user-minus"></i>
									<span>Remover selecionados</span>
								</a>
							</div>

						</div>

						<div class="col-12 pt-3 d-flex justify-content-between gap-5">
							<button type="button" class="btn rounded-5 btn-modal-default modal-back shadow-sm" data-bs-toggle="modal" data-bs-target="#modal-share-lists">Voltar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-perfil-edit" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modal-perfil-edit" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0">
				<div class="modal-header modal-header-default border-0 justify-content-center">
					<h5 class="modal-title fw-normal text-white">
						<i class="far fa-user-edit"></i>
						<span>Editar Perfil</span>
					</h5>
				</div>
				<div class="modal-body modal-body-default">
					<form id="form-modal-perfil-edit" class="row gap-3">
						
						<div class="col-12">
							<input type="text" class="form-control input-modal-default" name="input-perfil-name" id="input-perfil-name" placeholder="Nome do Usuário">
						</div>

						<div class="col-12">
							<input type="email" class="form-control input-modal-default" name="input-perfil-email" id="input-perfil-email" placeholder="Email">
						</div>
						
						<div class="col-12 pt-3 d-flex justify-content-between gap-5">
							<button type="button" class="btn rounded-5 btn-modal-default modal-back shadow-sm" data-bs-dismiss="modal">Cancelar</button>
							<button type="submit" class="btn rounded-5 btn-modal-default shadow-sm" form="form-modal-add-list">Salvar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-pass-alter" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modal-pass-alter" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0">
				<div class="modal-header modal-header-default border-0 justify-content-center">
					<h5 class="modal-title fw-normal text-white">
						<i class="far fa-lock"></i>
						<span>Nova senha</span>
					</h5>
				</div>
				<div class="modal-body modal-body-default">
					<form id="form-modal-pass-alter" class="row gap-3">
						
						<div class="col-12">
							<input type="password" class="form-control input-modal-default" name="input-pass-actual" id="input-pass-actual" placeholder="Senha atual">
						</div>

						<div class="col-12">
							<input type="password" class="form-control input-modal-default" name="input-pass-new" id="input-pass-new" placeholder="Nova senha">
						</div>

						<div class="col-12">
							<input type="password" class="form-control input-modal-default" name="input-pass-confirm" id="input-pass-confirm" placeholder="Confirme nova senha">
						</div>
						
						<div class="col-12 pt-3 d-flex justify-content-between gap-5">
							<button type="button" class="btn rounded-5 btn-modal-default modal-back shadow-sm" data-bs-dismiss="modal">Cancelar</button>
							<button type="submit" class="btn rounded-5 btn-modal-default shadow-sm" form="form-modal-add-list">Salvar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-photo-alter" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modal-photo-alter" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0">
				<div class="modal-header modal-header-default border-0 justify-content-center">
					<h5 class="modal-title fw-normal text-white">
						<i class="far fa-camera"></i>
						<span>Trocar foto</span>
					</h5>
				</div>
				<div class="modal-body modal-body-default">

					<div>
						<label class="h5 text-white fw-normal">
							Selecione uma foto
						</label>
					</div>

					<form id="form-modal-photo-alter" class="row gap-3">
						
						<div class="col-12 d-flex flex-wrap gap-2 justify-content-center">

							<a href="#image">
								<img src="<%=request.getContextPath()%>/public/images/photo-perfil.svg" class="img-thumbnail border border-primary" width="80" height="80" alt="...">
							</a>

							<a href="#image">
								<img src="<%=request.getContextPath()%>/public/images/photo-perfil.svg" class="img-thumbnail border border-primary" width="80" height="80" alt="...">
							</a>

							<a href="#image">
								<img src="<%=request.getContextPath()%>/public/images/photo-perfil.svg" class="img-thumbnail border border-primary" width="80" height="80" alt="...">
							</a>

							<a href="#image">
								<img src="<%=request.getContextPath()%>/public/images/photo-perfil.svg" class="img-thumbnail border border-primary" width="80" height="80" alt="...">
							</a>

							<a href="#image">
								<img src="<%=request.getContextPath()%>/public/images/photo-perfil.svg" class="img-thumbnail border border-primary" width="80" height="80" alt="...">
							</a>

							<a href="#image">
								<img src="<%=request.getContextPath()%>/public/images/photo-perfil.svg" class="img-thumbnail border border-primary" width="80" height="80" alt="...">
							</a>

							<a href="#image">
								<img src="<%=request.getContextPath()%>/public/images/photo-perfil.svg" class="img-thumbnail border border-primary" width="80" height="80" alt="...">
							</a>

							<a href="#image">
								<img src="<%=request.getContextPath()%>/public/images/photo-perfil.svg" class="img-thumbnail border border-primary" width="80" height="80" alt="...">
							</a>

							<a href="#image">
								<img src="<%=request.getContextPath()%>/public/images/photo-perfil.svg" class="img-thumbnail border border-primary" width="80" height="80" alt="...">
							</a>

							<a href="#image">
								<img src="<%=request.getContextPath()%>/public/images/photo-perfil.svg" class="img-thumbnail border border-primary" width="80" height="80" alt="...">
							</a>

						</div>

						<div class="col-12 pt-3 d-flex justify-content-between gap-5">
							<button type="button" class="btn rounded-5 btn-modal-default modal-back shadow-sm" data-bs-dismiss="modal">Cancelar</button>
							<button type="submit" class="btn rounded-5 btn-modal-default shadow-sm" form="form-modal-photo-alter">Salvar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modal-tag-edit" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modal-tag-edit" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0">
				<div class="modal-header modal-header-default border-0 justify-content-center">
					<h5 class="modal-title fw-normal text-white">
						<i class="far fa-tags"></i>
						<span>Editar Etiquetas</span>
					</h5>
				</div>
				<div class="modal-body modal-body-default">

					<form id="form-modal-tag-edit" class="row gap-3">
						
						<div class="col-12 d-flex align-items-center gap-2">

							<input type="text" class="form-control input-custom bd ps-0" id="input-name-tag" name="input-name-tag" placeholder="Nome da Etiqueta" autocomplete="off">
							
							<div class="dropdown color-switcher">
								<input type="hidden" name="input-tag-color" id="input-tag-color" value="0">
								<button class="input-custom" type="button" data-bs-toggle="dropdown" aria-expanded="false">
									<span style="color:#fff">
										<i class="fas fa-fill-drip"></i>
									</span>
								</button>
								<ul class="dropdown-menu">
									<span class="theme-buttons" data-color="#8e44ad" style="background: #8e44ad;"></span>
									<span class="theme-buttons" data-color="#2980b9" style="background: #2980b9;"></span>
									<span class="theme-buttons" data-color="#f39c12" style="background: #f39c12;"></span>
									<span class="theme-buttons" data-color="#27ae60" style="background: #27ae60;"></span>
									<span class="theme-buttons" data-color="#ea2027" style="background: #ea2027;"></span>
									<span class="theme-buttons" data-color="#e84393" style="background: #e84393;"></span>
									<span class="theme-buttons" data-color="#ff4757" style="background: #ff4757;"></span>
									<span class="theme-buttons" data-color="#ffc312" style="background: #ffc312;"></span>
									<span class="theme-buttons" data-color="#17c0eb" style="background: #17c0eb;"></span>
								</ul>
							</div>

							<button type="submit" class="btn btn-sm px-4 input-custom shadow-sm" form="form-modal-photo-alter">Adicionar</button>

						</div>

						<div class="col-12">

							<div class="list-group gap-2">

								<div class="list-group-item border-0 rounded-0 check-share-list">
									<div class="row">
										<div class="col d-flex gap-2 align-items-center">
											<i class="fas fa-tag text-danger"></i>
											<label>Faz agora</label>
										</div>
										<div class="col-2 d-flex justify-content-end align-items-center gap-5">
											<a href="#" class="icons-menu-tasks">
												<i class="far fa-minus"></i>
											</a>
										</div>
									</div>
								</div>

								<div class="list-group-item border-0 rounded-0 check-share-list">
									<div class="row">
										<div class="col d-flex gap-2 align-items-center">
											<i class="fas fa-tag text-warning"></i>
											<label>Se preocupe</label>
										</div>
										<div class="col-2 d-flex justify-content-end align-items-center gap-5">
											<a href="#" class="icons-menu-tasks">
												<i class="far fa-minus"></i>
											</a>
										</div>
									</div>
								</div>

								<div class="list-group-item border-0 rounded-0 check-share-list">
									<div class="row">
										<div class="col d-flex gap-2 align-items-center">
											<i class="fas fa-tag text-primary"></i>
											<label>Faz quando der</label>
										</div>
										<div class="col-2 d-flex justify-content-end align-items-center gap-5">
											<a href="#" class="icons-menu-tasks">
												<i class="far fa-minus"></i>
											</a>
										</div>
									</div>
								</div>

								<div class="list-group-item border-0 rounded-0 check-share-list">
									<div class="row">
										<div class="col d-flex gap-2 align-items-center">
											<i class="fas fa-tag text-success"></i>
											<label>Fica tranquilo</label>
										</div>
										<div class="col-2 d-flex justify-content-end align-items-center gap-5">
											<a href="#" class="icons-menu-tasks">
												<i class="far fa-minus"></i>
											</a>
										</div>
									</div>
								</div>

							</div>

						</div>

						<div class="col-12 pt-3 d-flex justify-content-between gap-5">
							<button type="button" class="btn rounded-5 btn-modal-default modal-back shadow-sm" data-bs-dismiss="modal">Cancelar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</c:set>

<c:set var="end">
	<script src="<%=request.getContextPath()%>/public/js/lista.js"></script>
	<script src="<%=request.getContextPath()%>/public/js/task.js"></script>
</c:set>

<t:layout>
	<jsp:attribute name="headTitle">${headTitle}</jsp:attribute>
	<jsp:attribute name="head">${head}</jsp:attribute>
	<jsp:attribute name="end">${end}</jsp:attribute>
	<jsp:body>${body}</jsp:body>
</t:layout>