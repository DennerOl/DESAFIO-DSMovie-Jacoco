
Neste projeto foram avalidas minhas competencias em desenvolver testes unitarios 

-----------Sobre o projeto DSMovie:

Este é um projeto de filmes e avaliações de filmes. 
A visualização dos dados dos filmes é pública (não necessita login), porém as alterações de filmes (inserir, atualizar, deletar)
são permitidas apenas para usuários ADMIN. As avaliações de filmes podem ser registradas por qualquer usuário logado CLIENT ou ADMIN. 
A entidade Score armazena uma nota de 0 a 5 (score) que cada usuário deu a cada filme. Sempre que um usuário registra uma nota,
o sistema calcula a média das notas de todos usuários, e armazena essa nota média (score) na entidade Movie, juntamente com a contagem de votos (count)

![dsmovie](https://github.com/DennerOl/DESAFIO-DSMovie-Jacoco/assets/124217386/0620c531-2b70-4e44-bdeb-c511e26ae1a8)


---------Abaixo segue os testes que tive que passar ------------

MovieServiceTests:


findAllShouldReturnPagedMovieDTO

findByIdShouldReturnMovieDTOWhenIdExists

findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist

insertShouldReturnMovieDTO

updateShouldReturnMovieDTOWhenIdExists

updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist

deleteShouldDoNothingWhenIdExists

deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist

deleteShouldThrowDatabaseExceptionWhenDependentId


ScoreServiceTests:


saveScoreShouldReturnMovieDTO

saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId


UserServiceTests:


authenticatedShouldReturnUserEntityWhenUserExists

authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists

loadUserByUsernameShouldReturnUserDetailsWhenUserExists

loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists

