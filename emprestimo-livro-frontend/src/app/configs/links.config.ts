import { Link } from "../interfaces/link";

export const loginLink:Link=    {
  title: 'Login',
  url: '/',
  icon: 'account_circle',
  detail:'logar na aplicação'
};

export const logoutLink:Link=    {
  title: 'Sair',
  url: '/',
  icon: 'run_circle',
  detail:'Sair da aplicação'
};
export const perfilLink:Link=    {
  title: 'Perfil',
  url: '/perfil',
  icon: 'face',
  detail:'Perfil do usuário'
};

export const links:Link[]=[
    {
      title: 'Pessoas',
      url: '/pessoas',
      icon: 'groups',
      detail:'Pessoas cadastradas no sistema',
      permissao:['ROLE_ADMIN']
    },
    {
      title: 'Livros',
      url: '/livros',
      icon: 'books',
      detail:'Livros cadastrados no sistema',
      permissao:['ROLE_ADMIN','ROLE_REQUISITANTE']
    },
    {
      title: 'Retiradas',
      url: '/retiradas',
      icon: 'logout',
      detail:'Retirada de livros cadastrados no sistema',
      permissao:['ROLE_REQUISITANTE']
    },
    {
      title: 'Devoluções',
      url: '/devolucoes',
      icon: 'login',
      detail:'Devolução de livros cadastrados no sistema',
      permissao:['ROLE_REQUISITANTE']
    },
    loginLink
  ];



  export const menuUrls=['/pessoas','/livros','/retiradas','/devolucoes','/atividades']

  export const menuLinks:Link[]=links.filter(link => menuUrls.indexOf(link.url)>-1)