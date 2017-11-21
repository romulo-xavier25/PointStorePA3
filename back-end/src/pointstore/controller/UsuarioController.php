<?php
namespace pointstore\controller;

use pointstore\persistence\UsuarioDAO;
use pointstore\entity\Usuario;
use pointstore\controller\AbstractController;
use Doctrine\ORM\EntityManager;

class UsuarioController extends AbstractController {

	public $entityManager;
	
	public function __construct(){
    	parent::__construct(new UsuarioDAO());
	}

	public function insert($json){
    	$user = new Usuario();
    	$user->setNome($json->nome);
    	$user->setSobrenome($json->sobrenome);
    	$user->setEmail($json->email);
    	$user->setLogin($json->login);
    	$user->setSenha($json->senha);
    	$this->getDao()->insert($user);
    	return ["mensagem"=>"Usuario inserido com sucesso"];
	}
	
	public function atualizarEmail($email, $json){
		$user = new Usuario();
		$usuarioDetached = $this->entityManager->createQuery('SELECT user FROM Usuario user WHERE user.email = :email');
		$usuarioDetached->setParameter("email", $json->email);
		$queryResult = $usuarioDetached->getResult();

		var_dump($queryResult);

		//$user->setSenha($json->senha);
		//$this->getDao()->update($user);
    	//return ["mensagem"=>"Senha atualizada com sucesso"];
	}

	public function update($json){}

	public function logarUsuario($json){
		$usuarioDetached = new Usuario();
		$usuarioDetached = $this->getDao()->entityManager->createQuery('SELECT user FROM \pointstore\entity\Usuario user WHERE user.login = :login and user.senha = :senha');
		$usuarioDetached->setParameter('login', $json->login);
		$usuarioDetached->setParameter('senha', $json->senha);
		$queryResult = (object) $usuarioDetached->getArrayResult()[0];
		return $queryResult;
	}
	
}