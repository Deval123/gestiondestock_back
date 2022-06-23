mvc clean install -U
Couche REPOSITORY OU DAO (Data Access Object) qui permet de recuperer les donnees de la BDD

    //exemple de methode JPQL
    @Query("select a from article where codearticle = :code and designation = :designation")
    List<Article> findByCustomQuery(@Param("code") String c, @Param("designation") String d);

    //exemple des requêtes Natives
    @Query(value = "select a from article where code = :code", nativeQuery = true)
    List<Article> findByCustomNativeQuery(@Param("code") String c);

    	gestion de stock


http://localhost:8081/swagger-ui/index.html#/article-controller (lien vers swagger).


to check against
https://www.marcobehler.com/guides/spring-security

Le reste à faire pour l'API Gestion De Stock. Just want to see

Done 
x Todo